package backend;

import javafx.util.Pair;
import simulation.Node;

import java.util.ArrayList;

public class intersectionFSMS {

    Node intersection;

    Road horizontal1, horizontal2;
    Road vertical1, vertical2;

    Map map;

    public intersectionFSMS(Node intersection, Map map){

        this.intersection = intersection;
        this.map = map;

    }

    public void connect2HorizontalRoads(Road a, Road b){

        if((a.getDirection() == 6 && b.getDirection() == 4) || (a.getDirection() == 4 && b.getDirection() == 6)){
            this.horizontal1 = a;
            this.horizontal2 = b;
        }

        a.setRoadWithSameFSM(b);
        b.setRoadWithSameFSM(a);

    }

    public void connect2VerticalRoads(Road a, Road b){

        if((a.getDirection() == 8 && b.getDirection() == 2) || (a.getDirection() == 2 && b.getDirection() == 8)){
            this.vertical1 = a;
            this.vertical2 = b;
        }

        a.setRoadWithSameFSM(b);
        b.setRoadWithSameFSM(a);
    }

    public ArrayList<Road> getAllFSMRoads(){
        ArrayList<Road> out = new ArrayList<>();

        if(horizontal1 != null && horizontal2 != null && vertical1 != null && vertical2 != null){
            out.add(horizontal1);
            out.add(horizontal2);
            out.add(vertical1);
            out.add(vertical2);
        }

        return out;
    }



}
