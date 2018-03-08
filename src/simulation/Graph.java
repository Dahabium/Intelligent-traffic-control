package simulation;

import java.util.List;

public class Graph {

    public List<Node> nodes;

    public Graph(List<Node> nodes){
        this.nodes = nodes;
    }

    public void addEdge(Node start, Node end, double weight){
        start.connections.add(new Edge(start,end,weight));
    }

}
