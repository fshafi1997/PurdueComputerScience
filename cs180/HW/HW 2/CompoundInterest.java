import java.util.*;
import java.lang.*;

public class CompoundInterest {
    public static void main (String[] args){
        double  principle, interestrate, years;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter principle");
        principle = scanner.nextDouble();
        System.out.println("Enter interest rate");
        interestrate = scanner.nextDouble();
        System.out.println("Enter years");
        years = scanner.nextDouble();
	double interest = ((((interestrate+100)/100)*years)*interestrate);
        System.out.println("Interest  = " + (int) interest);
			   }
    }