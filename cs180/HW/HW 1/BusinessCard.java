/**
 *HomeWork 1 - Business Card
 *
 *This will take the persons name, major and email as inputs
 *and will output them in the form of a business card.
 *
 *@author Farhan Shafi, lab section G02
 *
 *@version August 30, 2016
 *
 */

import javax.swing.*;
public class BusinessCard {
    public static void main(String[] args) {
	String name = JOptionPane.showInputDialog("Enter your name:");
	String major = JOptionPane.showInputDialog("Enter your major:");
	String email = JOptionPane.showInputDialog("Enter your e-mail");
	JOptionPane.showMessageDialog(null, "Name:" + name + "\n" + "Major:" + major + "\n" + "E-mail:" + email);
    }
}