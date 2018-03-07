package simulation;

public class Edge {

    public Node start;
    public Node end;
    public double weight;

    public Edge(Node start, Node end, double weight){
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

}
