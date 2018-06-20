package simulation;

import backend.Greedy;
import backend.Map;
import backend.Model;
import backend.TrafficLightController;

/**
 * Created by 123 on 31.05.2018.
 */
public class MainController {

    public int mode;
    private TrafficLightController trafficLightController;
    private GreedyController greedyController;
    private Map map;
    private Model model;
    private double greenTime;
    private AnimationParts animationParts;

    public final int NORTH = 8;
    public final int SOUTH = 2;
    public final int EAST = 6;
    public final int WEST = 4;

    public MainController(AnimationParts animationParts, int greenTime, int mode) {

        this.animationParts = animationParts;
        this.map = animationParts.model.map;
        this.model = animationParts.model;
        this.greenTime = greenTime;

        this.mode = mode;

        if (mode == 1) {

            for (int i = 0; i < animationParts.intersectionNodes.size(); i++) {
                this.greedyController = new GreedyController(this.animationParts.model.map, this.animationParts.model, this.animationParts.intersectionNodes.get(i),
                        this.greenTime, this);
            }
        }

        if (mode == 2) {

            for (int i = 0; i < animationParts.intersectionNodes.size(); i++) {
                this.trafficLightController = new TrafficLightController(this.animationParts.model.map, this.animationParts.model, this.animationParts.intersectionNodes.get(i), 5000, 10000);
            }

        }

//        System.out.println("INTERSECTION NODES " + animationParts.intersectionNodes.get(0).index );
//        System.out.println(" intersectionFSMS  " + animationParts.model.map.intersectionFSMS.get(0).intersection.index );
    }

    //TODO WRITE A METHOD TO RETURN A CAR AT THE END OF THE ROAD AT GIVEN INTERSECTION
    //return 3 values: 1) intersection number 2) intersection side that will work green 3) number of cars that want to turn left?
    public int[] leftCheck() {

        for (int i = 0; i < model.map.intersectionFSMS.size(); i++) {
            for (int j = 0; j < animationParts.carElements.size(); j++) {

                if (model.map.intersectionFSMS.get(i).intersection == animationParts.carElements.get(j).car.getLocRoad().end &&
                        animationParts.carElements.get(j).car.getPercentageOnCurrentRoad() >= 70) {

                    if (isALeftTurn(animationParts.carElements.get(j).car.getCurentDirection(),
                            getDirection2Nodes(animationParts.carElements.get(j).car.getPath().get(animationParts.carElements.get(j).pathIterator),
                                    animationParts.carElements.get(j).car.getPath().get(animationParts.carElements.get(j).pathIterator + 1))))
                    {

                        if(animationParts.carElements.get(j).car.getCurentDirection() == 6){
                            return new int[]{i,WEST,0};
                        }
                        if(animationParts.carElements.get(j).car.getCurentDirection() == 4){
                            return new int[]{i,EAST,0};
                        }
                        if(animationParts.carElements.get(j).car.getCurentDirection() == 2){
                            return new int[]{i,NORTH,0};
                        }
                        if(animationParts.carElements.get(j).car.getCurentDirection() == 8){
                            return new int[]{i,SOUTH,0};
                        }

                    }

                }

            }
        }

        int[] out = new int[]{};

        return out;
    }

    private boolean isALeftTurn(int previousDirection, int newDirection) {

        if (previousDirection == 2 && newDirection == 6) {
            return true;
        }
        else if (previousDirection == 4 && newDirection == 2) {
            return true;
        }
        else if (previousDirection == 6 && newDirection == 8) {
            return true;
        }
        else if (previousDirection == 8 && newDirection == 4) {
            return true;
        }

        return false;
    }

    private int getDirection2Nodes(int one, int two) {
        return model.graph.getEdgeByIndexes(one,two).direction;
    }

    public AnimationParts getAnimationParts() {
        return this.animationParts;
    }

    public TrafficLightController getTLCcontroller() {
        return this.trafficLightController;
    }

    public GreedyController getGreedyController() {
        return this.greedyController;
    }

}
