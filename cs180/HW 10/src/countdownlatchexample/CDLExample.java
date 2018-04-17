package countdownlatchexample;

/**
 * Example for CS18000-F16 Homework 10 to show use of CountDownLatch.
 *
 * @author Neil Allison
 * @version November 12, 2016
 */
public class CDLExample extends Thread {
    private char letter;

    public CDLExample(char letter) {
        this.letter = letter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(letter);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.err.printf("Sleeping Failed: %s%n", e.getMessage());
            }
        }
        System.out.printf("%c thread finished%n", letter);
        CDLExampleExecutor.decrementCountDownLatch();
    }
}
