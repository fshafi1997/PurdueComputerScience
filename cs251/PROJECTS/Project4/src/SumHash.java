import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import javafx.scene.paint.Stop;

import java.util.*;

public class SumHash {
    static class pairsForSum {
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

        HashMap<Integer, List<pairsForSum>> map = new HashMap<Integer, List<pairsForSum>>();

        //Putting all pairs in a hashtable with key is sum and value is a hashset
        //The two forloops go over all possible pairs
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                int sum = array[i] + array[j]; //Sum is the d (key for map)
                pairsForSum toAdd = new pairsForSum(array[i], array[j], i, j);
                if (map.containsKey(sum)) {   //if sum is a key stored in the map then we get the linkedList at the key(sum) and add to it the pair i.e toAdd
                    map.get(sum).add(toAdd);
                } else {  //If sum is no a key stored in the map then we make a new linkedlist, add to it the pair and put the ll in the map with sum as the key
                    List<pairsForSum> Sd = new ArrayList<pairsForSum>();
                    Sd.add(toAdd);
                    map.put(sum, Sd);
                }
            }
        }

        int counter = 0;

        List<forReturn> listWithQuads = new ArrayList<forReturn>();

        Set<Integer> keys = map.keySet();  //These are the values of the hashMap i.e sums
        for (Integer key : keys) {    //For every sum this is getting the linked list associated with it
            List<pairsForSum> x = map.get(key);
            //LinkedList<pairsForSum> x = map.get(key);
            Iterator<pairsForSum> pairs1 = x.iterator();  //This is to iterate over that linked list
            int countforItr = 0;
            int countforitr2;
            while (pairs1.hasNext()) {   //This one for every i,j
                countforitr2 = 0;
                pairsForSum pair1 = pairs1.next();
                countforItr++;
                Iterator<pairsForSum> pairs2 = x.iterator(); //2nd iterator
                while (countforitr2 < countforItr) {   //make the second itr go forward
                    pairs2.next();
                    countforitr2++;
                }
                while (pairs2.hasNext()) {   //This one for every k,l
                    pairsForSum pair2 = pairs2.next();
                    if (pair1.getIndexFirst() != pair2.getIndexFirst() && pair1.getIndexFirst() != pair2.getIndexSecond() && pair1.getIndexFirst()<pair1.getIndexSecond()) {
                        if (pair1.getIndexSecond() != pair2.getIndexFirst() && pair1.getIndexSecond() != pair2.getIndexSecond() && pair2.getIndexFirst()<pair2.getIndexSecond()) {
                            listWithQuads.add(new forReturn(pair1.getIndexFirst(), pair1.getIndexSecond(), pair2.getIndexFirst(), pair2.getIndexSecond()));
                            //StdOut.println(pair1.getIndexFirst() + " " + pair1.getIndexSecond() + " " + pair2.getIndexFirst() + " " + pair2.getIndexSecond());
                            counter++;
                        }
                    }
                }
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

        //System.out.println(counter);
        //System.out.println(stopwatch.elapsedTime());
    }
}
