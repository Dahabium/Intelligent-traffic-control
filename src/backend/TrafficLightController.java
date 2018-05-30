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
    private int rangeFactor;
    private Map map;
    private Model model;
    private int bestQ;
    private ArrayList<Integer> bestQueueDiff;
    private Node intersection;
    private int cycleCounter;

    public TrafficLightController(Map map, Model model, Node node, int rangeFactor, int greenTime) {
        this.map = map;
        this.model = model;
        this.rangeFactor = rangeFactor;
        this.intersection = node;
        cycleCounter = 0;
        g = greenTime;
        bestQueueDiff = new ArrayList<Integer>();
    }

    public void updateCycle() {

        //random
        double gDiff = ((rangeFactor * 2) * Math.random()) - rangeFactor;
        // fixed
        //double gDiff =

        // update green time
        g = g + gDiff;
        System.out.println("G + G this " + g);

        //calculate the queue before the cycle
        ArrayList<Integer> qBefore = caLculateQueue(intersection);

        //run cycle here

        this.model.map.intersectionFSMS.get(0).setHorizontalRed((int) g, false);
//        this.model.map.intersectionFSMS.get(1).setHorizontalRed((int)g, false);

        int z = -1;

        System.out.println("DELAY START ");
        final Timer timer = new Timer();
        final TimerTask task = new TimerTask() {

            @Override
            public void run() {

                //calculate the queue after the cycle
                ArrayList<Integer> qAfter = caLculateQueue(intersection);

                ArrayList<Integer> qDiff = new ArrayList();
                //store cycle reference
                qDiff.add(cycleCounter);
                cycleCounter++;

                //calculate the difference in the queues
                for (int i = 1; i < qAfter.size(); i++) {
                    qDiff.add(qAfter.get(i) - qBefore.get(i));
                }

                //check if new cycle is more optimal than current
                optimumChooser(qDiff);


                timer.cancel();
                timer.purge();


            }
        };

        timer.schedule(task, (int) g * 2 + 4000);
        System.out.println("AFTER DELAY");


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

        int chooser = 0;

        for (int i = 1; i < contestant.size() - 1; i++) {
            chooser = chooser + (contestant.get(i) - bestQueueDiff.get(i));
        }

        if (chooser > 0) {
            bestQueueDiff = contestant;
            bestQ = contestant.get(0);

        }
    }

    public int getRangeFactor() {
        return rangeFactor;
    }

    public void setRangeFactor(int i) {
        rangeFactor = i;
    }
}
