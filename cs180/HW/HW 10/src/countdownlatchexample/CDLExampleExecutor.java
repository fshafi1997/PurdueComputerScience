package countdownlatchexample;

import java.util.concurrent.CountDownLatch;

/**
 * Example for CS18000-F16 Homework 10 to show use of CountDownLatch.
 *
 * @author Neil Allison
 * @version November 12, 2016
 */
public class CDLExampleExecutor {
    private static CountDownLatch latch;
    private char[] letters;
    private countdownlatchexample.CDLExample[] cdlExampleThreads;

    public CDLExampleExecutor(char[] letters) {
        int numLetters = letters.length;
        latch = new CountDownLatch(numLetters);
        this.letters = letters;
        cdlExampleThreads = new countdownlatchexample.CDLExample[numLetters];
        initializeThreads();
    }

    public void initializeThreads() {
        for (int i = 0; i < letters.length; i++) {
            cdlExampleThreads[i] = new countdownlatchexample.CDLExample(letters[i]);
        }
    }

    public void executeThreads() {
        for (countdownlatchexample.CDLExample cdlExampleThread : cdlExampleThreads) {
            cdlExampleThread.start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void decrementCountDownLatch() {
        latch.countDown();
    }

    public static void main(String[] args) {
        CDLExampleExecutor cdlExampleExecutor = new CDLExampleExecutor(new char[] {'A', 'B', 'C', 'D'});
        cdlExampleExecutor.executeThreads();
        System.out.println("All threads done executing.");
    }
}
