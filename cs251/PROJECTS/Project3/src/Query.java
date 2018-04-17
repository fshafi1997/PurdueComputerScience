import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.List;

/**
 * How to compile the program?
 * javac -classpath .:stdlib.jar:algs4.jar Query.java
 * How to run the program?
 * java -classpath .:stdlib.jar:algs4.jar Query
 * <p>
 * Need to pass in the text file
 * java -classpath .:stdlib.jar:algs4.jar Query < query1.txt
 */

public class Query {
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

    public static void printTheSmallFiltered(List<Coordinates> list){
        Coordinates[] theList = list.toArray(new Coordinates[0]);
        MergeX.sort(theList);
        StringBuilder sb = new StringBuilder();
        if (theList.length == 0){
            sb.append("none\n");
        }
        else {
            for (int i = 0; i < theList.length; i++) {
                sb.append(theList[i].getX() + " " + theList[i].getY() + "\n");
            }
        }
        System.out.print(sb);
    }

    public static void main(String[] args) {
        //For time
        long startTime = System.currentTimeMillis();

        List<Coordinates> filtered = new ArrayList<Coordinates>();
        List<Coordinates> queries = new ArrayList<Coordinates>();



        int sizeofList = StdIn.readInt();

        Coordinates[] coordinates = new Coordinates[sizeofList];

        int index = 0;
        while (index<sizeofList) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            coordinates[index] = new Coordinates(p, q);
            index++;
        }

        //Sorting the array according to x coordinates
        MergeX.sort(coordinates);


        int sizeofQueries = StdIn.readInt();
        index = 0;
        Coordinates[] forqueries = new Coordinates[sizeofQueries];
        while (index<sizeofQueries) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            forqueries[index] = new Coordinates(p, q);
            queries.add(forqueries[index]);
            index++;
        }

        int counter =0;

        while (counter<sizeofQueries) {
            filtered.clear();
            for (int i = coordinates.length - 1; i >= 0; i--) {
                if (coordinates[i].getX() > queries.get(counter).getX() && coordinates[i].getY() > queries.get(counter).getY()) {
                    filtered.add(coordinates[i]);
                }
            }
            printTheSmallFiltered(filtered);
            counter++;
        }


        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Time is " + totalTime);

    }

}
