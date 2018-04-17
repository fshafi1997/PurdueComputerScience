import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * How to compile the program?
 * javac -classpath .:stdlib.jar:algs4.jar Percolation.java
 * How to run the program?
 * java -classpath .:stdlib.jar:algs4.jar Percolation
 *
 * Need to pass in the text file for the coordiates and size
 * java -classpath .:stdlib.jar:algs4.jar Percolation < test5.txt
 */

public class Percolation {

    private WeightedQuickUnionUF quickUnionUF;
    private int size;
    //private boolean bottom = false;
    private int top;
    private boolean[][] isOpened;

    public Percolation(int N) {
        size = N;
        quickUnionUF = new WeightedQuickUnionUF(size * size +1);
        isOpened = new boolean[size][size];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                isOpened[i][j] = false;
            }
        }
        for (int i=0;i<size;i++){
            quickUnionUF.union(size*size, getIndexQF(i , size-1));
        }
    }

    private int getIndexQF(int i, int j) {
        return size * (j) + i;
    }

    /*private int[][] neighbouringCells(int i, int j) {
        return new int[][]{{i - 1, j}, {i, j + 1}, {i + 1, j}, {i, j - 1}};
    }*/

    private boolean areValidIndices(int i, int j) {
        return (i >= 0 && i <= size - 1 && j >= 0 && j <= size - 1);
    }

    private void indicesVerification(int i, int j) {
        if (!areValidIndices(i, j)) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    public void open(int i, int j) {
        indicesVerification(i, j);
        //System.out.println(i + " " + j);
        isOpened[i][j] = true;
        if (j == size - 1) {
            if (i > 0 && i < size - 1) {
                if (isOpen(i - 1, j)) {
                    quickUnionUF.union(getIndexQF(i, j), getIndexQF(i - 1, j));
                }
                if (isOpen(i + 1, j)) {
                    quickUnionUF.union(getIndexQF(i, j), getIndexQF(i + 1, j));
                }
                if (isOpen(i, j - 1)) {
                    quickUnionUF.union(getIndexQF(i, j), getIndexQF(i, j - 1));
                }
            }
            if (i == 0) {
                if (isOpen(i + 1, j)) {
                    quickUnionUF.union(getIndexQF(i, j), getIndexQF(i + 1, j));
                }
                if (isOpen(i, j - 1)) {
                    quickUnionUF.union(getIndexQF(i, j), getIndexQF(i, j - 1));
                }
            }
            if (i == size - 1) {
                if (isOpen(i - 1, j)) {
                    quickUnionUF.union(getIndexQF(i, j), getIndexQF(i - 1, j));
                }
                if (isOpen(i, j - 1)) {
                    quickUnionUF.union(getIndexQF(i, j), getIndexQF(i, j - 1));
                }
            }
        }
        if (j < size - 1 && j > 0) {
            if (i > 0 && i < size - 1) {
                if (isOpen(i - 1, j)) {
                    quickUnionUF.union(getIndexQF(i, j), getIndexQF(i - 1, j));
                }
                if (isOpen(i + 1, j)) {
                    quickUnionUF.union(getIndexQF(i, j), getIndexQF(i + 1, j));
                }
                if (isOpen(i, j - 1)) {
                    quickUnionUF.union(getIndexQF(i, j), getIndexQF(i, j - 1));
                }
                if (isOpen(i, j + 1)) {
                    quickUnionUF.union(getIndexQF(i, j), getIndexQF(i, j + 1));
                }
            }
            if (i == 0) {
                if (isOpen(i + 1, j)) {
                    quickUnionUF.union(getIndexQF(i, j), getIndexQF(i + 1, j));
                }
                if (isOpen(i, j - 1)) {
                    quickUnionUF.union(getIndexQF(i, j), getIndexQF(i, j - 1));
                }
                if (isOpen(i, j + 1)) {
                    quickUnionUF.union(getIndexQF(i, j), getIndexQF(i, j + 1));
                }
            }
            if (i == size - 1) {
                if (isOpen(i - 1, j)) {
                    quickUnionUF.union(getIndexQF(i, j), getIndexQF(i - 1, j));
                }
                if (isOpen(i, j - 1)) {
                    quickUnionUF.union(getIndexQF(i, j), getIndexQF(i, j - 1));
                }
                if (isOpen(i, j + 1)) {
                    quickUnionUF.union(getIndexQF(i, j), getIndexQF(i, j + 1));
                }
            }
        }
        if (j == 0) {
            if (i > 0 && i < size - 1) {
                if (isOpen(i - 1, j)) {
                    quickUnionUF.union(getIndexQF(i, j), getIndexQF(i - 1, j));
                }
                if (isOpen(i + 1, j)) {
                    quickUnionUF.union(getIndexQF(i, j), getIndexQF(i + 1, j));
                }
                if (isOpen(i, j + 1)) {
                    quickUnionUF.union(getIndexQF(i, j), getIndexQF(i, j + 1));
                }
            }
            if (i == 0) {
                if (isOpen(i + 1, j)) {
                    quickUnionUF.union(getIndexQF(i, j), getIndexQF(i + 1, j));
                }
                if (isOpen(i, j + 1)) {
                    quickUnionUF.union(getIndexQF(i, j), getIndexQF(i, j + 1));
                }
            }
            if (i == size - 1) {
                if (isOpen(i - 1, j)) {
                    quickUnionUF.union(getIndexQF(i, j), getIndexQF(i - 1, j));
                }
                if (isOpen(i, j + 1)) {
                    quickUnionUF.union(getIndexQF(i, j), getIndexQF(i, j + 1));
                }
            }
        }
    }

    public boolean isOpen(int i, int j) {
        return isOpened[i][j];
    }

    public boolean isFull(int i, int j) {
        if (i >= 0 && i <= size && j >= 0 && j <= size) {
            if (isOpen(i,j)) {
                return quickUnionUF.connected(size * size, getIndexQF(i, j));
            }
            return false;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public boolean percolates() {
        for (int i=0,j=0;i<size;i++){
            if (quickUnionUF.connected(size*size, getIndexQF(i,j))==true){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int N = StdIn.readInt();
        Percolation percolation = new Percolation(N);
        while (!StdIn.isEmpty()){
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            percolation.open(p,q);
        }
        if (percolation.percolates() == true) {
            StdOut.println("Yes");
        }
        else{
            StdOut.println("No");
        }
    }
}
