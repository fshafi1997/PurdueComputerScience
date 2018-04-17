import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;

import java.awt.*;

public class PercolationVisualizer {
    public static void setUpGrid(Percolation perc, int size) {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(0, size);
        StdDraw.setYscale(0, size);
        StdDraw.filledSquare(size/2.0, size/2.0, size/2.0);
        int openedSites = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (perc.isFull(i, j)) {
                    StdDraw.setPenColor(StdDraw.BOOK_BLUE);
                    openedSites++;
                }
                else if (perc.isOpen(i, j)) {
                    StdDraw.setPenColor(StdDraw.WHITE);
                    openedSites++;
                }
                else {
                    StdDraw.setPenColor(StdDraw.BLACK);
                }
                StdDraw.filledSquare(i + 0.5, j + 0.5, 0.45);
            }
        }

        StdDraw.setFont(new Font("SansSerif", Font.BOLD, 12));
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(size*.25, .025*-size, openedSites + " Open sites");
        if (perc.percolates()){
            StdDraw.text(size*.75, .025*-size, "Percolates");
        }
        else{
            StdDraw.text(size*.75, .025*-size, "Does not percolate");
        }
    }

    public static void main(String[] args) {
        int N = StdIn.readInt();
        Percolation percolation = new Percolation(N);

        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();

            if (!percolation.isOpen(p, q))
                percolation.open(p, q);
            setUpGrid(percolation, N);
            StdDraw.show(100);
        }
    }
}