package simulation;

import javafx.animation.PathTransition;
import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class Main extends Application {
    public StackPane stackPane = new StackPane();;
    Path path;
    //runscene - scene for simulation, drawscene - scene for creating a graph. startscene - startup menu
    Scene startScene, runScene, drawScene;
    private boolean release = false;

    //Variable control is here to show which mode is being used in CreateGraph window
    //mode 0 - nothing
    //mode 1 - create verts
    //mode 2 - create edges
    //mode 3 - delete verts + edges

    private int control = 0;
    private int indexCount = 0;
    public Graph graph;
    private String fileName = "graph2";
//    private GridSnapper gridSnapper = new GridSnapper();
    Board board;

    public static void main(String[] args) {

        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Intelligent Traffic Control");
        primaryStage.setHeight(600);
        primaryStage.setWidth(600);

        Group root = new Group();
        Group drawSceneElements = new Group();
        Group simulationElements = new Group();

        startScene = new Scene(root);
        drawScene = new Scene(drawSceneElements, 500, 500);

        primaryStage.setScene(startScene);

        //============================START MENU START===============================

        Button drawGraphbtn = new Button("Create Configuration");
        drawGraphbtn.setOnAction(e -> {
            graph = new Graph();
            board = new Board(30,10);


            primaryStage.setScene(drawScene);
        });

        TextField loadGraphTXT = new TextField(fileName);
        loadGraphTXT.textProperty().addListener((observable, oldValue, newValue) -> {
            //File Name of Graph1.xml = Graph1
            fileName = observable.getValue();

        });

        Button loadGraphbtn = new Button("Load Configuration");
        loadGraphbtn.setOnAction(e ->
        {
            System.out.println("loading from file " + fileName);
            XMLLoader graphLoader = new XMLLoader(fileName);

            graph = graphLoader.getGraph();

            javafx.scene.image.Image img = new Image("background.JPG", 3000,1000,false,false);
            ImageView imgView = new ImageView(img);
            simulationElements.getChildren().add(imgView);

            Board simulationBoard = new Board(30,10);
            simulationBoard.setBoard(graph);

            ScrollPane simulationPane = new ScrollPane();
            simulationPane.setContent(imgView);
            simulationPane.setContent(simulationBoard);

            simulationPane.setFitToHeight(false);
            simulationPane.setFitToWidth(false);

            simulationElements.getChildren().add(simulationPane);

            ArrayList<Integer> arr = new ArrayList<>();
            arr.addAll(Arrays.asList(0,1,2,3));

            AnimationParts animationParts = new AnimationParts(arr, graph, simulationBoard);

            Group animGroup = new Group(animationParts.getAnimatedCar());

            simulationElements.getChildren().add(animGroup);

            primaryStage.setScene(runScene);

//            primaryStage.show();
        });

        Button testbtn = new Button("Show Test Graph");
        testbtn.setOnAction(event -> {

            //Tester class for creating graph
            DummyGraph dummyGraph = new DummyGraph();
            Graph dummyGraph1 = dummyGraph.createModelTestGraph();

            dummyGraph1.showGraph(simulationElements);
            primaryStage.setScene(runScene);


            XMLCreator xmlCreator = new XMLCreator();
            xmlCreator.createXML(dummyGraph1);

        });

        Button backToMenubtn = new Button("Go to main menu");
        backToMenubtn.setOnAction(e -> primaryStage.setScene(startScene));

        GridPane StartMenuPlacer = new GridPane();
        StartMenuPlacer.setHgap(10);
        StartMenuPlacer.setVgap(10);
        StartMenuPlacer.setPadding(new Insets(10, 10, 10, 10));
        StartMenuPlacer.add(drawGraphbtn, 1, 1);
        StartMenuPlacer.add(loadGraphbtn, 1, 2);
        StartMenuPlacer.add(loadGraphTXT, 2, 2);
        StartMenuPlacer.add(testbtn, 1, 3);
        root.getChildren().add(StartMenuPlacer);

        //============================START MENU END===============================

        //============================CREATE GRAPH WINDOW START===============================

        GridPane CreateConfigMenuPlacer = new GridPane();
        Button interSectbtn = new Button("Create Intersections");
        Button joinbtn = new Button("Join Intersections");
        Button deletebtn = new Button("Delete");
        Button saveConfigbtn = new Button("Save this configuration");
        Button prnt = new Button("Print check");
        CreateConfigMenuPlacer.add(interSectbtn, 0, 0);
        CreateConfigMenuPlacer.add(joinbtn, 1, 0);
        CreateConfigMenuPlacer.add(deletebtn, 2, 0);
        CreateConfigMenuPlacer.add(saveConfigbtn, 0, 1);
        CreateConfigMenuPlacer.add(backToMenubtn, 1, 1);
        CreateConfigMenuPlacer.add(prnt, 2, 1);
        CreateConfigMenuPlacer.setVgap(10);
        CreateConfigMenuPlacer.setHgap(10);

        drawSceneElements.getChildren().add(CreateConfigMenuPlacer);

        ScrollPane boardPane = new ScrollPane();
        Board board = new Board(10,10);

        for (int i = 0; i < board.getBoardSizeX(); i++) {
            for (int j = 0; j < board.getBoardSizeY(); j++) {
                Tile tile = new Tile();
                board.setTileAtCoordinates(tile,i,j);
            }
        }

        boardPane.setContent(board);

        boardPane.setLayoutX(20);
        boardPane.setLayoutY(100);

        drawSceneElements.getChildren().add(boardPane);

//        // vertical lines
//        for(int i = 0 ; i < drawScene.getWidth() ; i+=30){
//            Line line = new Line(i,30,i,(drawScene.getHeight() - drawScene.getHeight()%30));
//            drawSceneElements.getChildren().add(line);
//        }
//
//        // horizontal lines
//        for(int i = 30 ; i < drawScene.getHeight(); i+=30){
//            Line line = new Line(30, i, drawScene.getWidth(), i);
//            drawSceneElements.getChildren().add(line);
//        }


        interSectbtn.setOnMouseClicked(event -> {
            control = 1;
            System.out.println("Vertex creation mode (code " + control + ")" );
        });
        joinbtn.setOnMouseClicked(event -> {
            control = 2;
            System.out.println("Edges creation mode (code " + control + ")");
        });
        deletebtn.setOnMouseClicked(event -> {
            control = 3;
            System.out.println("Vertex deletion mode (code " + control + ")");
        });
        prnt.setOnMouseClicked(event -> {
            graph.printAdjecency();
        });
        saveConfigbtn.setOnMouseClicked(event -> {
            try {
                System.out.println(" graph.export");
                graph.export();
            } catch (ParserConfigurationException parse) {
                System.out.println("ParserConfigurationException");
            } catch (FileNotFoundException notFound) {
                System.out.println("ParserConfigurationException");
            } catch (IOException io) {
                System.out.println("IOException");
            }
        });

        //============================CREATE GRAPH WINDOW END===============================

        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent mouseEvent) {


                if (control == 1 && mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED) {

                    double[] gridXY = board.getGridXY(mouseEvent.getSceneX(), mouseEvent.getSceneY());

                    double gridX = gridXY[0];
                    double gridY = gridXY[1];

                    int x = (int)gridXY[2];
                    int y = (int)gridXY[3];

                    Circle vertex = new Circle(gridX, gridY, 12);
                    vertex.setFill(Color.BLUE);
                    vertex.setStroke(Color.BLACK);

                    //Before showing a new vertex in gui, check if it doesent intersect with other nodes

                    if (mouseEvent.isShiftDown()) {
                        graph.addNodeV2(indexCount, gridX, gridY, x, y, 1);
                    } else graph.addNodeV2(indexCount, gridX, gridY, x, y, 0);

                    System.out.println("Node added  " + indexCount + " x,y :" + graph.getNodeByIndex(indexCount).x + " " + graph.getNodeByIndex(indexCount).y);

                    indexCount++;
                    drawSceneElements.getChildren().add(vertex);

                    //Add a listener to a vertex that will trigger if its being pressed to delete it
                    vertex.addEventHandler(MouseEvent.MOUSE_CLICKED, arg0 -> {

                        if (control == 3) {

                            //remove edges
                            if (graph.getAdjecents(graph.getNodeAtCoord(vertex.getCenterX(), vertex.getCenterY())).size() > 0) {

                                System.out.println("Removing edge(es) ");

                                for (int i = 0; i < drawSceneElements.getChildren().size(); i++) {
                                    System.out.println(drawSceneElements.getChildren().get(i));
                                    if (drawSceneElements.getChildren().get(i) instanceof Path) {

                                        if(drawSceneElements.getChildren().get(i).contains(vertex.getCenterX(), vertex.getCenterY()) ){

                                            drawSceneElements.getChildren().remove(drawSceneElements.getChildren().get(i));
                                            System.out.println(drawSceneElements.getChildren().get(i));
                                        }

                                    }
                                }
                            }

                            System.out.println("Node removed " + graph.getNodeAtCoord(vertex.getCenterX(), vertex.getCenterY()));
                            graph.removeNode(graph.getNodeAtCoord(vertex.getCenterX(), vertex.getCenterY()));
                            drawSceneElements.getChildren().remove(vertex);
                        }

                        if (control == 2) {

                            //reset strokes
                            for (int i = 0; i < drawSceneElements.getChildren().size() - 1; i++) {
                                if (drawSceneElements.getChildren().get(i) instanceof Circle) {
                                    ((Circle) drawSceneElements.getChildren().get(i)).setStrokeWidth(1.0);
                                    ((Circle) drawSceneElements.getChildren().get(i)).setStroke(Color.BLACK);
                                }
                            }

                            Arrow arrow;

                            if (!release) {
                                //Create new Line object and set the start of it
                                graph.addLineStart(vertex);

                                //highlighting
                                vertex.setStrokeWidth(3.0);
                                vertex.setStroke(Color.RED);

                                release = true;
                            }
                            else {
                                //get the last line object and set the end of this line
                                //highlighting
                                vertex.setStrokeWidth(3.0);
                                vertex.setStroke(Color.GREEN);
                                graph.addLineEnd(vertex);

                                double stX = graph.lines.get(graph.lines.size() - 1).getStartX();
                                double stY = graph.lines.get(graph.lines.size() - 1).getStartY();
                                double ndX = graph.lines.get(graph.lines.size() - 1).getEndX();
                                double ndY = graph.lines.get(graph.lines.size() - 1).getEndY();

                                if (stX != ndX || stY != ndY) {

                                    arrow = new Arrow(stX, stY, ndX, ndY);

                                    graph.addEdge(graph.getNodeAtCoord(stX, stY), graph.getNodeAtCoord(ndX, ndY));
                                    drawSceneElements.getChildren().add(arrow);
                                    release = false;
                                }
                            }
                        }
                    });
                }


            }
        };

