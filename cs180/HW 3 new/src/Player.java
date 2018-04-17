import java.util.*;
import java.lang.*;
public class Player {
    //Player class data members
    private String name;
    private double positionX;
    private double positionY;

    //Constructors
    public Player(String name){
        this.name = name;
        this.positionX = 0;
        this.positionY = 0;
    }
    public Player(String name, double positionX, double positionY){
        this.name = name;
        this.positionX = positionX;
        this.positionY = positionY;
    }


    //Methods (accessors)
    public String getName(){
        return name;
    }
    public double getPositionX(){
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    //Methods (mutators)
    public void setName(String name){
        this.name = name;
    }

    //Further methods
    public void moveX(double offsetX){
        double newPositonx = offsetX + positionX;
    }
    public void moveY(double offsetY){
        double newPositiony = offsetY + positionY;
    }
    public void moveInDirection(double theta, double distance){
        positionY = positionY + (distance * Math.sin(Math.toRadians(theta)));
        positionX = positionX + (distance * Math.cos(Math.toRadians(theta)));
    }
    public boolean hasSamePositionAs(Player player){
        return true;

    }

    //Main method
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is the name of player 1: ");
        String Name1 = scanner.next();
        System.out.println("Enter the starting xPosition of " + Name1);
        double X1 = scanner.nextDouble();
        System.out.println("Enter the starting yPosition of " + Name1);
        double Y1 = scanner.nextDouble();
        Player player1 = new Player(Name1, X1, Y1);
        player1.moveX(X1);
        player1.moveY(Y1);
        System.out.println("What is the name of player 2: ");
        String Name2 = scanner.next();
        System.out.println("Enter the starting xPosition of " + Name2);
        double X2 = scanner.nextDouble();
        System.out.println("Enter the starting yPosition of " + Name2);
        double Y2 = scanner.nextDouble();
        Player player2 = new Player(Name2, X2, Y2);
        System.out.println("Enter " + Name1 + "'s " + "horizontal move offset");
        double offsetx1 = scanner.nextDouble();
        System.out.println("Enter " + Name1 + "'s " + "vertical move offset");
        double offsety1 = scanner.nextDouble();
        System.out.println("Enter " + Name1 + "'s " + "diagnol move angle degrees");
        double angle1 = scanner.nextDouble();
        System.out.println("Enter " + Name1 + "'s " + "diagnol move distance");
        double diagnol1 = scanner.nextDouble();
        player1.moveInDirection(angle1, diagnol1);
        System.out.println("Enter " + Name2 + "'s " + "horizontal move offset");
        double offsetx2 = scanner.nextDouble();
        System.out.println("Enter " + Name2 + "'s " + "vertical move offset");
        double offsety2 = scanner.nextDouble();
        System.out.println("Enter " + Name2 + "'s " + "diagnol move angle degrees");
        double angle2 = scanner.nextDouble();
        System.out.println("Enter " + Name2 + "'s " + "diagnol move distance");
        double diagnol2 = scanner.nextDouble();
        player2.moveX(X2);
        player2.moveY(Y2);
        player2.moveInDirection(angle2, diagnol2);
        double X11 = player1.getPositionX();
        double Y11 = player1.getPositionY();
        double X22 = player2.getPositionX();
        double Y22 = player2.getPositionY();

        System.out.println(Name1 + "'s " + "position: " + "(" + X11 + "," + Y11 + ")");
        System.out.println(Name2 + "'s " + "position: " + "(" + X22 + "," + Y22 + ")");



        double distancebetween = Math.sqrt(Math.pow((X22-X11),2)+Math.pow((Y22-Y11),2));
        System.out.println("distance between players: " + distancebetween);

        if((X11==X22)&&(Y11==Y22))System.out.println("Same postion: true");
                else System.out.println("Same position: false");







    }

}
