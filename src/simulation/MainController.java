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

//        System.out.println("INTERSECTION NODES " + animationParts.intersectionNodes.get(0).index + "   " +
//                animationParts.intersectionNodes.get(1).index);
//        System.out.println(" intersectionFSMS  " + animationParts.model.map.intersectionFSMS.get(0).intersection.index + "   " +
//                animationParts.model.map.intersectionFSMS.get(1).intersection.index);

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
