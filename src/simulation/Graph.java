package simulation;
import java.util.LinkedList;

public class Graph {

    int V;
    LinkedList<Integer> adjListArray[];

    // constructor
    public Graph(int V)
    {
        this.V = V;

        //size of array is amount of verts
        adjListArray = new LinkedList[V];

        // Create a new list for each vertex
        // such that adjacent nodes can be stored
        for(int i = 0; i < V ; i++){
            adjListArray[i] = new LinkedList<>();
        }
    }

    // Adds an edge to an undirected graph
    public static void addEdge(Graph graph, int src, int dest)
    {
        // Add an edge from src to dest.
        graph.adjListArray[src].add(dest);

        // Since graph is undirected, add an edge from dest
        // to src also
        graph.adjListArray[dest].add(src);
    }
    // A utility function to print the adjacency list
    // representation of graph

    public static void printGraph(Graph graph)
    {
        for(int v = 0; v < graph.V; v++)
        {
            System.out.println("Adjacency list of vertex "+ v);
            for(Integer pCrawl: graph.adjListArray[v]){
                System.out.print(" -> "+pCrawl);
            }
            System.out.println("\n");
        }
    }



}
