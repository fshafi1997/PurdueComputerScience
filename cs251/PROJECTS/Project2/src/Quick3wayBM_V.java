import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Quick3wayBM_V {
    public static void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        int n = hi - lo;
        int flag = 0;
        int flag2 = 0;

        if (n <= 8) {
            insertionSort(a, lo, hi);
            show2(a);
            return;
        }

        else if (n <= 40) {
            int m = median_of_3(a, lo, lo + n/2, lo +(2*(n/2)));
            flag = 1;
            exchange(a, lo, m);
        }

        else  {
            int m1 = median_of_3(a, lo, lo + n/8, lo + n/8 + n/8);
            int m2 = median_of_3(a, lo + (3*(n/8)), lo + (4*(n/8)), lo + (5*(n/8)));
            int m3 = median_of_3(a, lo + (6*(n/8)), lo + (7*(n/8)), lo + (8*(n/8)));
            int ninther = median_of_3(a, m1, m2, m3);
            flag2 = 1;
            exchange(a, lo, ninther);
        }

        int i = lo, j = hi+1;
        int p = lo, q = hi+1;
        Comparable v = a[lo];
        while(true){
            while (less(a[++i],v))
                if (i == hi) break;
            while (less(v,a[--j]));
            if (j == lo) break;

            if (i>j){
                break;
            }
            if (i == j && equals(a[i], v) && equals(a[j],v)) {
                exchange(a, --q, j);
                --q;
            }
            if (i==j){
                j--;
                break;
            }

            exchange(a, i, j);
            //show2(a);
            if (equals(a[i], v)) exchange(a, ++p, i);
            if (equals(a[j], v)) exchange(a, --q, j);
            //show2(a);

        }


        for (int k = lo; k < p; k++)
            exchange(a, k, j--);
        for (int k = j+1; k > q; k++)
            exchange(a, k, j++);


        show2(a);
        sort(a, lo, j);
        sort(a, i, hi);
    }

    private static void insertionSort(Comparable[] a, int lo, int hi) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(a[j], a[j-1]); j--)
                exchange(a, j, j-1);
    }


    private static int median_of_3(Comparable[] a, int i, int j, int k) {
        if (less(a[i],a[j]) == true){
            if (less(a[j],a[k]) == true){
                return j;
            }
            else if (less(a[i],a[k]) == true){
                return k;
            }
            else return i;
        }
        else{
            if (less(a[k],a[j]) == true){
                return j;
            }
            else if (less(a[k],a[i]) == true){
                return k;
            }
            else return i;
        }
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static boolean equals(Comparable v, Comparable w) {
        return v.compareTo(w) == 0;
    }

    private static void exchange(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

    public static void main(String[] args) {
        int N = StdIn.readInt();
        Comparable[] a = new Comparable[N];
        for(int i = 0; !StdIn.isEmpty(); i++){
            a[i] = StdIn.readInt();
        }
        show2(a);
        Quick3wayBM_V.sort(a);
        assert isSorted(a);
        show(a);
        System.out.println(isSorted(a));
    }

    private static void show2(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            if (i != (a.length - 1)) {
                StdOut.print(a[i] + " ");
            } else StdOut.println(a[i]);
        }
    }
}
