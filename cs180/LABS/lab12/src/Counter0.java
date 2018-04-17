/**
 * Created by farhanshafi on 11/16/16.
 */
public class Counter0 implements Counter {
    private int value = 0;

    public void inc() {
        value = value + 1;
    }

    public void dec() {
        value = value - 1;
    }

    public int get() {
        return value;
    }
}