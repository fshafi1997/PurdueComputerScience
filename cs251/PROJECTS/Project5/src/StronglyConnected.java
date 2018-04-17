import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.StdOut;
/**
 * How to compile the program?
 * compile Graph class before running this one
 * javac -classpath .:stdlib.jar:algs4.jar StronglyConnected.java
 *
 * Need to pass in the text file as an argument to run
 * The constructor is taking the input stream
 * Api used is from algs4 changed what was needed
 *
 * To run
 * java -classpath .:stdlib.jar:algs4.jar StronglyConnected input0.txt
 */

import java.util.*;
import java.util.Stack;

public class StronglyConnected {
    private static final String NEWLINE = System.getProperty("line.separator");

    static StronglyConnected G;
    private static int V;
    private int E;
    private static Bag<Integer>[] adj;
    static List<Integer> listOfVertices;
    static List<List<Integer>> vertices = new ArrayList<List<Integer>>();

    static Graph g1;

    static Stack<Integer> post = new Stack<Integer>();
    static boolean[] visited;

    static Digraph digraph;

    static class pairs {
        private int v, w;
        private boolean connection;

        pairs(int v, int w) {
            this.v = v;
            this.w = w;
            this.connection = false;
        }

        public void setConnection(boolean connection) {
            this.connection = connection;
        }

        public boolean isConnection() {
            return connection;
        }

        public int getV() {
            return v;
        }

        public int getW() {
            return w;
        }
    }

    /**
     * Initializes an empty graph with {@code V} vertices and 0 edges.
     * param V the number of vertices
     *
     * @param V number of vertices
     * @throws IllegalArgumentException if {@code V < 0}
     */
    public StronglyConnected(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
    }

    /**
     * Initializes a graph from the specified input stream.
     * The format is the number of vertices <em>V</em>,
     * followed by the number of edges <em>E</em>,
     * followed by <em>E</em> pairs of vertices, with each entry separated by whitespace.
     *
     * @param in the input stream
     * @throws IllegalArgumentException if the endpoints of any edge are not in prescribed range
     * @throws IllegalArgumentException if the number of vertices or edges is negative
     * @throws IllegalArgumentException if the input stream is in the wrong format
     */
    public StronglyConnected(In in) {
        try {
            this.V = in.readInt();
            if (V < 0) throw new IllegalArgumentException("number of vertices in a Graph must be nonnegative");
            adj = (Bag<Integer>[]) new Bag[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new Bag<Integer>();
            }
            int E = in.readInt();
            if (E < 0) throw new IllegalArgumentException("number of edges in a Graph must be nonnegative");
            for (int i = 0; i < E; i++) {
                int v = in.readInt();
                int w = in.readInt();
                validateVertex(v);
                validateVertex(w);
                addEdge(v, w);
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in Graph constructor", e);
        }
    }


    /**
     * Initializes a new graph that is a deep copy of {@code G}.
     *
     * @param G the graph to copy
     */
    public StronglyConnected(StronglyConnected G) {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<Integer> reverse = new Stack<Integer>();
            for (int w : G.adj[v]) {
                reverse.push(w);
            }
            for (int w : reverse) {
                adj[v].add(w);
            }
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        E++;
        adj[v].add(w);
        adj[w].add(v);
    }


    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    /**
     * Returns the degree of vertex {@code v}.
     *
     * @param v the vertex
     * @return the degree of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    //My sorted adjList of the graph is made here (vertices)
    public static void sortBag() {
        for (int v = 0; v < V; v++) {
            listOfVertices = new ArrayList<Integer>();
            for (int w : adj[v]) {
                listOfVertices.add(w);
            }
            Collections.sort(listOfVertices);
            vertices.add(listOfVertices);
        }
    }

    /**
     * passed v so going through list of w looking for v
     */
    private static boolean checkInDigraph(StronglyConnected digraph, int v, int w) {
        for (int checkWw : digraph.adj(w)) {
            if (checkWw == v) {
                return true;
            }
        }
        return false;
    }

    //Checking index of the v in the subList
    private static int listIndexOf(List<Integer> list, int toCompare) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == toCompare) {
                return i;
            }
        }
        return -1;
    }

    static List<pairs> pairs2 = new ArrayList<pairs>();

    //Starting vertex is n-1
    private static void dfs_rec(List<List<Integer>> adjLists, boolean[] visited, int v, Stack<Integer> stack) {
        visited[v] = true;
        stack.push(v);
        //System.out.print(v + " ");
        //for (int w : adjLists.get(v)) {
        for (int j = 0; j < adjLists.get(v).size(); j++) {
            int w = adjLists.get(v).get(j);
            if (w > -1 && checkInDigraph(G, v, w) == true) {
                List<Integer> subAdj = vertices.get(w);
                int x = listIndexOf(subAdj, v);
                if (x >= 0) {
                    if (vertices.get(w) == vertices.get(v)) {
                        //System.out.println("in here");
                        vertices.get(v).set(j + 1, -1);
                    }
                    vertices.get(v).set(j, -1);
                    vertices.get(w).set(x, -1);
                    g1.addEdge(v, w);
                    digraph.addEdge(v, w);
                    pairs2.add(new pairs(v, w));

                }
                if (!visited[w]) {
                    dfs_rec(adjLists, visited, w, stack);
                }
            }
        }
    }


    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w = 0; w < vertices.get(v).size(); w++) {
                s.append(vertices.get(v).get(w) + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }


    public static void main(String[] args) {
        In in = new In();
        G = new StronglyConnected(in);
        sortBag();
        visited = new boolean[vertices.size()];
        digraph = new Digraph(V);
        g1 = new Graph(V);
        dfs_rec(vertices, visited, vertices.size() - 1, post);
        //StdOut.println(G);
        //System.out.println(post);
        //System.out.println();
        //System.out.println(digraph);


        KosarajuSharirSCC kosarajuSharirSCC = new KosarajuSharirSCC(digraph);
        int stronglyConnected = kosarajuSharirSCC.count();

        int[] array = new int[stronglyConnected];
        for (int i = 0; i < vertices.size(); i++) {
            array[kosarajuSharirSCC.id(i)] = i;
        }
        //System.out.println(digraph);

        for (int i = 0; i < array.length; i++) {
            DepthFirstDirectedPaths directedDFS = new DepthFirstDirectedPaths(digraph, array[i]);
            for (int j = 0; j < array.length; j++) {
                if (directedDFS.hasPathTo(array[j]) && i != j) {
                    stronglyConnected--;
                    break;
                }
            }
        }

        //Printing the minimum number of edges to add
        if (g1.isStronglyConnected() == true) {
            StdOut.println("0");
        } else {
            StdOut.println(stronglyConnected);
        }

        //Print out the pairs
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < pairs2.size(); i++) {
            if (i!=pairs2.size()-1) {
                stringBuilder.append(pairs2.get(i).getV() + " " + pairs2.get(i).getW() + "\n");
            }
            else stringBuilder.append(pairs2.get(i).getV() + " " + pairs2.get(i).getW());
        }
        StdOut.println(stringBuilder);
    }
}