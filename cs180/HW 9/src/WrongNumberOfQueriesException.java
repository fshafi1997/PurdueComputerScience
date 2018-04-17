/**
 * Created by farhanshafi on 11/9/16.
 */
public class WrongNumberOfQueriesException extends Exception {
    public WrongNumberOfQueriesException(){};

    public WrongNumberOfQueriesException(String message){
        super(message);
    }
}