//        drawScene.setOnMouseDragged(mouseHandler);
        drawScene.setOnMouseClicked(mouseHandler);


        Button button2 = new Button("Go to main menu");
        button2.setOnAction(e -> primaryStage.setScene(startScene));

        VBox layout2 = new VBox(20);
        layout2.getChildren().add(button2);
        runScene = new Scene(simulationElements, 300, 250);


        primaryStage.setScene(startScene);
        primaryStage.show();

//        final long startNanoTime = System.nanoTime();
    }

    private Group addAnimation() {
        //Drawing a Circle
        Circle circle = new Circle();

        //Setting the position of the circle
        circle.setCenterX(300.0f);
        circle.setCenterY(135.0f);

        //Setting the radius of the circle
        circle.setRadius(25.0f);

        //Setting the color of the circle
        circle.setFill(Color.BROWN);

        //Setting the stroke width of the circle
        circle.setStrokeWidth(20);

        //Creating a Path
        Path path = new Path();

        //Moving to the starting point
        MoveTo moveTo = new MoveTo(108, 71);

        //Creating 1st line
        LineTo line1 = new LineTo(321, 161);

        //Creating 2nd line
        LineTo line2 = new LineTo(126,232);

        //Creating 3rd line
        LineTo line3 = new LineTo(232,52);

        //Creating 4th line
        LineTo line4 = new LineTo(269, 250);

        //Creating 5th line
        LineTo line5 = new LineTo(108, 71);

        //Adding all the elements to the path
        path.getElements().add(moveTo);
        path.getElements().addAll(line1, line2, line3, line4, line5);

        //Creating the path transition
        PathTransition pathTransition = new PathTransition();

        //Setting the duration of the transition
        pathTransition.setDuration(Duration.millis(1000));

        //Setting the node for the transition
        pathTransition.setNode(circle);

        //Setting the path for the transition
        pathTransition.setPath(path);

        //Setting the orientation of the path
        pathTransition.setOrientation(
                PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

        //Setting the cycle count for the transition
        pathTransition.setCycleCount(2);

        //Setting auto reverse value to true
        pathTransition.setAutoReverse(false);

        //Playing the animation
        pathTransition.play();

        Group root = new Group(circle);

        return root;

    }



    public Scene getDrawScene()
    {
        return drawScene;
    }

}






/*
Simple animation in JavaFX

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                double x = 232 + 128 * Math.cos(t);
                double y = 232 + 128 * Math.sin(t);

                // background image clears canvas
                gc.setFill(Color.WHITE);
                gc.fillRect(0,0,500,500);
                gc.setFill(Color.CYAN);
                gc.fillRect(x,y,50,50);

            }
        }.start();
 */

