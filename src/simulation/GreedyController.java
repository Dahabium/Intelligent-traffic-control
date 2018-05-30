package simulation;

import backend.Map;
import backend.Model;
import backend.Road;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 123 on 31.05.2018.
 */
public class GreedyController {

    MainController parentController;

    Map map;
    Model model;
    Node intersectionNode;

    private final Node node;
    double greenTime;


    public GreedyController(Map map, Model model, Node node, double greenTime, MainController parentController){

        this.parentController = parentController;

        this.map = map;
        this.model = model;
        this.node = node;
        this.greenTime = greenTime;

        GreedyCheckIntersection();
    }

    private void GreedyCheckIntersection() {

        ArrayList<Road> roadArrayList = model.map.getIncomingRoads(this.intersectionNode);

        for (int i = 0; i < roadArrayList.size(); i++) {

            if(roadArrayList.get(i).carsAtEndOfRoad > 1){

            }
        }

    }

    public HashMap<Integer, Integer> getRoadWeights(int percentage){

        // Create a hashmap and add the amount of cars to the corresponding index of the road + add the weight to the road itself to a variable

        //key is edge.gei(i), value is weight on those edges at current moment
        HashMap<Integer, Integer> hmap = new HashMap<>();

        for (int i = 0; i < model.map.roads.size(); i++) {
            hmap.put(i,0);

            model.map.roads.get(i).carsAtEndOfRoad = 0;
        }


        for (int i = 0; i < model.map.roads.size() ; i++) {
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
