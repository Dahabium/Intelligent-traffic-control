package simulation;

import backend.*;

import java.util.ArrayList;

public class AnimationParts {


    ArrayList<carAnimation> carElements;
    ArrayList<TrafficLight> trafficLights;

    Model model;

    Graph graph;
    Board board;

    CollisionDetection collisionDetection;

    public AnimationParts(Graph graph, Board board){

        this.carElements = new ArrayList<>();
        this.trafficLights = new ArrayList<>();
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


    public ArrayList<Integer> getRouteGreedy(int start, int end){
        Greedy greedy = new Greedy(graph.getNodeByIndex(start),graph.getNodeByIndex(end),graph);

        return greedy.getIntPath();
    }

    public ArrayList<Integer> getRouteAStar(Car car){
        Pathfinding path = new Pathfinding(graph);
        return path.Astar(car, graph);
    }

    public void addTrafficLight(int XPos, int YPos){
        TrafficLight test = new TrafficLight(XPos, YPos);
        this.trafficLights.add(test);

    }

    public ArrayList<TrafficLight> getTrafficLights(){
        return this.trafficLights;
    }




    //return a list of cars that are driving at a given road
    public ArrayList<Car> carsOnRoad(Road road){
        return  null;
    }


}
