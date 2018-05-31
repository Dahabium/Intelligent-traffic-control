package backend;

import simulation.Node;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sandersalahmadibapost on 29/05/2018.
 */

public class TrafficLightController {

    private double g;
    private double gTime;
    private int rangeFactor;
    private Map map;
    private Model model;
    private int bestQ;
    private ArrayList<Integer> bestQueueDiff;
    public Node intersection;
    private int cycleCounter;
    private int rangeCounter;

    public TrafficLightController(Map map, Model model, Node node, int rangeFactor, double greenTime) {
        this.map = map;
        this.model = model;
        this.rangeFactor = rangeFactor;
        this.intersection = node;
        cycleCounter = 0;
        g = greenTime;
        gTime = 10000;
        bestQueueDiff = new ArrayList<>();
        rangeCounter = -5;

//        bestQueueDiff.add(0);
//        bestQueueDiff.add(0);
//        bestQueueDiff.add(0);
//        bestQueueDiff.add(0);
    }

    public void updateCycle() {
        System.out.println("Updatecycle Method exec");
        gTime = g;
        //random
        double gDiff =(int) ((rangeFactor * 2) *( Math.random())) - rangeFactor;
        // fixed
        System.out.println("G before " + g);
       // double gDiff = rangeCounter*1000;
        System.out.println("G Diff " + gDiff);
        rangeCounter++;

        // update green time
        gTime = gTime + gDiff;
        System.out.println("G + G this " + gTime);

        //calculate the queue before the cycle
        ArrayList<Integer> qBefore = caLculateQueue(intersection);

        //run cycle here

        this.model.map.intersectionFSMS.get(0).setHorizontalRed((int) gTime, false);
        this.model.map.intersectionFSMS.get(0).runIntersectionFSMS();

//        this.model.map.intersectionFSMS.get(1).setHorizontalRed((int)g, false);

        System.out.println("DELAY START ");

        final Timer timer = new Timer();
        final TimerTask task = new TimerTask() {

            @Override
            public void run() {
                System.out.println("IN The Delay ");

                //calculate the queue after the cycle
                ArrayList<Integer> qAfter = caLculateQueue(intersection);

                ArrayList<Integer> qDiff = new ArrayList();
                //store cycle reference
                qDiff.add(cycleCounter);
                cycleCounter++;
                if (qAfter.size()!= 0||qBefore.size()!=0){
                    //calculate the difference in the queues
                    for (int i = 1; i < qAfter.size(); i++) {
                        qDiff.add(qAfter.get(i) - qBefore.get(i));
                    }
                }

                if (qDiff.size() != 1) {
                    //check if new cycle is more optimal than current
                    optimumChooser(qDiff);
                }


            }
        };

        timer.schedule(task, (int) gTime * 2 + 4000);

        System.out.println("AFTER DELAY");



//        delay - delay in milliseconds before task is to be executed.
//        period - time in milliseconds between successive task executions.

    }

    // in this method we should calculate the queues on every road at the intersection
    public ArrayList<Integer> caLculateQueue(Node intersection) {

        //int j = 0;
        ArrayList<Integer> q = new ArrayList();

        //calculate the queues on each road at an intersection
        for (int i = 0; i < map.roads.size(); i++) {
            if (map.roads.get(i).end == intersection) {
                System.out.println("QUEUE SIZE " + map.roads.get(i).carsAtEndOfRoad);
                q.add(map.roads.get(i).carsAtEndOfRoad);
                // j++;
            }
        }
        return q;
    }

    public void optimumChooser(ArrayList<Integer> contestant) {

        double chooser = 0;
        System.out.println("Contestant " );
        if(contestant.size()!= 1 ||bestQueueDiff.size()!= 1){
            for (int i = 0; i < contestant.size()-1; i++) {
                chooser = chooser + (contestant.get(i+1) - bestQueueDiff.get(i+1));
            }

            if (chooser > 0) {
                bestQueueDiff = contestant;
                bestQ = contestant.get(0);

            }
        }

    }

    public int getRangeFactor() {
        return rangeFactor;
    }

    public void setRangeFactor(int i) {
        rangeFactor = i;
    }

    public double getGTime() {
        return gTime;
    }
}
