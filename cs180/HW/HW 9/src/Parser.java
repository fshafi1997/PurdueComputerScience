import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by farhanshafi on 11/9/16.
 */
public class Parser {

    private String username;
    private int numQueries;

    public String getUserName() {
        return username;
    }

    public int getNumQueries() {
        return numQueries;
    }

    Scanner scanner;


    public void parse(String filePath) throws WrongFileFormatException, WrongNumberOfQueriesException,
            InvalidInputException, MalformedQueryException, IOException {

        scanner = new Scanner(new File(filePath));
        String firstCapitalLetter = scanner.nextLine();

        if (!(firstCapitalLetter.equals("C"))) {
            throw new WrongFileFormatException("Format of file is wrong!");
        }

        username = scanner.nextLine();

        String firstSmallLetter = scanner.nextLine();

        if (!(firstSmallLetter.equals("c"))) {
            throw new WrongFileFormatException("Format of file is wrong!");
        }

        String secondCapitalLetter = scanner.nextLine();

        if (!(secondCapitalLetter.equals("N"))) {
            throw new WrongFileFormatException("Format of file is wrong!");
        }

        String numberQueries = scanner.nextLine();
        numQueries = Integer.parseInt(numberQueries);

        if (numQueries < 1) {
            throw new InvalidInputException("The number should be 1 or larger");
        }

        String secondSmallLetter = scanner.nextLine();

        if (!(secondSmallLetter.equals("n"))) {
            throw new WrongFileFormatException("Format of file is wrong!");
        }

        String thirdCapitalLetter = scanner.nextLine();

        if (!(thirdCapitalLetter.equals("Q"))) {
            throw new WrongFileFormatException("Format of file is wrong!");
        }

        String query;
        int counter = 0;
        boolean x = true;

        while (x = true) {
            query = scanner.nextLine();

            String splitted[] = query.split(" ");
            //System.out.println(splitted[0].equals("SELECT"));

            if (query.equals("q") || query.equals("-1")) {
                x = false;
                break;
            }
            else if (!(splitted[0].equals("SELECT") || splitted[0].equals("UPDATE") || splitted[0].equals("INSERT") ||
                    splitted[0].equals("DELETE"))) {
                throw new MalformedQueryException("Query is not required format");
            }
             else {
                counter++;
            }
        }
        if (counter != getNumQueries()) {
            throw new WrongNumberOfQueriesException("There are wrong number of queries");
        }
    }


    public static void main(String[] args) {
        Parser test = new Parser();

        try {
            test.parse("file11.sql");
        } catch (WrongFileFormatException e) {
            e.printStackTrace();
        } catch (WrongNumberOfQueriesException e) {
            e.printStackTrace();
        } catch (InvalidInputException e) {
            e.printStackTrace();
        } catch (MalformedQueryException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

