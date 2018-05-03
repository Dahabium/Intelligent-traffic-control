package simulation;

import backend.Car;
import backend.Greedy;
import backend.Pathfinding;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class simulationWindowController {

    @FXML
    private ScrollPane simulationScrollpane;

    @FXML
    private Group simulationElements;

    @FXML
    private Button createCarbtn, setCarStartbtn, setCarEndbtn, runSimbtn;

    @FXML
    private Text speedVariable;

    @FXML
    private TextField StartInput,EndInput;

    private Graph graph;
    private String filename;
//    private AnimationParts handAnimation;
    private ArrayList<AnimationParts> animationParts;

    private int carStart;
    private int carEnd;

    Board simulationBoard;

    //TODO PASSING THE FILENAME

    public void initialize() {

        XMLLoader graphLoader = new XMLLoader("graph2");
        graph = graphLoader.getGraph();

        animationParts = new ArrayList<>();


        javafx.scene.image.Image img = new Image("background.JPG", 800,800,false,false);
        ImageView imgView = new ImageView(img);
        simulationElements.getChildren().add(imgView);
                                    
        simulationBoard = new Board(15,15);

        simulationBoard.setBoard(graph);

        simulationElements.getChildren().add(simulationBoard);

        //===============================ANIMATION==========================

//        Car car = new Car(graph.getNodeByIndex(0),graph.getNodeByIndex(1),graph.getNodeByIndex(5),graph);
//
//        Pathfinding pathfinding = new Pathfinding(graph);

        //TODO fix A* algorithm

//        System.out.println("Astar path " + pathfinding.Astar(car,graph));


//        AnimationParts demoAnimation = new AnimationParts(getRouteSequence(0,5), graph, simulationBoard);
//
//        animationParts.add(demoAnimation);
//
//        simulationElements.getChildren().add(demoAnimation.getAnimatedCar());


        speedVariable.setText("0 km/h");
        simulationScrollpane.setContent(simulationElements);

    }


    @FXML
    public void createCar(){

        System.out.println("COMPUTED PATH " + getRouteSequence(carStart,carEnd));

        AnimationParts carAnimation = new AnimationParts(getRouteSequence(carStart,carEnd),graph,simulationBoard);


        this.animationParts.add(carAnimation);
        this.simulationElements.getChildren().add(carAnimation.getAnimatedCar());
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

        for (int i = 0; i < animationParts.size(); i++) {

            animationParts.get(i).animationTimer.start();
        }
    }


    //doesent work, the initilize method always triggers first.
    public void setFilename(String filename){
        this.filename = filename;
    }

   /* public void speedLabelUpdate(double newVal){
        speedVariable.setText(handAnimation.getCarSpeed() + " km/h");
    }

    public AnimationParts getHandAnimation(){
        return this.handAnimation;
    }*/

    public ArrayList<Integer> getRouteSequence(int start, int end){
       Greedy greedy = new Greedy(graph.getNodeByIndex(start),graph.getNodeByIndex(end),graph);
       return greedy.getIntPath();
    }

}

