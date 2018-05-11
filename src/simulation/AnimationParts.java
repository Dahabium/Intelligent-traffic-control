package simulation;

import backend.*;
import javafx.animation.AnimationTimer;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class AnimationParts {


    ArrayList<carAnimation> carElements;
    ArrayList<Car> carBackend;
    Model model;

    Graph graph;
    Board board;

    CollisionDetection collisionDetection;

    public AnimationParts(Graph graph, Board board){

        this.carElements = new ArrayList<>();
        this.model = new Model();
        this.graph = graph;
        this.board = board;

        this.collisionDetection = new CollisionDetection();
    }


    //in future, change inputs to start node and end node (just to tidy some code)...



    //input a car object, comute the path here.
    public void addCarToAnimation(int start, int end){

        ArrayList<Integer> IntPath = getRouteSequence(start,end);

        Car car = new Car(graph.getNodeByIndex(IntPath.get(0)), graph.getNodeByIndex(IntPath.get(1)),  graph);
        car.setPath(IntPath);

        collisionDetection.addCar(car);

        carAnimation carAnim = new carAnimation(this.graph, this.board,this.model, car, collisionDetection);
        carElements.add(carAnim);


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


    public ArrayList<Integer> getRouteSequence(int start, int end){
        Greedy greedy = new Greedy(graph.getNodeByIndex(start),graph.getNodeByIndex(end),graph);
        System.out.println("Greedy path" + greedy.getIntPath());
        
        Pathfinding path = new Pathfinding(graph);
        Car car = new Car(graph.getNodeByIndex(start),graph.getNodeByIndex(end),graph);
        path.Astar(car, graph);
        return greedy.getIntPath();
    }





}
