import java.util.*;
import java.lang.*;

public class Pythagoras{
    public static void main(String[] args) {
	double a, b;
	Scanner scanner = new Scanner(System.in);
	System.out.println("Enter a");
	a = scanner.nextDouble();
	System.out.println("Enter b");
	b = scanner.nextDouble();
	double c = Math.sqrt(Math.pow(a,2) + Math.pow(b,2));
	System.out.println("Hypotenuse = " + c);
    }
}