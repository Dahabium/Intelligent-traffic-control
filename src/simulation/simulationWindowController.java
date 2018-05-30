package simulation;

import backend.Model;
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

        //create the parent class for animation of all cars and traffic lights

        animationParts = new AnimationParts(this.graph, this.simulationBoard);

//        createTrafficLightsManual();

        createTrafficLights();



        speedVariable.setText("0 km/h");
        simulationScrollpane.setContent(simulationElements);

    }


    public void createTrafficLights() {

        //cases: T- Section, Cross-Intersection

//        if there is a parrallel road ( ie same direction after intersection
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

        for (int i = 0; i < animationParts.model.map.intersectionFSMS.size(); i++) {

            System.out.println("shit " + animationParts.model.map.intersectionFSMS.get(i).getAllFSMRoads().size());

            if(animationParts.model.map.intersectionFSMS.get(i).getAllFSMRoads().size() == 4){

//                System.out.println("Traffic light at road " + animationParts.getRoads().get(i).start.index +"  " + animationParts.getRoads().get(i).end.index + "  " +
//                "Share the same sequence as road " + animationParts.getRoads().get(i).roadWithSameFSM.start.index + "  " + animationParts.getRoads().get(i).roadWithSameFSM.end.index);
//                System.out.println("yas");
            }
        }

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

    @FXML
    public void debugbtnaction() {
//        animationParts.printRoadWeights();


//        this.animationParts.model.startCycle();
//
//        this.animationParts.model.map.roads.get(0).getTrafficLight().runGreen();

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

