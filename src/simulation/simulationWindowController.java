package simulation;

import backend.Road;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.AnimationTimer;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class simulationWindowController {

    Board simulationBoard;
    @FXML
    private ScrollPane simulationScrollpane;
    @FXML
    private Group simulationElements;
    @FXML
    private Button createCarbtn, runSimbtn, stopSimbtn, debugbtn, launchCars;

    @FXML
    private TextField StartInput, EndInput, numberCarsForSim;
    @FXML
    private CheckBox GreedySelector, AStarselector;
    private AnimationTimer animationTimer;
    private Graph graph;
    private String filename;

    private Scene infoPanelScene;
    private Pane infoPanePane;
    ObservableList<javafx.scene.Node> children = this.simulationElements.getChildren();
    private MainController mainController;

    private int PathfindingMode;

    private AnimationParts animationParts;


    private int carStart;
    private int carEnd;

    //TODO PASSING THE FILENAME

    public void initialize() {
        PathfindingMode = 1;

        XMLLoader graphLoader = new XMLLoader("singlelane");
        graph = graphLoader.getGraph();

        javafx.scene.image.Image img = new Image("background.JPG", 800, 800, false, false);
        ImageView imgView = new ImageView(img);
        simulationElements.getChildren().add(imgView);

        simulationBoard = new Board(15, 15);

        simulationBoard.setBoard(graph);

        simulationElements.getChildren().add(simulationBoard);


        //===============================ANIMATION==========================

        //create the parent class for animation of all cars and traffic lights
        animationParts = new AnimationParts(this.graph, this.simulationBoard);

        //create and connect traffic lights (add to gui + backend)
        createTrafficLights();

        //refresh the value of cars on each road after specified time
        roadStatusUpdater(1000);

        //mode 1 - Greedy, mode 2 - TLC
        this.mainController = new MainController(this.animationParts, 10000, 2);

        this.animationParts.model.map.runAllConnectedFSMS();

        if(this.mainController.mode == 2 ){
            updateCycleSander();

        }

        simulationScrollpane.setContent(simulationElements);


//        this.infoPanelScene = new Scene();


//        Scene scene =  new Scene(root, 800,800);
//
//        stage.setScene(scene);
//        stage.show();


        handleMouseClickOnCars();

    }

    private void handleMouseClickOnCars() {

        this.simulationElements.setOnMouseClicked(event -> {

            System.out.println("Clicked on position " + event.getSceneX() + "   " +  event.getSceneY());
            for (int i = 0; i < animationParts.carElements.size(); i++) {

                if (event.getTarget() == animationParts.carElements.get(i).getAnimatedCar()) {
                    String infoPanel;
                    infoPanel = "Car number: " + i + " Direction: " + animationParts.carElements.get(i).car.getCurentDirection()
                    +"   Velocity: " + animationParts.carElements.get(i).car.getVel() + "   Local road: " + animationParts.carElements.get(i).car.getLocRoad();
                    JOptionPane.showMessageDialog(null, infoPanel );
                }

            }

        });
    }


    @FXML
    public void debugbtnaction() {

//        System.out.println("Currently running on lane " + this.animationParts.carElements.get(0).car.getLocEdge());

        this.animationParts.carElements.get(0).changeLane(1,this.animationParts.carElements.get(0).getBackendCar().getCurentDirection());
    }

    public void updateCycleSander(){


        Timeline timeline = new Timeline(new KeyFrame(Duration.millis((int)(mainController.getTLCcontroller().getGTime()*2)+4000), ev -> {

            System.out.println("Cycle Runned in simWinControl");
            mainController.getTLCcontroller().updateCycle();

        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }


    //This class creates the traffic lights and adds them to gui,
    // then we connect the traffic lights FSM cycles via connectFSM() method called in animationParts class

    public void createTrafficLights() {


        //cases: T- Section, Cross-Intersection
        for (Road road : animationParts.getRoads()) {

            int endX = road.end.x;
            int endY = road.end.y;
            int direction = road.getDirection();
            Group trafficLightGui = road.getTrafficLight().getTrafficLightGui();
            //check if more than one road is directed towards an intersection => create traffic lights at the end
            //Cross-intersection...
            if (animationParts.model.map.getIncomingRoads(road.end).size() == 4) {

                if(!animationParts.intersectionNodes.contains(road.end)){
                    animationParts.intersectionNodes.add(road.end);
                }

                if (direction == 6) {

                    road.addTrafficLight(endX - 70,  endY + 70,
                            5000, 3000, 1500, 1);
                    children.add( trafficLightGui);
                }

                else if (direction == 4) {
                    road.addTrafficLight(endX + 70, endY - 70,
                            5000, 3000, 1500, 1);
                    children.add( trafficLightGui);
                }
                else if  (direction == 8) {

                    road.addTrafficLight(endX + 70, endY + 70,
                            5000, 3000, 1500, 1);
                    children.add( trafficLightGui);
                }
                else if (direction == 2) {
                    road.addTrafficLight(endX - 70, endY - 70,
                            5000, 3000, 1500, 1);
                    children.add( trafficLightGui);

                }
            }

            //T-Section
            if(animationParts.model.map.getIncomingRoads(road.end).size() == 3){

                if (direction == 6) {
                    road.addTrafficLight(endX, endY,
                            5000, 3000, 1500, 1);
                    children.add( trafficLightGui);
                }
                else if (direction == 4){
                    road.addTrafficLight(endX, endY,
                            5000, 3000, 1500, 1);
                    children.add( trafficLightGui);
                }
                else if  (direction == 8) {

                    road.addTrafficLight(endX + 70, endY + 130,
                            5000, 3000, 1500, 1);
                    children.add( trafficLightGui);
                }
                else if (direction == 2) {
                    road.addTrafficLight(endX, endY - 130,
                            5000, 3000, 1500, 1);
                    children.add( trafficLightGui);

                }

            }
        }

        this.animationParts.model.connectFSM();


    }


    public void createTrafficLightsManual() {

        animationParts.model.map.roads.get(0).addTrafficLight(graph.edges.get(0).end.x * 100 - 50, graph.edges.get(0).end.y * 100 + 50, 15000, 6000, 1000, 1);
        children.add(animationParts.model.map.roads.get(0).getTrafficLight().getTrafficLightGui());

    }

    @FXML
    public void createCar() {

        System.out.println("Car created");
        this.carStart = Integer.parseInt(StartInput.getText());
        this.carEnd = Integer.parseInt(EndInput.getText());
        this.animationParts.addCarToAnimation(carStart, carEnd, PathfindingMode);

        int lastCar = this.animationParts.carElements.size() - 1;

        children.add(this.animationParts.carElements.get(lastCar).getAnimatedCar());

    }


    @FXML
    public void launchCarsSim(){

        ArrayList<Node> startEndPoints = new ArrayList<>();

        for (int i = 0; i < animationParts.model.map.roads.size(); i++) {

            if(animationParts.model.map.getIncomingRoads(animationParts.getRoads().get(i).start).size() <= 1 &&
                    animationParts.model.map.getOutgoingRoads(animationParts.getRoads().get(i).start).size() <= 1 ){

                startEndPoints.add(animationParts.getRoads().get(i).start);

            }
        }

        //determine the range for the random selector
        int min = 0;
        int max = startEndPoints.size()-1;

        int numberOfCarsToSimulate = Integer.valueOf(numberCarsForSim.getText());

        ArrayList<Integer> startPositionsIndexes = new ArrayList<>();
        ArrayList<Integer> endPositionsIndexes = new ArrayList<>();
        ArrayList<Integer> interArrivals = new ArrayList<>();
        double lambda = 11 ;

        //will return a value in index of the array of start/ending points
        Random random = new Random();

        for (int i = 0; i < numberOfCarsToSimulate; i++) {

            int randStart = random.nextInt(max - min + 1) + min;
            int randEnd = random.nextInt(max - min + 1) + min;

            if(randStart != randEnd){

                startPositionsIndexes.add(startEndPoints.get(randStart).index);
                endPositionsIndexes.add(startEndPoints.get(randEnd).index);

            }
            else if(i != 0){
                i--;
            }
            int rand = getPoissonRandom(lambda);
            int temp = 60000 / (rand);

            if(temp < 5000){
                temp = 5000;
            }

            interArrivals.add(temp);
            System.out.println("TIME IS:  " + temp);

        }

        if(startPositionsIndexes.size() == endPositionsIndexes.size()){
            //launch timer to start cars each 2 seconds....
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(interArrivals.get(interArrivals.size()-1)), ev -> {

                System.out.println("Creating a new car with start at " + startPositionsIndexes.get(startPositionsIndexes.size()-1) + " and end at : " +
                        endPositionsIndexes.get(startPositionsIndexes.size()-1));
                this.animationParts.addCarToAnimation(startPositionsIndexes.get(startPositionsIndexes.size()-1), endPositionsIndexes.get(startPositionsIndexes.size()-1)
                        , PathfindingMode);
                int lastCar = this.animationParts.carElements.size() - 1;
                children.add(this.animationParts.carElements.get(lastCar).getAnimatedCar());
                startPositionsIndexes.remove(startPositionsIndexes.size()-1);
                animationParts.simulate();
                interArrivals.remove(interArrivals.size()-1);


            }));

            timeline.setCycleCount(numberOfCarsToSimulate);
            timeline.play();

        }

    }


    @FXML
    public void runSimulation() {
        animationParts.simulate();
    }

    @FXML
    public void stopSimulation() {
        animationParts.stopSimulate();
    }

    @FXML
    public void selectGreedy() {
        AStarselector.setSelected(false);
        GreedySelector.setSelected(true);
        PathfindingMode = 1;
    }

    @FXML
    public void selectAstar() {
        GreedySelector.setSelected(false);
        AStarselector.setSelected(true);
        PathfindingMode = 2;
    }

    public static int getPoissonRandom(double lambda) {
        double L = Math.exp(-lambda);
        double p = 1.0;
        int k = 0;

        do {
            k++;
            p *= Math.random();
        } while (p > L);

        return k - 1;
    }


    public void roadStatusUpdater(int period){

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(period), ev -> {

            deleteCarAtDestination();
            roadWeightUpdate();

        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    private void deleteCarAtDestination() {

        for (int i = 0; i < animationParts.carElements.size(); i++) {
            if(animationParts.carElements.get(i).car.destinationReached){

                children.remove(animationParts.carElements.get(i).getAnimatedCar());
                this.animationParts.collisionDetection.cars.remove(animationParts.carElements.get(i).getBackendCar());

                System.out.println("Removing a car that took " + animationParts.carElements.get(i).car.getElapsedTimeTotal() +"  seconds to reach destination");
//                System.out.println("This car was waiting at intersection for " + animationParts.carElements.get(i).car.timeAtIntersection + " seconds...");
                animationParts.carElements.remove(i);

            }
        }
    }


    public void roadWeightUpdate(){
        this.animationParts.getRoadWeights(30);
    }


    //doesent work, the initilize method always triggers first.
    public void setFilename(String filename) {
        this.filename = filename;
    }

   /* public void speedLabelUpdate(double newVal){
        speedVariable.setText(handAnimation.getCarSpeed() + " km/h");
    }

    */


}

