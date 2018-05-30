package backend;

import simulation.Node;

public class TrafficLightController {

    private double g;
    private int rangeFactor;
    private Map map;
    private Model model;
    private int bestQ;
    private int[] bestQueueDiff;
    private Node intersection;
    private int cycleCounter;

    public TrafficLightController(Map map, Model model, Node node, int rangeFactor, int greenTime){
        this.map = map;
        this.model = model;
        this.rangeFactor = rangeFactor;
        this.intersection = node;
        cycleCounter = 0;
        g = greenTime;
    }

    public void updateCycle(){


        double gDiff = ((rangeFactor*2) * Math.random()) - rangeFactor;
        // this is going to be fixed interval within range
        //double gDiff =
        // update green time
        g = g + gDiff;

        //calculate the queue before the cycle
        int[] qBefore = caLculateQueue(intersection);
        //run cycle here
        //calculate the queue after the cycle
        int[] qAfter = caLculateQueue(intersection);

        int[] qDiff = new int[];
        //store cycle reference
        qDiff[0] = cycleCounter;
        cycleCounter++;

        //calculate the difference in the queues
        for (int i = 1; i < qAfter.length ; i++) {
            qDiff[i] = qAfter[i] - qBefore[i];
        }

        //check if new cycle is more optimal than current
        optimumChooser(qDiff);

    }

    // in this method we should calculate the queues on every road at the intersection
    public int[] caLculateQueue(Node intersection){

        int j = 0;
        int[] q = new int[];

        //calculate the queues on each road at an intersection
        for (int i = 0; i < map.roads.size(); i++) {
            if(map.roads.get(i).end == intersection){
               // q[j]= map.roads.get(i).queue;
                j++;
            }
        }
    }

    public void optimumChooser(int[] contestant){

        int chooser = -1000;

        for (int i = 1; i < contestant.length; i++) {
            chooser = chooser + (contestant[i] - bestQueueDiff[i]);
        }

        if(chooser > 0){
            bestQueueDiff = contestant;
            bestQ = contestant[0];

        }
    }

    public int getRangeFactor(){
        return rangeFactor;
    }

    public void setRangeFactor(int i){
        rangeFactor = i;
    }
}
