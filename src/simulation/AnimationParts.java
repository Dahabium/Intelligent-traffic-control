package simulation;

import backend.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AnimationParts {


    ArrayList<carAnimation> carElements;
    ArrayList<TrafficLight> trafficLights;

    ArrayList<FSMTrafficLight> trafficLightsV2;


    Model model;

    Graph graph;
    Board board;

    CollisionDetection collisionDetection;

    public AnimationParts(Graph graph, Board board){

        this.carElements = new ArrayList<>();
        this.trafficLights = new ArrayList<>();

        //test
        this.trafficLightsV2 = new ArrayList<>();

        this.model = new Model();
        this.graph = graph;
        this.board = board;

        this.collisionDetection = new CollisionDetection();
    }


    //input a car object, comute the path here.
    public void addCarToAnimation(int start, int end, int pathFindingMode){

        if(pathFindingMode == 1){

            ArrayList<Integer> IntPath = getRouteGreedy(start,end);

            Car car = new Car(graph.getNodeByIndex(IntPath.get(0)), graph.getNodeByIndex(IntPath.get(IntPath.size() - 1)), graph);
            car.setPath(IntPath);

            collisionDetection.addCar(car);

            carAnimation carAnim = new carAnimation(this.graph, this.board,this.model, car, collisionDetection);
            carElements.add(carAnim);

        }

        if(pathFindingMode == 2){
            Car car = new Car(graph.getNodeByIndex(start), graph.getNodeByIndex(end), graph);

            ArrayList<Integer> IntPath = getRouteAStar(car);
            car.setPath(IntPath);

            collisionDetection.addCar(car);

            carAnimation carAnim = new carAnimation(this.graph, this.board,this.model, car, collisionDetection);
            carElements.add(carAnim);

        }

    }

    public void simulate(){

        for (int i = 0; i < this.carElements.size(); i++) {
            this.carElements.get(i).animationTimer.start();
        }


    }

    public void stopSimulate(){
        for (int i = 0; i < this.carElements.size(); i++) {
            this.carElements.get(i).animationTimer.stop();
        }
    }

    public void printRoadWeights(){


//        traff.simulateFSM();

        //key is edge.gei(i), value is weight on those edges
        HashMap<Integer, Integer> hmap = new HashMap<>();

        for (int i = 0; i < graph.edges.size(); i++) {
            hmap.put(i,0);
        }


        for (int i = 0; i < this.carElements.size(); i++) {
            for (int j = 0; j < graph.edges.size(); j++) {
                if (this.carElements.get(i).getBackendCar().getLocEdge().start == graph.edges.get(j).start &&
                        this.carElements.get(i).getBackendCar().getLocEdge().end == graph.edges.get(j).end) {


                    hmap.put(j, hmap.get(j) + 1);
                }
            }
        }

        System.out.println("HASHMAP " + hmap.entrySet());

    }


    public ArrayList<Integer> getRouteGreedy(int start, int end){
        Greedy greedy = new Greedy(graph.getNodeByIndex(start),graph.getNodeByIndex(end),graph);

        return greedy.getIntPath();
    }

    public ArrayList<Integer> getRouteAStar(Car car){
        Pathfinding path = new Pathfinding(graph);
        return path.Astar(car, graph);
    }

    public void addTrafficLight(int XPos, int YPos){

//        TrafficLight test = new TrafficLight(XPos, YPos);
//        this.trafficLights.add(test);

        FSMTrafficLight test = new FSMTrafficLight(10000,3000,5000,1,XPos,YPos);
        this.trafficLightsV2.add(test);
    }

    public ArrayList<TrafficLight> getTrafficLights(){
        return this.trafficLights;
    }

    public ArrayList<FSMTrafficLight> getTrafficLightsV2(){
        return this.trafficLightsV2;
    }




    //return a list of cars that are driving at a given road
    public ArrayList<Car> carsOnRoad(Road road){
        return  null;
    }


}
