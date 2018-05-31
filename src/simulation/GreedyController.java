package simulation;

import backend.Map;
import backend.Model;
import backend.Road;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 123 on 31.05.2018.
 */
public class GreedyController {

    private final Node intersection;
    MainController parentController;
    Map map;
    Model model;
    double greenTime;

    int horizontalWeight;
    int verticalWeight;

    ArrayList<Road> roadArrayList;


    public GreedyController(Map map, Model model, Node intersection, double greenTime, MainController parentController) {

        this.parentController = parentController;

        this.map = map;
        this.model = model;
        this.intersection = intersection;
        this.greenTime = greenTime;

        this.horizontalWeight = 0;
        this.verticalWeight = 0;

        this.roadArrayList = model.map.getIncomingRoads(this.intersection);
        greedyTimer();
    }

    private void greedyTimer() {

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {

                GreedyCheckIntersection();

            }
        }, 0, 1000);

    }


    private void GreedyCheckIntersection() {


        horizontalWeight = 0;
        verticalWeight = 0;

        System.out.println(roadArrayList);
        for (int i = 0; i < roadArrayList.size(); i++) {

//            System.out.println("roadArrayList. start : " + roadArrayList.get(i).start  +" end " +  roadArrayList.get(i).end);
            if (this.parentController.getAnimationParts().getRoadWeights(30).get(i) > 0) {

                System.out.println("there is a car that passed 30 percent of road. Start : " + this.parentController.getAnimationParts().getRoads().get(i).start.index + " End : " + this.parentController.getAnimationParts().getRoads().get(i).end.index);

                if (this.parentController.getAnimationParts().getRoads().get(i).getDirection() == 6) {


                    horizontalWeight = this.parentController.getAnimationParts().getRoadWeights(30).get(i);

                    for (int j = 0; j < roadArrayList.size(); j++) {
                        if (i != j && this.parentController.getAnimationParts().getRoads().get(j).getDirection() == 4 &&
                                this.parentController.getAnimationParts().getRoads().get(j).end == this.parentController.getAnimationParts().getRoads().get(i).end) {


                            horizontalWeight = this.parentController.getAnimationParts().getRoadWeights(30).get(j) + this.parentController.getAnimationParts().getRoadWeights(30).get(i);


                            System.out.println("Horizontal " + horizontalWeight);
                        }

                    }

                } else if (this.parentController.getAnimationParts().getRoads().get(i).getDirection() != 6 &&
                        this.parentController.getAnimationParts().getRoads().get(i).getDirection() != 4) {


                    System.out.println("vertical entr");

                    verticalWeight = this.parentController.getAnimationParts().getRoadWeights(30).get(i);

                    for (int j = 0; j < roadArrayList.size(); j++) {
                        if (i != j && this.parentController.getAnimationParts().getRoads().get(i).getDirection() == 2) {

                            //this.parentController.getAnimationParts().getRoadWeights(30).get(j)
                            System.out.println("Vertical  two " + verticalWeight);

                            verticalWeight = this.parentController.getAnimationParts().getRoadWeights(30).get(i);

                            System.out.println("Vertical " + verticalWeight);

                        }

                    }


                }

            }
        }


    }

    public HashMap<Integer, Integer> getRoadWeights(int percentage) {

        // Create a hashmap and add the amount of cars to the corresponding index of the road + add the weight to the road itself to a variable

        //key is edge.gei(i), value is weight on those edges at current moment
        HashMap<Integer, Integer> hmap = new HashMap<>();

        for (int i = 0; i < model.map.roads.size(); i++) {
            hmap.put(i, 0);

            model.map.roads.get(i).carsAtEndOfRoad = 0;
        }


        for (int i = 0; i < model.map.roads.size(); i++) {
            for (int j = 0; j < model.map.roads.size(); j++) {

                if (this.parentController.getAnimationParts().carElements.get(i).getBackendCar().getLocRoad().start == model.map.roads.get(j).start &&
                        this.parentController.getAnimationParts().carElements.get(i).getBackendCar().getLocRoad().end == model.map.roads.get(j).end &&
                        this.parentController.getAnimationParts().carElements.get(i).getBackendCar().getPercentageOnCurrentRoad() >= percentage) {

                    hmap.put(j, hmap.get(j) + 1);

                    // add it to the reoads as well
                    model.map.roads.get(j).carsAtEndOfRoad = hmap.get(j);

                }
            }
        }


        return hmap;

    }


}
