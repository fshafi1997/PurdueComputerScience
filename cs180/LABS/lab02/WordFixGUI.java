import java.util.*;
import javax.swing.*;

public class WordFixGUI{
    public static void main(String[] args) {
	String input = JOptionPane.showInputDialog("Please enter the input");
	
        int len = input.length();
	
	    char first = input.charAt(0);
	char last = input.charAt(len-1);
	int index = input.indexOf("*");
	String sub1 = input.substring(1,index);
	String sub2 = input.substring(index+1,len-1);
	String newname = " " + last + sub2 + sub1 + first;
	JOptionPane.showMessageDialog(null,"Input:" + input + "\n" + "Result:" + newname);
	
	    
    }
}
