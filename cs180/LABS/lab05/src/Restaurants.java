/**
 * CS180 - Lab 07
 *
 * This class contains a list of some of the on-campus and off-campus restaurants and cafes.
 * You should complete it to fulfill the requirements of lab07
 *
 */
import java.util.*;
import java.lang.*;

public class Restaurants {
    // On campus
    public static final String ON_CAMPUS_VEGAN = "Purdue Dining Courts\nFlatbreads";
    public static final String ON_CAMPUS_VEGETARIAN = ON_CAMPUS_VEGAN + "\nOasis Cafe\nAh*Z\nFreshens";
    public static final String ON_CAMPUS_GLUTEN_FREE = "Purdue Dining Courts\nFlatbreads\nOasis Cafe\nPappy's " +
            "Sweet Shop";
    public static final String ON_CAMPUS_BURGERS = "Pappy's Sweet Shop\nCary Knight Spot";
    public static final String ON_CAMPUS_SANDWICHES = "Flatbreads\nOasis Cafe\nErbert & Gerbert's";
    public static final String ON_CAMPUS_OTHERS = "Purdue Dining Courts\nAh*Z\nFreshens\nLemongrass";
    public static final String ON_CAMPUS_ALL = ON_CAMPUS_BURGERS + "\n" + ON_CAMPUS_SANDWICHES + "\n" +
            ON_CAMPUS_OTHERS;

    // Off campus
    public static final String OFF_CAMPUS_VEGAN = "Chipotle\nQdoba\nNine Irish Brothers\nFive Guys\n Puccini's " +
            "Smiling Teeth\nPanera Bread";
    public static final String OFF_CAMPUS_VEGETARIAN = OFF_CAMPUS_VEGAN + "\nWendy's\nBruno's Pizza\nJimmy " +
            "John's\nPotbelly Sandwich Shop\nBasil Thai\nIndia Mahal";
    public static final String OFF_CAMPUS_GLUTEN_FREE = "Chipotle\nQdoba\nNine Irish Brothers\nPuccini's Smiling " +
            "Teeth\nWendy's\nScotty's Brewhouse\nPanera Bread\nBasil Thai";
    public static final String OFF_CAMPUS_BURGERS = "Five Guys\nWendy's\nTriple XXX\nScotty's Brewhouse";
    public static final String OFF_CAMPUS_SANDWICHES = "Panera Bread\nJimmy John's\nPotbelly Sandwich Shop";
    public static final String OFF_CAMPUS_PIZZAS = "Puccini's Smiling Teeth\nMad Mushroom Pizza\nBruno's Pizza\n";
    public static final String OFF_CAMPUS_OTHERS = "Chipotle\nQdoba\nNine Irish Brothers\nFamous Frank's\n Von's " +
            "Dough Shack\nBuffalo Wild Wings\nBasil Thai\nMaru Sushi\nIndia Mahal\nHappy China\nYori";
    public static final String offCampusAll = OFF_CAMPUS_BURGERS + "\n" + OFF_CAMPUS_SANDWICHES + "\n" +
            OFF_CAMPUS_PIZZAS + OFF_CAMPUS_OTHERS;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Where do you want to eat? " + "\n" + "On campus" + "\n" + "Off campus");
        String first = scanner.nextLine();
        System.out.println(first);
        if (first.equalsIgnoreCase("Off campus")) {
            System.out.println("Dietary restrictions? " + "y/n");
            String firsty = scanner.nextLine();
            if (firsty.equalsIgnoreCase("y")) {
                System.out.println("1 - Vegan" + "\n" + "2 - Vegetarian" + "\n" + "3 - Gluten-free");
                int dietaryrestriction = scanner.nextInt();
                if (dietaryrestriction == 1) {
                    System.out.println(OFF_CAMPUS_VEGAN);
                } else if (dietaryrestriction == 2) {
                    System.out.println(OFF_CAMPUS_VEGETARIAN);
                } else if (dietaryrestriction == 3) {
                    System.out.println(OFF_CAMPUS_GLUTEN_FREE);
                } else System.out.println("Error");
            }
            {
                if (firsty.equalsIgnoreCase("n")) {
                    System.out.println("Food preference? " + "y/n");
                    String secondy = scanner.nextLine();
                    if (secondy.equalsIgnoreCase("y")) {
                        System.out.println("1 - Burgers" + "\n" + "2 - Sandwiches" + "\n" + "3 - Pizzas" + "\n" + "4 - Others");
                        int foodpreference = scanner.nextInt();
                        if (foodpreference == 1) ;
                        System.out.println(OFF_CAMPUS_BURGERS);
                        if (foodpreference == 2) ;
                        System.out.println(OFF_CAMPUS_SANDWICHES);
                        if (foodpreference == 3) ;
                        System.out.println(OFF_CAMPUS_PIZZAS);
                        if (foodpreference == 4) ;
                        System.out.println(OFF_CAMPUS_OTHERS);
                    } else if (secondy.equalsIgnoreCase("n")) {
                        System.out.println(offCampusAll);

                    }
                }
            }

        } else if (first.equalsIgnoreCase("On campus")) {
            System.out.println("Dietary restrictions? " + "y/n");
            String first1y = scanner.nextLine();
            if (first1y.equalsIgnoreCase("y")) {
                System.out.println("1 - Vegan" + "\n" + "2 - Vegetarian" + "\n" + "3 - Gluten-free");
                int dietaryrestriction_l = scanner.nextInt();
                if (dietaryrestriction_l == 1) {
                    System.out.println(ON_CAMPUS_VEGAN);
                } else if (dietaryrestriction_l == 2) {
                    System.out.println(ON_CAMPUS_VEGETARIAN);
                } else if (dietaryrestriction_l == 3) {
                    System.out.println(ON_CAMPUS_GLUTEN_FREE);
                } else System.out.println("Error");
            } else if (first1y.equalsIgnoreCase("n")) {
                System.out.println("Food preference? " + "y/n");
                String secondyl = scanner.nextLine();
                if (secondyl.equalsIgnoreCase("y")) {
                    System.out.println("1 - Burgers" + "\n" + "2 - Sandwiches" + "\n" + "3 - Others");
                    int foodpreferencel = scanner.nextInt();
                    if (foodpreferencel == 1) {
                        System.out.println(ON_CAMPUS_BURGERS);
                    } else if (foodpreferencel == 2) {
                        System.out.println(ON_CAMPUS_SANDWICHES);
                    } else if (foodpreferencel == 3) {
                        System.out.println(ON_CAMPUS_OTHERS);
                    }
                } else if (secondyl.equalsIgnoreCase("n")) {
                    System.out.println(ON_CAMPUS_ALL);
                }
            } else System.out.println("Error");
        }
    }
}











