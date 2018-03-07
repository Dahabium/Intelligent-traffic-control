package simulation;

import java.util.List;

public class Graphv2 {

    public List<Node> nodes;

    public Graphv2( List<Node> nodes){
        this.nodes = nodes;
    }

    public void addEdge(Node start, Node end, double weight){
        start.connections.add(new Edge(start,end,weight));
    }

}
