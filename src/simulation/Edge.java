package simulation;

public class Edge {

    public Node start;
    public Node end;
    public int incomingLanes,outcominglanes;
    public double weight;


    public Edge(Node start, Node end, int incomingLanes, int outcominglanes, double weight){
        this.start = start;
        this.end = end;
        this.weight = weight;
        this.incomingLanes = incomingLanes;
        this.outcominglanes = outcominglanes;
    }

    public Edge(Node start, Node end){
        this.start = start;
        this.end = end;
    }

}
