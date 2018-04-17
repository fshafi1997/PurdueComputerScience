import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private double[] sitesOpenedForPercolation;
    private static String type;
    private static double[] time;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Grid size and total number of tests must be > 1");
        }
        sitesOpenedForPercolation = new double[T];
        time = new double[T];

        if (type.equals("fast")) {
            for (int i = 0; i < T; i++) {
                Stopwatch watch = new Stopwatch();
                int openedSites = 0;
                Percolation p = new Percolation(N);
                while (!p.percolates()) {
                    int row = (int) StdRandom.uniform(0, N);
                    int column = (int) StdRandom.uniform(0, N);
                    if (!p.isOpen(row, column)) {
                        p.open(row, column);
                        openedSites++;
                    }
                }
                time[i] = watch.elapsedTime();
                sitesOpenedForPercolation[i] = ((double) openedSites) / (N * N);
            }
        }
        else{
            for (int i = 0; i < T; i++) {
                Stopwatch watch2 = new Stopwatch();
                int openedSites = 0;
                PercolationSlow p = new PercolationSlow(N);
                while (!p.percolates()) {
                    int row = (int) StdRandom.uniform(0, N);
                    int column = (int) StdRandom.uniform(0, N);
                    if (!p.isOpen(row, column)) {
                        p.open(row, column);
                        openedSites++;
                    }
                }
                time[i] = watch2.elapsedTime();
                sitesOpenedForPercolation[i] = ((double) openedSites) / (N * N);
            }
        }
    }

    public double mean() {
        return StdStats.mean(sitesOpenedForPercolation);
    }

    public double stddev() {
        return StdStats.stddev(sitesOpenedForPercolation);
    }



    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        type = args[2];
        PercolationStats percStats = new PercolationStats(N, T);
        StdOut.println("mean threshold=" + percStats.mean());
        StdOut.println("stddev=" + percStats.stddev());
        StdOut.println("time=" + stopwatch.elapsedTime());
        StdOut.println("mean time=" + StdStats.mean(time));
        StdOut.println("stddev time=" + (StdStats.stddev(time)));
    }
}
