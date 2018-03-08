package simulation;

import java.util.List;

public class Graph {

    public List<Node> nodes;

    public Graph(List<Node> nodes){
        this.nodes = nodes;
    }

    public void addEdge(Node start, Node end,int incominglanes, int outcominglanes, double weight){
        start.connections.add(new Edge(start,end, incominglanes, outcominglanes, weight));
    }

}
