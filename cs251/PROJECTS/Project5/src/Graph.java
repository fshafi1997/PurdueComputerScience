import java.util.*;
import java.util.LinkedList;


class Graph {
    private int V;
    private LinkedList<Integer> adj[];

    Graph(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList();
    }

    void addEdge(int v, int w) {
        adj[v].add(w);
    }

    void DFSUtil(int v, Boolean visited[]) {
        visited[v] = true;
        int n;
        Iterator<Integer> i = adj[v].iterator();
        while (i.hasNext()) {
            n = i.next();
            if (!visited[n])
                DFSUtil(n, visited);
        }
    }

    Graph getTransposeOfGraph() {
        Graph g = new Graph(V);
        for (int v = 0; v < V; v++) {
            Iterator<Integer> i = adj[v].listIterator();
            while (i.hasNext())
                g.adj[i.next()].add(v);
        }
        return g;
    }

    Boolean isStronglyConnected() {
        Boolean visited[] = new Boolean[V];
        for (int i = 0; i < V; i++)
            visited[i] = false;

        DFSUtil(0, visited);

        for (int i = 0; i < V; i++)
            if (visited[i] == false)
                return false;

        Graph gr = getTransposeOfGraph();

        for (int i = 0; i < V; i++)
            visited[i] = false;

        gr.DFSUtil(0, visited);

        for (int i = 0; i < V; i++)
            if (visited[i] == false)
                return false;

        return true;
    }
}