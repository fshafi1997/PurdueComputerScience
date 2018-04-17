/**
 * Created by farhanshafi on 11/9/16.
 */
public class MalformedQueryException extends Exception {
    public MalformedQueryException(){};

    public MalformedQueryException(String message){
        super(message);
    }
}
