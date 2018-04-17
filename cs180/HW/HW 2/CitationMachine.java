import javax.swing.*;

public class CitationMachine{
    public static void main(String[] args) {
	JOptionPane.showMessageDialog(null, "Enter book details to generate APA format citation.");
	String authorname = JOptionPane.showInputDialog("Author name:");
	String yearofpublication = JOptionPane.showInputDialog("Year of publication:");
	String titleofwork = JOptionPane.showInputDialog("Title of work:");
	String publisher = JOptionPane.showInputDialog("Publisher:");
	String location = JOptionPane.showInputDialog("Location:");
	String firstinitial = authorname.charAt(0) + "";
	String firstupper = firstinitial.toUpperCase();
	int index = authorname.indexOf(" ");
	int len = authorname.length();
	String second = authorname.substring(index);
	JOptionPane.showMessageDialog(null, " " + second + "," + " " + firstupper + "." + "(" + yearofpublication + ")" + "." + titleofwork + "." + location + ":" + publisher + ".");
    }
}


