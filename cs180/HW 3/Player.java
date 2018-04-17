import java.util.*;
import java.lang.*;
public class Player {
    private String name;
    private double positionX;
    private double positionY;
    //constructors
    public Player(String name){
	this.name = name;
	positionX = 0;
	positionY = 0;
    }
    public Player(String name, double positionX, double positionY){
    this.name = name;
    this.positionX = positionX;
    this.positionY = positionY;
    }
    //accessor for name
    public String getName(){
	return this.name;
    }
    public double getPositionX(){
	return this.positionX;
    }
    public double getPositionY(){
	return this.positionY;
    }

    //mutator
    public void setName(String name){
	this.name = name;
    }
    //method prototypes
    public void moveX(double offsetX){
	positionX = positionX + offsetX;
    }
    public void moveY(double offsetY){
	positionY = positionY + offsetY;
    }
    public void moveInDirection(double theta, double distance){
    }
    public boolean hasSamePositionAs(Player player){
	return true;
	}
    //main method
    public static void main(String[] args){
	Player player1, player2;
	Scanner scanner = new Scanner(System.in);
	System.out.println("What is the name of player1: ");
	String firstname = scanner.next();
	System.out.println("Enter the starting xPosition of " + firstname);
	double startpositionx1 = scanner.nextDouble();
	System.out.println("Enter the starting yPosition of " + firstname);
        double startpositiony1 = scanner.nextDouble();
	player1 = new Player(firstname, startpositionx1, startpositiony1);
	System.out.println("What is the name of player 2: ");
	String secondname = scanner.next();
	System.out.println("Enter the starting xPosition of " + secondname);
	double startpositionx2 = scanner.nextDouble();
	System.out.println("Enter the starting yPosition of " + secondname);
	double startpositiony2 = scanner.nextDouble();
	player2 = new Player(secondname, startpositionx2, startpositiony2);
	System.out.println("Enter " + firstname + "'s" + " horizontal move offset");
	double offsetX1 = scanner.nextDouble();
	player1.moveX(offsetX1);
	System.out.println("Enter " + firstname + "'s" + " vertical move offset");
	double offsetY1 = scanner.nextDouble();
	player1.moveY(offsetY1);
	System.out.println("Enter " + firstname + "'s" + " diagnol move angle degrees");
	double aliceAngle = scanner.nextDouble();
	aliceAngle = Math.toRadians(aliceAngle);
	System.out.println("Enter " + firstname + "'s" + " diagnol move distance");
	double aliceDiagnol = scanner.nextDouble();
	System.out.println("Enter " + secondname + "'s" + " horizontal move offset");
	double offsetX2 = scanner.nextDouble();
	player2.moveX(offsetX2);
	System.out.println("Enter " + secondname + "'s" + " vertical move offset");
	double offsetY2 = scanner.nextDouble();
	player2.moveY(offsetY2);
	System.out.println("Enter " + secondname + "'s" + " diagnol move angle degrees");
	double bobAngle = scanner.nextDouble();
	bobAngle = Math.toRadians(bobAngle);
	System.out.println("Enter " + secondname + "'s" + " diagnol move distance");
	double bobDiagnol = scanner.nextDouble();
	double X1 = startpositionx1 + offsetX1 + (aliceDiagnol * Math.cos(aliceAngle));
	double Y1 = startpositiony1 + offsetY1 + (aliceDiagnol * Math.sin(aliceAngle));
	double X2 = startpositionx2 + offsetX2 + (bobDiagnol * Math.cos(bobAngle));
	double Y2 = startpositiony2 + offsetY2 + (bobDiagnol * Math.sin(bobAngle));
	System.out.println(firstname + "'s " + "position: " + "(" + X1 + ", " + Y1 + ")");
	System.out.println(secondname + "'s " + "position: " + "(" + X2 + ", " + Y2 + ")");
	double distanceBetween = X1-X2;
	System.out.println("Distance between players: " + distanceBetween);
	if(distanceBetween<0.001)System.out.println("Same position: true");
	else System.out.println("Same position: false");

    }
}
