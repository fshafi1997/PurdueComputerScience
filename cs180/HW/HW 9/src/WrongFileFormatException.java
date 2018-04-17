/**
 * Created by farhanshafi on 11/9/16.
 */
public class WrongFileFormatException extends Exception {
    public WrongFileFormatException(){};

    public WrongFileFormatException(String message){
        super (message);
    }
}
