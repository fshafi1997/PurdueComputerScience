import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by farhanshafi on 11/11/16.
 */
public class Client {
    private static Socket socket;
    private static PrintWriter out;
    private static InputStreamReader isr;
    private static BufferedReader in;

    public static String userName,fileName;

    public static void main(String[] args) throws IOException {
        File f = new File("/homes/fshafi/Desktop/cs180/lab12/Client/info.txt");
        FileWriter fr = new FileWriter(f);

        try {
            socket = new Socket("localhost", 5000);
            out = new PrintWriter(socket.getOutputStream(), true);
            isr = new InputStreamReader(socket.getInputStream());
            in = new BufferedReader(isr);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter filename");
        fileName = scanner.nextLine();
        out.println(fileName);
        System.out.println("Please enter username");
        userName = scanner.nextLine();
        out.println(userName);

        String x = in.readLine();

        if (x == "FileNotFoundException"){
            System.out.println("File does not exist");
        }else if (x == "Info of username not found"){
            try {
                throw new InfoNotFoundException("User is not in database");
            } catch (InfoNotFoundException e) {
                e.printStackTrace();
            }
        }
        else fr.write(x);

        fr.close();
    }


}
