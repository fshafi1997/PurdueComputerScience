/**
 * Created by fshafi on 9/16/16.
 */
import java.util.*;
import java.lang.*;

public class Rectangle {
    private double length, width;

    public Rectangle(double lengthrectangle, double widthrectangle){
        length = lengthrectangle;
        width = widthrectangle;
    }
    double getPerimeter(){
        double perimeter = (2*length)+(2*width);
        return perimeter;
    }
    double getArea(){
        double area = width*length;
        return area;
    }

}
