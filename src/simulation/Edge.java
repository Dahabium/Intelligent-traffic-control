package simulation;

import backend.Intersection;
import backend.Road;

public class Edge {

    public Node start, end;

    public int incomingLanes,outcominglanes, type = 0;
    public double weight;
    public Road road;


    public Edge(Node start, Node end, int incomingLanes, int outcominglanes, double weight){
        this.start = start;
        this.end = end;
        this.weight = weight;
        this.incomingLanes = incomingLanes;
        this.outcominglanes = outcominglanes;
        createRoad(1000, start.intersection, end.intersection, 1, 0, 50, false);
    }

    public void createRoad(int distance, Intersection beginning, Intersection end, int lanes, int level, int speedLimit, boolean blocked)
    {
        this.road = new Road(distance, beginning, end, lanes, level, speedLimit, false );
    }

    public Edge(Node start, Node end){
        this.start = start;
        this.end = end;
    }

    public Road getRoad(){
        return this.road;
    }

}
