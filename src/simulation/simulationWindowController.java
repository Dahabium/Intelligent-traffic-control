package simulation;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class simulationWindowController {

    Board simulationBoard;
    @FXML
    private ScrollPane simulationScrollpane;
    @FXML
    private Group simulationElements;
    @FXML
    private Button createCarbtn, setCarStartbtn, setCarEndbtn, runSimbtn, stopSimbtn, debugbtn;
    @FXML
    private Text speedVariable;
    @FXML
    private TextField StartInput, EndInput;
    @FXML
    private CheckBox GreedySelector, AStarselector;


    private Graph graph;
    private String filename;

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

        //create the parent class for animation of cars and traffic lights

        animationParts = new AnimationParts(this.graph, this.simulationBoard);

        createTrafficLightsManual();


        speedVariable.setText("0 km/h");
        simulationScrollpane.setContent(simulationElements);

    }

    //TODO move this method to animationParts Class

    public void createTrafficLights(){

        //add traffic lights at the end of each edge (ie at the end of each road to trafficlight arraylist.
        for (int i = 0; i < graph.edges.size(); i++) {
            graph.edges.get(i).getRoad().addTrafficLight(graph.edges.get(i).end.x*simulationBoard.SIM_SIZE,graph.edges.get(i).end.y*simulationBoard.SIM_SIZE,
                    5000,3000, 1500,1);
//            animationParts.addTrafficLight(graph.edges.get(i).end.x*simulationBoard.SIM_SIZE ,graph.edges.get(i).end.y*simulationBoard.SIM_SIZE,
//                    5000,3000, 1500,1);
            this.simulationElements.getChildren().add(graph.edges.get(i).getRoad().getTrafficLight().getTrafficLightGui());
        }

        //get the trafficlight arraylist and add all the elements in GUI
//        for (int i = 0; i < this.animationParts.getTrafficLightsV2().size(); i++) {
//
//            this.simulationElements.getChildren().add(this.animationParts.getTrafficLightsV2().get(i).getTrafficLightGui());
//        }
    }

    public void createTrafficLightsManual(){

        graph.edges.get(0).getRoad().addTrafficLight(graph.edges.get(0).end.x * 100 - 50, graph.edges.get(0).end.y * 100 + 50, 7000,6000,1000,1);
        graph.edges.get(1).getRoad().addTrafficLight(graph.edges.get(1).end.x * 100 , graph.edges.get(1).end.y * 100 - 70, 7000,6000,1000,3);


        this.simulationElements.getChildren().add(graph.edges.get(0).getRoad().getTrafficLight().getTrafficLightGui());
        this.simulationElements.getChildren().add(graph.edges.get(1).getRoad().getTrafficLight().getTrafficLightGui());

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
    public void runSimulation() {
        animationParts.simulate();
    }

    @FXML
    public void stopSimulation() {
        animationParts.stopSimulate();
    }
    @FXML
    public void selectGreedy(){
        AStarselector.setSelected(false);
        GreedySelector.setSelected(true);
        PathfindingMode = 1;
    }
    @FXML
    public void selectAstar(){
        GreedySelector.setSelected(false);
        AStarselector.setSelected(true);
        PathfindingMode = 2;
    }

    @FXML
    public void debugbtnaction(){
//        animationParts.printRoadWeights();

        this.animationParts.model.connectFSM();

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

