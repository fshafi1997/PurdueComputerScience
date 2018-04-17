import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdIn;
import java.util.*;

/**
 * How to compile the program?
 * javac -classpath .:stdlib.jar:algs4.jar Filter.java
 * How to run the program?
 * java -classpath .:stdlib.jar:algs4.jar Filter
 * <p>
 * Need to pass in the text file
 * java -classpath .:stdlib.jar:algs4.jar Filter < Filter1.txt
 */

public class Filter {
    static class Coordinates implements Comparable<Coordinates> {
        private int X;
        private int Y;

        public Coordinates() {
            this(0, 0);
        }

        public Coordinates(int X, int Y) {
            this.X = X;
            this.Y = Y;
        }

        public int getX() {
            return X;
        }

        public int getY() {
            return Y;
        }

        public void setX(int X) {
            this.X = X;
        }

        public void setY(int Y) {
            this.Y = Y;
        }

        public int compareTo(Coordinates o) {
            Coordinates coordinates = this;

            Coordinates compare2Object = o;
            if (coordinates.getX() == compare2Object.getX()) return 0;
            else if (coordinates.getX() < compare2Object.getX()) return -1;
            else return 1;
        }
    }


    public static void main(String[] args) {
        List<Coordinates> filtered = new ArrayList<Coordinates>();

        int sizeofList = StdIn.readInt();

        Coordinates[] coordinates = new Coordinates[sizeofList];

        int index = 0;
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            //System.out.println("pis " + p + " qis " + q);
            //multiplePoints.put(index, new Coordinates(p, q));
            coordinates[index] = new Coordinates(p, q);
            index++;
        }


        //TODO: fine now . Just make it work with StdIn
        MergeX.sort(coordinates);

        //Doing the filtering
        int biggest = 0;
        for (int i=coordinates.length-1;i>0;i--){
            if (i==coordinates.length-1){
                filtered.add(coordinates[i]);
                biggest = coordinates[i].getY();
            }
            if (coordinates[i-1].getY()>coordinates[i].getY() && coordinates[i-1].getY()>biggest){
                biggest = coordinates[i-1].getY();
                filtered.add(coordinates[i-1]);
                continue;
            }
        }


        //Printing After filtering
        for (int i=filtered.size()-1;i>=0;i--){
            System.out.println(filtered.get(i).getX() + " " + filtered.get(i).getY());
        }

    }

}
