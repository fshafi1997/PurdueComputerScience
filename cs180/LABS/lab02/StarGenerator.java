import java.util.*;
import javax.swing.*;

public class StarGenerator{
    public static void main(String[] args) {
	Scanner scan1 = new Scanner(System.in);
	System.out.println("Please enter your name: ");
	name = scan1.next();
	System.out.println("Enter the college you are in: ");
	college = scan1.next();
	System.out.println("Your name: " + name);
	char first = name.charAt(0);
	int index = input.indexOf(" ");
	int len = name.length();
	String lastname = name.substring(index+1);
	String email = " " + first + lastname + "@purdue.edu";
	System.out.println("Your College: " + college);
	String college2 = college.substring(0,4);
	String college3= college.toUpperCase();
	Random number = new random();
	number.nextInt(5);
	String group = " " + college3 + number;
	System.out.println("STAR Group:" + group);
    }
}
	
	
