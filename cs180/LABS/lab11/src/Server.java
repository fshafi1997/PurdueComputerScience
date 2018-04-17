import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by farhanshafi on 11/11/16.
 */
public class Server {


    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static BufferedReader bufferedReader,reader;
    private static PrintWriter outToclient;

    static String fileLocation = "/homes/fshafi/Desktop/cs180/lab12/Server/";

    public static void main(String[] args) throws IOException, InfoNotFoundException {


        try {
            serverSocket = new ServerSocket(5000);
            System.out.println("Server waiting for connection");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        try {
            clientSocket = serverSocket.accept();
            System.out.println("Connection is successful and waiting for commands");
        } catch (IOException e) {
            e.printStackTrace();
        }

        outToclient = new PrintWriter(clientSocket.getOutputStream(),true);
        bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String fileName = bufferedReader.readLine();
        String UserName = bufferedReader.readLine();



            System.out.println("Filename: " + fileName);
            System.out.println("Username: " + UserName);


        File myFile = new File(fileLocation+fileName);

        try{
            reader = new BufferedReader(new FileReader(myFile));
        }catch (FileNotFoundException e){
            e.printStackTrace();
            outToclient.println("FileNotFoundException");
        }
        String[] usernames = new String[5];
        String[] lastname = new  String[5];
        String[] firstname = new String[5];
        int[] marks = new int[5];

        /*String inputLine;
        while((inputLine = reader.readLine()) != null) {
            String[] array = inputLine.split(";");
        }*/
        for (int i =0; i<6;i++){
            String x = reader.readLine();
            int indexFirstSemiColon = x.indexOf(";");
            System.out.println(indexFirstSemiColon);
            int indexSecondSemiColon = x.indexOf(";",indexFirstSemiColon);
            System.out.println(indexSecondSemiColon);
            int indexThirdSemiColon = x.indexOf(";",indexSecondSemiColon);
            System.out.println(indexSecondSemiColon);
            int indexFourthSemiColon = x.indexOf(";",indexThirdSemiColon);
            System.out.println(indexFourthSemiColon);

            String Username = x.substring(indexFirstSemiColon+1,indexSecondSemiColon);
            String Lastname = x.substring(indexSecondSemiColon+1,indexThirdSemiColon);
            String Firstname = x.substring(indexThirdSemiColon+1,indexFourthSemiColon);
            int Marks = Integer.parseInt(x.substring(indexFourthSemiColon+1));

            usernames[i]=Username;
            lastname[i]=Lastname;
            firstname[i]=Firstname;
            marks[i]=Marks;
        }

        for (int i=0;i<6;i++){
            if (UserName==usernames[i]){
                outToclient.print("LastName: " + lastname[i] + "\n" + "FirstName: " + firstname[i] +"\n" + "Marks: " + marks[i]);
            }else throw new InfoNotFoundException("Info of username not found");
        }

        clientSocket.close();




    }
}

