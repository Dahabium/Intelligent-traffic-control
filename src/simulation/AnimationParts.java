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


    //controls the speed changes of each car and traffic light controll.
    Model model;
    Graph graph;
    Board board;

    CollisionDetection collisionDetection;

    public AnimationParts(Graph graph, Board board){

        this.carElements = new ArrayList<>();
        this.trafficLights = new ArrayList<>();

        this.graph = graph;
        this.board = board;

        this.model = new Model(graph);

        this.collisionDetection = new CollisionDetection();
    }


    //input a car object, comute the path here.
    public void addCarToAnimation(int start, int end, int pathFindingMode){

        if(pathFindingMode == 1){
            ArrayList<Integer> IntPath = getRouteGreedy(start,end);

            Car car = new Car(graph.getNodeByIndex(IntPath.get(0)), graph.getNodeByIndex(IntPath.get(IntPath.size() - 1)), this.model.map);
            car.setPath(IntPath);

            collisionDetection.addCar(car);

            carAnimation carAnim = new carAnimation(this.graph, this.board,this.model, car, collisionDetection);
            carElements.add(carAnim);
        }

        if(pathFindingMode == 2){
            Car car = new Car(graph.getNodeByIndex(start), graph.getNodeByIndex(end), this.model.map);

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

    public HashMap<Integer, Integer> getRoadWeights(){


        //key is edge.gei(i), value is weight on those edges at current moment
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

        return hmap;

    }


    public ArrayList<Integer> getRouteGreedy(int start, int end){
        Greedy greedy = new Greedy(graph.getNodeByIndex(start),graph.getNodeByIndex(end),graph);

        return greedy.getIntPath();
    }

    public ArrayList<Integer> getRouteAStar(Car car){
        Pathfinding path = new Pathfinding(graph);
        return path.Astar(car, graph);
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

    //return the arraylist of roads from map
    public ArrayList<Road> getRoads(){
        return this.model.map.roads;
    }

}
