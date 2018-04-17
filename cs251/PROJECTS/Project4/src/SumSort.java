import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * How to compile the program?
 * javac -classpath .:stdlib.jar:algs4.jar SumSort.java
 * How to run the program?
 * java -classpath .:stdlib.jar:algs4.jar SumSort
 * <p>
 * Need to pass in the text file
 * java -classpath .:stdlib.jar:algs4.jar SumSort < values100.txt
 */


public class SumSort {
    static class pairsForSum implements Comparable<pairsForSum> {
        private int first, second, indexFirst, indexSecond;
        int sum;

        public pairsForSum() {
            this(0, 0, 0, 0);
        }

        public pairsForSum(int first, int second, int indexFirst, int indexSecond) {
            this.first = first;
            this.second = second;
            this.indexFirst = indexFirst;
            this.indexSecond = indexSecond;
            this.sum = first + second;
        }

        public int getSum() {
            return sum;
        }

        public int getFirst() {
            return first;
        }

        public int getIndexFirst() {
            return indexFirst;
        }

        public int getIndexSecond() {
            return indexSecond;
        }

        public int getSecond() {
            return second;
        }

        public int compareTo(pairsForSum o) {
            return Comparator.comparing(pairsForSum::getSum).compare(this, o);
        }
    }

    static class forReturn implements Comparable<forReturn> {
        private int i0, i1, i2, i3;

        public forReturn() {
            this.i0 = 0;
            this.i1 = 0;
            this.i2 = 0;
            this.i3 = 0;
        }

        public forReturn(int i0, int i1, int i2, int i3) {
            this.i0 = i0;
            this.i1 = i1;
            this.i2 = i2;
            this.i3 = i3;
        }

        public int getI0() {
            return i0;
        }

        public int getI1() {
            return i1;
        }

        public int getI2() {
            return i2;
        }

        public int getI3() {
            return i3;
        }

        public int compareTo(forReturn o) {
            return Comparator.comparing(forReturn::getI0).thenComparing(forReturn::getI1).thenComparingInt(forReturn::getI2).thenComparing(forReturn::getI3).compare(this, o);
        }
    }


    public static void main(String[] args) {
        //Stopwatch stopwatch = new Stopwatch();
        //Things read in
        int sizeOfArray = StdIn.readInt();
        int[] array = new int[sizeOfArray];
        int index = 0;
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            array[index] = p;
            index++;
        }

        List<pairsForSum> listOfPairs = new ArrayList<pairsForSum>();


        //Making all the pairs
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                listOfPairs.add(new pairsForSum(array[i], array[j], i, j));
            }
        }
        //System.out.println(listOfPairs.size());


        //Sorting
        Collections.sort(listOfPairs);  //Sorting according to the sums of the pairs (equal sums come together)

        List<forReturn> listWithQuads = new ArrayList<forReturn>();

        //Testing
        int counter = 0;
        for (int i = 0; i < listOfPairs.size() - 1; i++) {
            int sum = listOfPairs.get(i).getSum();
            int j = i + 1;
            while (sum == listOfPairs.get(j).getSum()) {
                if (j < listOfPairs.size() - 1) {
                    if (listOfPairs.get(i).getIndexFirst() != listOfPairs.get(j).getIndexFirst() && listOfPairs.get(i).getIndexFirst() != listOfPairs.get(j).getIndexSecond() && listOfPairs.get(i).getIndexFirst()<listOfPairs.get(i).getIndexSecond()) {
                        if (listOfPairs.get(i).getIndexSecond() != listOfPairs.get(j).getIndexFirst() && listOfPairs.get(i).getIndexSecond() != listOfPairs.get(j).getIndexSecond() && listOfPairs.get(j).getIndexFirst()<listOfPairs.get(j).getIndexSecond()) {
                            listWithQuads.add(new forReturn(listOfPairs.get(i).getIndexFirst(), listOfPairs.get(i).getIndexSecond(), listOfPairs.get(j).getIndexFirst(), listOfPairs.get(j).getIndexSecond()));
                            //StdOut.println("( " + listOfPairs.get(i).getIndexFirst() + ", " + listOfPairs.get(i).getIndexSecond() + ", " + listOfPairs.get(j).getIndexFirst() + ", " + listOfPairs.get(j).getIndexSecond() + " ) " + "[" + listOfPairs.get(i).getSum() + "]");
                            counter++;
                        }
                    }
                }
                j++;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(counter+"\n");
        //TODO: need to learn how to do the final sorting
        Collections.sort(listWithQuads);
        for (int i=0;i<listWithQuads.size();i++){
            stringBuilder.append(listWithQuads.get(i).getI0() + " " + listWithQuads.get(i).getI1() + " " + listWithQuads.get(i).getI2() + " " + listWithQuads.get(i).getI3()+"\n");
        }
        StdOut.println(stringBuilder);
        //System.out.println(stopwatch.elapsedTime());
    }
}