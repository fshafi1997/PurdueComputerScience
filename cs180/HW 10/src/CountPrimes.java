import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by farhanshafi on 11/16/16.
 */
public class CountPrimes extends Thread{
    private long lower;
    private long upper;
    static  AtomicInteger numPrimes = new AtomicInteger(0);

    public CountPrimes(long lower, long upper){
        this.lower = lower;
        this.upper = upper;
    }

    private boolean isPrime(long num){
        if (num<=1){
            return false;
        }

        if (num==3 || num==2){
            return true;
        }

        if (num%2 == 0){
            return false;
        }

        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }

        return true;
    }

    public static void resetNumPrimes(){
        numPrimes.set(0);
    }

    public static int getNumPrimes(){

        return numPrimes.get();
    }

    public void run(){
        for (long i=lower;i<upper;i++) {
            boolean test = isPrime(i);
            if (test == true) {
                numPrimes.getAndIncrement();
            }
        }
    }
}
