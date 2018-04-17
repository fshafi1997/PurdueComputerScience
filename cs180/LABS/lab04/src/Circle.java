/**
 * Created by fshafi on 9/16/16.
 */
import java.lang.*;
import java.util.*;

public class Circle {

    private double radius;

    public Circle(double radius){

        this.radius = radius;
    }
    double getCircumfrance(){
        double circumfrance = 2*Math.PI*radius;
        return circumfrance;

    }
    double getArea(){
        double area = Math.PI * Math.pow(radius,2);
                return area;
    }

}
