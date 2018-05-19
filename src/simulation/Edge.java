package simulation;

import backend.FSMTrafficLight;
import backend.Intersection;
import backend.Road;

public class Edge {

    public Node start, end;
    public int type;


    public Road road;

    //default constructor ¯\_(ツ)_/¯ dont know much why needed
    public Edge(){
        super();
    }

    public Edge(Node start, Node end){

        this.start = start;
        this.end = end;


        int deltaX = (int)start.Xpos - (int) end.Xpos;
        int deltaY = (int)start.Ypos - (int) end.Ypos;
        int distance = (int) Math.sqrt(deltaX*deltaX + deltaY*deltaY);


        createRoad(distance, start.intersection, end.intersection,1, 0, 50, false);


    }

    public Road getRoad(){
        return this.road;
    }


//    public Edge(Node start, Node end, int incomingLanes, int outcominglanes, double weight){
//        this.start = start;
//        this.end = end;
//        this.weight = weight;
//        this.incomingLanes = incomingLanes;
//        this.outcominglanes = outcominglanes;
//        createRoad(1000, start.intersection, end.intersection, 1, 0, 50, false);
//    }

    public void createRoad(int distance, Intersection beginning, Intersection end, int lanes, int level, int speedLimit, boolean blocked)
    {
        this.road = new Road(distance, beginning, end, lanes, level, speedLimit, blocked );

    }


}
