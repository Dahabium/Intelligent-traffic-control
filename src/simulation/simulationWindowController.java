package simulation;

import backend.TrafficLightController;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.animation.AnimationTimer;

import java.util.Timer;
import java.util.TimerTask;

public class simulationWindowController {

    Board simulationBoard;
    @FXML
    private ScrollPane simulationScrollpane;
    @FXML
    private Group simulationElements;
    @FXML
    private Button createCarbtn, setCarStartbtn, setCarEndbtn, runSimbtn, stopSimbtn, debugbtn, launchCars;

    @FXML
    private TextField StartInput, EndInput;
    @FXML
    private CheckBox GreedySelector, AStarselector;
    private AnimationTimer animationTimer;
    private Graph graph;
    private String filename;


    private MainController mainController;

    private int PathfindingMode;

    private AnimationParts animationParts;


    private int carStart;
    private int carEnd;

    //TODO PASSING THE FILENAME

    public void initialize() {
        PathfindingMode = 1;

        XMLLoader graphLoader = new XMLLoader("graph2");
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
        this.mainController = new MainController(this.animationParts, 10000, 1);

        this.animationParts.model.map.runAllConnectedFSMS();

        if(this.mainController.mode == 2 ){
            updateCycleSander();

        }

        simulationScrollpane.setContent(simulationElements);

    }

    @FXML
    public void debugbtnaction() {

//        this.mainController.getTLCcontroller().updateCycle();
//
//        this.animationParts.model.map.runAllConnectedFSMS();

        this.animationParts.model.map.intersectionFSMS.get(0).runFSM_Vertical_Red();
    }

    public void updateCycleSander(){
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Cycle Runned in simWinControl");
                mainController.getTLCcontroller().updateCycle();
            }
        }, 0, (int)(mainController.getTLCcontroller().getGTime()*2)+4000);
    }


    //This class creates the traffic lights and adds them to gui,
    // then we connect the traffic lights FSM cycles via connectFSM() method called in animationParts class

    public void createTrafficLights() {

        //cases: T- Section, Cross-Intersection


        for (int i = 0; i < animationParts.getRoads().size(); i++) {

            //check if more than one road is directed towards an intersection => create traffic lights at the end
            if (animationParts.model.map.getIncomingRoads(animationParts.getRoads().get(i).end).size() > 2) {

                if (animationParts.getRoads().get(i).getDirection() == 6) {

                    animationParts.getRoads().get(i).addTrafficLight(graph.edges.get(i).end.x * simulationBoard.SIM_SIZE - 70, graph.edges.get(i).end.y * simulationBoard.SIM_SIZE + 70,
                            5000, 3000, 1500, 1);
                    this.simulationElements.getChildren().add( animationParts.getRoads().get(i).getTrafficLight().getTrafficLightGui());
                }

                else if (animationParts.getRoads().get(i).getDirection() == 4) {
                    animationParts.getRoads().get(i).addTrafficLight(graph.edges.get(i).end.x * simulationBoard.SIM_SIZE + 130, graph.edges.get(i).end.y * simulationBoard.SIM_SIZE - 50,
                            5000, 3000, 1500, 1);
                    this.simulationElements.getChildren().add( animationParts.getRoads().get(i).getTrafficLight().getTrafficLightGui());
                }
                else if  (animationParts.getRoads().get(i).getDirection() == 8) {

                    animationParts.getRoads().get(i).addTrafficLight(graph.edges.get(i).end.x * simulationBoard.SIM_SIZE + 70, graph.edges.get(i).end.y * simulationBoard.SIM_SIZE + 130,
                            5000, 3000, 1500, 1);
                    this.simulationElements.getChildren().add( animationParts.getRoads().get(i).getTrafficLight().getTrafficLightGui());
                }
                else if (animationParts.getRoads().get(i).getDirection() == 2) {
                    animationParts.getRoads().get(i).addTrafficLight(graph.edges.get(i).end.x * simulationBoard.SIM_SIZE, graph.edges.get(i).end.y * simulationBoard.SIM_SIZE - 130,
                            5000, 3000, 1500, 1);
                    this.simulationElements.getChildren().add( animationParts.getRoads().get(i).getTrafficLight().getTrafficLightGui());

                }

            }
        }

        this.animationParts.model.connectFSM();


    }


    public void createTrafficLightsManual() {

        animationParts.model.map.roads.get(0).addTrafficLight(graph.edges.get(0).end.x * 100 - 50, graph.edges.get(0).end.y * 100 + 50, 15000, 6000, 1000, 1);
//        model.map.roads.get(1).addTrafficLight(graph.edges.get(1).end.x * 100 , graph.edges.get(1).end.y * 100 - 70, 1000000,6000,1000,3);

        //add shadow cars....

        this.simulationElements.getChildren().add(animationParts.model.map.roads.get(0).getTrafficLight().getTrafficLightGui());
//        this.simulationElements.getChildren().add(model.map.roads.get(1).getTrafficLight().getTrafficLightGui());

    }

    @FXML
    public void createCar() {

        this.animationParts.addCarToAnimation(carStart, carEnd, PathfindingMode);

        int lastCar = this.animationParts.carElements.size() - 1;

        this.simulationElements.getChildren().add(this.animationParts.carElements.get(lastCar).getAnimatedCar());

    }

    @FXML
    public void setCarStart() {
        this.carStart = Integer.parseInt(StartInput.getText());
        System.out.println("CARSTART " + this.carStart);

    }

    @FXML
    public void setCarEnd() {
        this.carEnd = Integer.parseInt(EndInput.getText());
        System.out.println("CAREND " + this.carEnd);

    }

    @FXML
    public void launchCarsSim(){
//        for (int i = 0; i < animationParts.model.map.roads.size(); i++) {
//            if(animationParts.model.map.getIncomingRoads(animationParts.getRoads().get(i).end).size() <= 1){
//
//
//                if(animationParts.getRoads().get(i).end.index == )
//                this.animationParts.addCarToAnimation(animationParts.getRoads().get(i).end.index, animationParts.getRoads().get(i).,
//                        PathfindingMode);
//
//                int lastCar = this.animationParts.carElements.size() - 1;
//
//                this.simulationElements.getChildren().add(this.animationParts.carElements.get(lastCar).getAnimatedCar());
//
//            }
//        }

        this.animationParts.addCarToAnimation(graph.nodes.get(0).index, graph.nodes.get(2).index, PathfindingMode);
        int lastCar = this.animationParts.carElements.size() - 1;
        this.simulationElements.getChildren().add(this.animationParts.carElements.get(lastCar).getAnimatedCar());

//        this.animationParts.addCarToAnimation(graph.nodes.get(2).index, graph.nodes.get(0).index, PathfindingMode);
//        lastCar = this.animationParts.carElements.size() - 1;
//        this.simulationElements.getChildren().add(this.animationParts.carElements.get(lastCar).getAnimatedCar());

        this.animationParts.addCarToAnimation(graph.nodes.get(3).index, graph.nodes.get(4).index, PathfindingMode);
        lastCar = this.animationParts.carElements.size() - 1;
        this.simulationElements.getChildren().add(this.animationParts.carElements.get(lastCar).getAnimatedCar());
//
//        this.animationParts.addCarToAnimation(graph.nodes.get(4).index, graph.nodes.get(3).index, PathfindingMode);
//        lastCar = this.animationParts.carElements.size() - 1;
//        this.simulationElements.getChildren().add(this.animationParts.carElements.get(lastCar).getAnimatedCar());

        animationParts.simulate();


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



    public void roadStatusUpdater(int period){

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                roadWeightUpdate();
            }
        }, 0, period);

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

