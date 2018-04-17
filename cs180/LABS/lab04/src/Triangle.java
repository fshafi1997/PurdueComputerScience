/**
 * Created by fshafi on 9/16/16.
 */
import java.util.*;
import java.lang.*;
public class Triangle {
    private double side;

//    Constructor
    public Triangle(double sidelength) {
        side = sidelength;
    }

    double getPerimeter(){
        double perimeter  = 3*side;
        return perimeter;
    }
    double getArea(){
        double area = 0.5*side*side*Math.sin(1.0472);
        return area;
    }

}
