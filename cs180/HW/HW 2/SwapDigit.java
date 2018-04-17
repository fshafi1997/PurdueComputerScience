import java.util.*;

public class SwapDigit{
    public static void main(String[] args) {
	Scanner scanner = new Scanner(System.in);
	System.out.println("Enter number");
	String number = scanner.next();
	char first = number.charAt(0);
	char second = number.charAt(1);
	System.out.println("Swapped: " + second + first);
    }
}
