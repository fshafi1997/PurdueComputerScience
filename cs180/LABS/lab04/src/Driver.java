/**
 * Created by fshafi on 9/16/16.
 */
import java.util.*;
public class Driver {
    public static void main(String[] args){
        Scanner myScanner = new Scanner(System.in);
        System.out.println("Enter the circle's radius: ");
        double radiusofcircle = myScanner.nextDouble();
        System.out.println("Enter side of triangle: ");
        double sideoftriangle = myScanner.nextDouble();
        System.out.println("Enter length of rectangle: ");
        double lengthofrectangle = myScanner.nextDouble();
        System.out.println("Enter width of rectangle: ");
        double widthofrectangle = myScanner.nextDouble();

        Circle C1 = new Circle(radiusofcircle);
        Triangle T1 = new Triangle(sideoftriangle);
        Rectangle R1 = new Rectangle(lengthofrectangle,widthofrectangle);

        double circumfrance = C1.getCircumfrance();
        double circlearea = C1.getArea();
        double triangleparameter = T1.getPerimeter();
        double trianglearea = T1.getArea();
        double rectangleparamtere = R1.getPerimeter();
        double rectanglearea = R1.getArea();

        System.out.println("Circumfrance of circle: " + circumfrance);
        System.out.println("Area of circle: " + circlearea);
        System.out.println("Parameter of triangle: " + triangleparameter);
        System.out.println("Area of triangle: " + trianglearea);
        System.out.println("Parameter of rectangle: " + rectangleparamtere);
        System.out.println("Area of rectangle: " + rectanglearea);
    }
}
