package simulation;

import backend.Greedy;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class simulationWindowController {

    @FXML
    private ScrollPane simulationScrollpane;

    @FXML
    private Group simulationElements;

    @FXML
    private Button createCarbtn, setCarStartbtn, setCarEndbtn, runSimbtn, stopSimbtn;

    @FXML
    private Text speedVariable;

    @FXML
    private TextField StartInput,EndInput;

    private Graph graph;
    private String filename;

    private AnimationParts animationParts;

    private int carStart;
    private int carEnd;

    Board simulationBoard;

    //TODO PASSING THE FILENAME
    //TODO fix A* algorithm


    public void initialize() {

        XMLLoader graphLoader = new XMLLoader("graph2");
        graph = graphLoader.getGraph();

        javafx.scene.image.Image img = new Image("background.JPG", 800,800,false,false);
        ImageView imgView = new ImageView(img);
        simulationElements.getChildren().add(imgView);

        simulationBoard = new Board(15,15);

        simulationBoard.setBoard(graph);

        simulationElements.getChildren().add(simulationBoard);

        //===============================ANIMATION==========================

        //create the parent class for animation

        animationParts = new AnimationParts(this.graph,this.simulationBoard);


        speedVariable.setText("0 km/h");
        simulationScrollpane.setContent(simulationElements);

    }


    @FXML
    public void createCar(){


        this.animationParts.addCarToAnimation(carStart,carEnd);

        int lastCar =  this.animationParts.carElements.size()-1;

        this.simulationElements.getChildren().add(this.animationParts.carElements.get(lastCar).getAnimatedCar());

    }

    @FXML
    public void setCarStart(){
        this.carStart = Integer.parseInt(StartInput.getText());
        System.out.println("CARSTART " + this.carStart);

    }

    @FXML
    public void setCarEnd(){
        this.carEnd = Integer.parseInt(EndInput.getText());
        System.out.println("CAREND " + this.carEnd);

    }

    @FXML
    public void runSimulation(){

        animationParts.simulate();
    }

    @FXML
    public void stopSimulation(){
        animationParts.stopSimulate();
    }


    //doesent work, the initilize method always triggers first.
    public void setFilename(String filename){
        this.filename = filename;
    }

   /* public void speedLabelUpdate(double newVal){
        speedVariable.setText(handAnimation.getCarSpeed() + " km/h");
    }

    */


}

