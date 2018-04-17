/**
 * Created by farhanshafi on 11/16/16.
 */
public class Driver {
    public static void main(String[] args) {
        Thread t = new A();
        t.start();

        Runnable r = new B();
        Thread x = new Thread(r);
        x.start();
    }
}
