/**
 * Created by farhanshafi on 10/26/16.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class Controller{
    Socket socket;
    PrintWriter out;
    InputStreamReader isr;
    BufferedReader in;

    String firstPlayerResult;
    String secondPlayerResult;

    static Model userInfo = new Model();


    public Controller() {
        String serverIP = "localhost";
        int serverPort = 5000;
        try {
            socket = new Socket(serverIP, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            isr = new InputStreamReader(socket.getInputStream());
            in = new BufferedReader(isr);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String registerNewUser(String userName, String password) {
        String registerRequest = "CREATENEWUSER--" + userName + "--" + password;
        String registerResponse = "";
        try {
            out.println(registerRequest);
            registerResponse = in.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        registerResponse = registerResponse.substring(25, registerResponse.length() - 2);

        if (registerResponse.equals("INVALIDMESSAGEFORMAT")) {
            return "Invalid message format please try again.";
        } else if (registerResponse.equals("INVALIDUSERNAME")) {
            return "Invalid username please try again";
        } else if (registerResponse.equals("INVALIDUSERPASSWORD")) {
            return "Invalid userpass please try again";
        } else if (registerResponse.equals("USERALREADYEXISTS")) {
            return "User already exists please try again";
        } else if (registerResponse.equals("SUCCESS")) {
            return "SUCCESS";
        } else {
            return "Registration failed";
        }
    }


    public String userLogin(String userName, String password) {
        String loginRequest = "LOGIN--" + userName + "--" + password;
        String loginResponse = "";
        try {
            out.println(loginRequest);
            loginResponse = in.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }

        loginResponse = loginResponse.substring(17);
        String statusCheck = loginResponse.substring(0, 7);
        /*if (statusCheck.equals("SUCCESS")) {
            userInfo.setUserToken(loginResponse.substring(9));
        }*/
        if (statusCheck.equals("SUCCESS")) {
            userInfo.setUserToken(loginResponse.substring(9));
            return "SUCCESS";
        } else if (statusCheck.equals("INVALIDMESSAGEFORMAT")) {
            return "Message format was invalid";
        } else if (statusCheck.equals("INVALIDUSERNAME")) {
            return "Username is invalid";
        } else if (statusCheck.equals("INVALIDUSERPASSWORD")) {
            return "Password is invalid";
        } else if (statusCheck.equals("USERALREADYLOGGEDIN")) {
            return "User is already logged in";
        }
        return "Login Failed";
    }

    public String startNewGame() {
        String startRequest = "STARTNEWGAME--" + userInfo.getUserToken();
        String startResponse = "";

        try {
            out.println(startRequest);
            startResponse = in.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        String checkSuccess = startResponse.substring(24, startResponse.length() - 5);
        if (checkSuccess.equals("SUCCESS")) {
            userInfo.setGameToken(startResponse.substring(33));
        } else if (checkSuccess.equals("USERNOTLOGGEDIN")) {
            return "User is not logged in";
        } else if (checkSuccess.equals("FAILURE")) {
            return "Server failed to create new game session";
        }
        return startResponse.substring(24);
    }

    public String joinGame(String gameToken) {
        String joinRequest = "JOINGAME--" + userInfo.getUserToken() + "--" + gameToken;
        String joinResponse = "";
        try {
            out.println(joinRequest);
            joinResponse = in.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return joinResponse.substring(20);
    }

    public void launchGame() {
        String launchRequest = "ALLPARTICIPANTSHAVEJOINED--" + userInfo.getUserToken() +"--"+ userInfo.getGameToken();
        String launchResponse = "";

        try {
            out.println(launchRequest);
            launchResponse = in.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        launchResponse = launchResponse.substring(37, launchResponse.length() - 2);
        /*if (launchResponse.equals("USERNOTLOGGEDIN")) {
            return "User is not logged in";
        } else if (launchResponse.equals("INVALIDGAMETOKEN")) {
            return "Game token is invalid";
        } else if (launchResponse.equals("USERNOTGAMELEADER")) {
            return "User is not game leader";
        } else {
            return launchResponse;
        }
        */
    }

    //used three time
    public String serverListener() throws IOException {
        String serverResponse = null;

        socket.setSoTimeout(1);

            while (serverResponse == null) {
                try {
                    serverResponse = in.readLine();
                } catch (SocketTimeoutException e) {
                    e.printStackTrace();
                }
            }
            userInfo.incrementUserCounter();

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        // Make socket blocking
        socket.setSoTimeout(0);
        System.out.println(serverResponse);
        // Return server response
        return serverResponse;
    }

    public void playerSuggestion(String suggestion) {
        String suggestRequest = "PLAYERSUGGESTION--" + userInfo.getUserToken() + "--" + userInfo.getGameToken() + "--" + suggestion;
        String suggestResponse = "";
        out.println(suggestRequest);
        //suggestResponse = in.readLine();

    }
        /*suggestResponse = suggestResponse.substring(27, suggestResponse.length() - 2);
        if (suggestResponse.equals("USERNOTLOGGEDIN")) {
            return "User is not logged in";
        } else if (suggestResponse.equals("INVALIDGAMETOKEN")) {
            return "Game token is invalid";
        } else if (suggestResponse.equals("UNEXPECTEDMESSAGETYPE")) {
            return "Message type is unexpected";
        } else if (suggestResponse.equals("INVALIDMESSAGEFORMAT")) {
            return "Message format is invalid";
        } else {
            return suggestResponse;
        }
    }
    */
    public String playerSuggestion(String suggestion,String gameToken) {
        String suggestRequest = "PLAYERSUGGESTION--" + userInfo.getUserToken() + "--" + gameToken + "--" + suggestion;
        String suggestResponse = "";
        try {
            out.println(suggestRequest);
            suggestResponse = in.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        suggestResponse = suggestResponse.substring(27, suggestResponse.length() - 2);
        if (suggestResponse.equals("USERNOTLOGGEDIN")) {
            return "User is not logged in";
        } else if (suggestResponse.equals("INVALIDGAMETOKEN")) {
            return "Game token is invalid";
        } else if (suggestResponse.equals("UNEXPECTEDMESSAGETYPE")) {
            return "Message type is unexpected";
        } else if (suggestResponse.equals("INVALIDMESSAGEFORMAT")) {
            return "Message format is invalid";
        } else {
            return suggestResponse;
        }
    }

    public String playerChoice(String suggestion) {
        String choiceRequest = "PLAYERCHOICE--" + userInfo.getGameToken() + "--" + userInfo.getUserToken() + "--" + suggestion;
        String choiceResponse = "";
        try {
            out.println(choiceRequest);
            choiceResponse = in.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        choiceResponse = choiceResponse.substring(24, choiceResponse.length() - 2);
        if (choiceResponse.equals("USERNOTLOGGEDIN")) {
            return "User is not logged in";
        } else if (choiceResponse.equals("INVALIDGAMETOKEN")) {
            return "Game token is invalid";
        } else if (choiceResponse.equals("UNEXPECTEDMESSAGETYPE")) {
            return "Message type is unexpected";
        } else if (choiceResponse.equals("INVALIDMESSAGEFORMAT")) {
            return "Message format is invalid";
        } else {
            return choiceResponse;
        }
    }
    public String makeResults(String passAString) {

        String newTest = passAString.substring(13);
        String[] everything = newTest.split("--");
        String lastone = everything[8];
        String firstName = everything[0];
        firstPlayerResult = everything[1];
        String score1 = everything[2];
        String fooled1 = everything[3];
        String fooledby1 = everything[4];
        String secondName = everything[5];
        secondPlayerResult = everything[6];
        String score2 = everything[7];
        String fooled2 = everything[8];
        String fooledBy2 = everything[9];

        String first1 = firstName + " ==> " + "Score : " + score1 + " | " + "Fooled : " + fooled1 + " player(s)" + " | " + "Fooled by: " + fooledby1 + " player(s)";
        String second1 = secondName + " ==> " + "Score : " + score2 + " | " + "Fooled : " + fooled2 + " player(s)" + " | " + "Fooled by: " + fooledBy2 + " player(s)";

        String toReturn = first1 + "\n" + second1;

        return toReturn;
    }

    public String logout() {
        String logoutRequest = "LOGOUT--";
        String logoutResponse = "";
        try {
            out.println(logoutRequest);
            logoutResponse = in.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return logoutResponse;
    }

}
