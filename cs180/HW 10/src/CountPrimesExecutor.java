

/**
 * Created by farhanshafi on 11/16/16.
 */
public class    CountPrimesExecutor {
    private long lower;
    private long upper;
    private int numThreads;
    private CountPrimes[] countPrimeThreads;

    public CountPrimesExecutor(int numThreads, long lower, long upper) {
        this.numThreads = numThreads;
        this.lower = lower;
        this.upper = upper;
        this.countPrimeThreads = new CountPrimes[numThreads];

        int x = (int) ((upper - lower + 1) / numThreads);
        for (int i = 0; i < numThreads; i++) {
            CountPrimes t1 = new CountPrimes((x * i) + lower, ((x * i)) + x);
            countPrimeThreads[i] = t1;

        }

    }

    public void executeThreads() {
        for (int i = 0; i < numThreads; i++) {
            countPrimeThreads[i].start();
            try {
                countPrimeThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        CountPrimesExecutor t = new CountPrimesExecutor(4, 1, 100);
        t.executeThreads();
        System.out.println("amount of primes: " + CountPrimes.getNumPrimes());
    }

}
