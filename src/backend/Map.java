package backend;

import simulation.Edge;
import simulation.Graph;
import simulation.Node;

import java.util.ArrayList;


public class Map {

    private Graph graph;
    public ArrayList<Road> roads;

    public ArrayList<intersectionFSMS> intersectionFSMS;


    public Map(Graph graph){

        this.graph = graph;

        this.roads = new ArrayList<>();
        this.intersectionFSMS = new ArrayList<>();

        createRoads();

    }

    public void createRoads(){

        for (int i = 0; i < this.graph.edges.size(); i++) {

            //if this edge is not yet added to road and there is no analogical edges in road already, then add the first edge to road.
            if(this.graph.edges.get(i).addedToRoad == false && getRoadByEdge(this.graph.edges.get(i)) == null){
                ArrayList<Edge> tempEdge = new ArrayList<>();
                tempEdge.add(this.graph.edges.get(i));
                Road temp = new Road(tempEdge);
                this.graph.edges.get(i).addedToRoad = true;

                roads.add(temp);
            }

            //block of code to be executed if the condition1 is false and condition2 is true
            else if (this.graph.edges.get(i).addedToRoad == false && getRoadByEdge(this.graph.edges.get(i)) != null){
                getRoadByEdge(this.graph.edges.get(i)).addLane(this.graph.edges.get(i));
                this.graph.edges.get(i).addedToRoad = true;
            }

        }

    }

    public Road getRoadByEdge(Edge edge){
        if(this.roads.size() > 0){
            for (int i = 0; i < this.roads.size(); i++) {
                if(this.roads.get(i).start == edge.start && this.roads.get(i).end == edge.end){
                    return this.roads.get(i);
                }
            }
        }

        return null;
    }

    public ArrayList<Road> getIncomingRoads(Node node){

        ArrayList<Road> out = new ArrayList<>();

        for (int i = 0; i < this.roads.size(); i++) {

            if(roads.get(i).end == node){
                out.add(roads.get(i));
            }

        }

        return out;
    }

    public ArrayList<Road> getOutgoingRoads(Node node){

        ArrayList<Road> out = new ArrayList<>();

        for (int i = 0; i < this.roads.size(); i++) {

            if(roads.get(i).start == node){
                out.add(roads.get(i));
            }

        }
        return out;
    }


    public intersectionFSMS getCorrespondingFSM(Node node){
        for (int i = 0; i < intersectionFSMS.size(); i++) {

            if(intersectionFSMS.get(i).intersection == node){
                return intersectionFSMS.get(i);
            }
        }

        return null;

    }

    public void runAllConnectedFSMS(){
        System.out.println("IntersectionFSMS array size " + intersectionFSMS.size());
        for (int i = 0; i < this.intersectionFSMS.size() ; i++) {
            this.intersectionFSMS.get(i).runFSM_Horizontal_Red();
        }
    }

    public Road exsistParallelOutgoingRoad(int direction, Node node){

        for (int i = 0; i < roads.size(); i++) {

            if(roads.get(i).start == node && roads.get(i).getDirection() == direction){
                return roads.get(i);
            }
        }
        return null;
    }

    public boolean RoadsFacingEachOther(Road a, Road b){
        if(a.getDirection() == 6 && b.getDirection() == 4){
            return true;
        }
        else if(a.getDirection() == 4 && b.getDirection() == 6){
            return true;
        }
        else if(a.getDirection() == 8 && b.getDirection() == 2){
            return true;
        }
        else if(a.getDirection() == 2 && b.getDirection() == 8){
            return true;
        }

        else return false;
    }


}
