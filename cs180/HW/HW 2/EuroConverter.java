import java.util.*;
import java.lang.*;

public class EuroConverter {
    public static void main (String[] args){
	double AmountUSD, NumberinEUR;
	Scanner scanner = new Scanner(System.in);
	System.out.println("Enter amount in USD");
	AmountUSD = scanner.nextDouble();
	System.out.println("Enter number of $ in 100 Eur");
	NumberinEUR = scanner.nextDouble();
	double numberofeuro = (AmountUSD/NumberinEUR)*100;
	System.out.println("Number of euros = " + numberofeuro);
    }
}