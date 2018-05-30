package backend;

import javafx.util.Pair;
import simulation.Node;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class intersectionFSMS {

    Node intersection;

    Road horizontal1, horizontal2;
    Road vertical1, vertical2;

    Map map;

    ArrayList<Road> unstructured;


    public intersectionFSMS(ArrayList<Road> input) {

        this.unstructured = input;

        //regular cross-intersection
        if (unstructured.size() == 4) {

            for (int i = 0; i < 4; i++) {

                System.out.println("DIRECTION " + input.get(i).getDirection());

                if (unstructured.get(i).getDirection() == 6) {
                    this.horizontal1 = unstructured.get(i);
                }
                if (unstructured.get(i).getDirection() == 4) {
                    this.horizontal2 = unstructured.get(i);
                }
                if (unstructured.get(i).getDirection() == 2) {
                    this.vertical1 = unstructured.get(i);
                }
                if (unstructured.get(i).getDirection() == 8) {
                    this.vertical2 = unstructured.get(i);
                }
            }
        }
        //T-Section
        if (unstructured.size() == 3) {

            for (int i = 0; i < 3; i++) {
                if (unstructured.get(i).getDirection() == 6) {
                    this.horizontal1 = unstructured.get(i);
                }
                if (unstructured.get(i).getDirection() == 4) {
                    this.horizontal2 = unstructured.get(i);
                }
                if (unstructured.get(i).getDirection() == 2) {
                    this.vertical1 = unstructured.get(i);
                }
                if (unstructured.get(i).getDirection() == 8) {
                    this.vertical2 = unstructured.get(i);
                }
            }
        }

        setHorizontalRed(12000, true);
    }

    public intersectionFSMS(Node intersection, Map map) {

        this.intersection = intersection;
        this.map = map;
        unstructured = new ArrayList<>();

        connect();

    }

    public void delayExecution(){
        runIntersectionFSMS();

//        Timer t = new Timer();
//        t.schedule(new TimerTask() {
//            @Override
//            public void run() {
//
//            }
//        }, 0, 24000);
    }

    public void runIntersectionFSMS() {


        this.horizontal1.getTrafficLight().runRed();
        this.horizontal2.getTrafficLight().runRed();

        this.vertical1.getTrafficLight().runGreen();
        this.vertical2.getTrafficLight().runGreen();

    }

    public void setHorizontalRed(int Green, boolean newCurrentState) {

        this.horizontal1.getTrafficLight().setRedTime(Green + 2000);
        this.horizontal2.getTrafficLight().setRedTime(Green + 2000);

        this.vertical1.getTrafficLight().setRedTime(Green + 2000);
        this.vertical2.getTrafficLight().setRedTime(Green + 2000);

        this.horizontal1.getTrafficLight().setGreeenTime(Green);
        this.horizontal2.getTrafficLight().setGreeenTime(Green);

        this.vertical1.getTrafficLight().setGreeenTime(Green);
        this.vertical2.getTrafficLight().setGreeenTime(Green);


        if (newCurrentState) {

            //1 - red, 2 - yellow, 3- green
            this.horizontal1.getTrafficLight().currentstate = 1;
            this.horizontal2.getTrafficLight().currentstate = 1;

            this.vertical1.getTrafficLight().currentstate = 3;
            this.vertical2.getTrafficLight().currentstate = 3;
        }

    }

    public void setVerticalSequence(int Red, int Green) {

        this.vertical1.getTrafficLight().setRedTime(Red);
        this.vertical2.getTrafficLight().setRedTime(Red);
        this.vertical1.getTrafficLight().setGreeenTime(Green);
        this.vertical2.getTrafficLight().setGreeenTime(Green);

    }

    public void connect() {


        unstructured = map.getIncomingRoads(intersection);


        for (int i = 0; i < unstructured.size(); i++) {

            if (unstructured.get(i).getDirection() == 6) {
                this.horizontal1 = unstructured.get(i);
            }
            if (unstructured.get(i).getDirection() == 4) {
                this.horizontal2 = unstructured.get(i);
            }
            if (unstructured.get(i).getDirection() == 2) {
                this.vertical1 = unstructured.get(i);
            }
            if (unstructured.get(i).getDirection() == 8) {
                this.vertical2 = unstructured.get(i);
            }
        }


    }

    public void connect2HorizontalRoads(Road a, Road b) {

        if ((a.getDirection() == 6 && b.getDirection() == 4) || (a.getDirection() == 4 && b.getDirection() == 6)) {
            this.horizontal1 = a;
            this.horizontal2 = b;
        }

        a.setRoadWithSameFSM(b);
        b.setRoadWithSameFSM(a);

    }

    public void connect2VerticalRoads(Road a, Road b) {

        if ((a.getDirection() == 8 && b.getDirection() == 2) || (a.getDirection() == 2 && b.getDirection() == 8)) {
            this.vertical1 = a;
            this.vertical2 = b;
        }

        a.setRoadWithSameFSM(b);
        b.setRoadWithSameFSM(a);
    }

    public ArrayList<Road> getAllFSMRoads() {

        ArrayList<Road> out = new ArrayList<>();

        if (horizontal1 != null && horizontal2 != null && vertical1 != null && vertical2 != null) {
            out.add(horizontal1);
            out.add(horizontal2);
            out.add(vertical1);
            out.add(vertical2);
        }

        return out;
    }


}
