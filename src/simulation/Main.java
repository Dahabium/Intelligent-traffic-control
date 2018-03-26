package simulation;

import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

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
    private Graph graph;
    private String fileName = "graph2";
    private GridSnapper gridSnapper = new GridSnapper();
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
        BorderPane drawScenePane = new BorderPane();



        primaryStage.setScene(startScene);



        //============================START MENU START===============================

        Button drawGraphbtn = new Button("Create Configuration");
        drawGraphbtn.setOnAction(e -> {
            graph = new Graph();
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

            //TODO - test if this works
            graph.showGraph(simulationElements);
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

        Image image = new Image("1.jpeg");
        board.getTileatCoord(5,5).getChildren().add(new ImageView(image));


        boardPane.setContent(board);

        boardPane.setLayoutX(20);
        boardPane.setLayoutY(100);

        drawSceneElements.getChildren().add(boardPane);



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

        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {

                if (control == 1 && mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED) {

                    double mX = mouseEvent.getSceneX();
                    double mY = mouseEvent.getSceneY();

                    double [] gridXY = gridSnapper.getGridXY(mX, mY);

                    double gridX = gridXY[0];
                    double gridY = gridXY[1];

                    Circle vertex = new Circle(gridX, gridY, 12);
                    vertex.setFill(Color.BLUE);
                    vertex.setStroke(Color.BLACK);

                    //Before showing a new vertex in gui, check if it doesent intersect with other nodes
                    //TODO remove collision if we are using a grid

                    if (!checkShapeIntersection(vertex, drawSceneElements)) {

                        graph.addNodeV2(indexCount, gridX, gridY);

                        System.out.println("Node added  " +graph.getNodeByIndex(indexCount));

                        indexCount++;
                        drawSceneElements.getChildren().add(vertex);
                    }

                    //Add a listener to a vertex that will trigger if its being pressed to delete it
                    vertex.addEventHandler(MouseEvent.MOUSE_CLICKED, arg0 -> {

                        //remove edges
                        if (control == 3) {

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

                        //Add edges
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


                                arrow = new Arrow(stX, stY, ndX, ndY);

                                graph.addEdge(graph.getNodeAtCoord(stX, stY), graph.getNodeAtCoord(ndX, ndY));

                                drawSceneElements.getChildren().add(arrow);
                                System.out.println(drawSceneElements.getChildren());
                                release = false;
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


    //Simple Collision detection, only for vertecies now
    private boolean checkShapeIntersection(Shape block, Group elements) {

        boolean collisionDetected = false;

        ArrayList<Circle> allVertsInGroup = new ArrayList<>();
//        ArrayList<Line> allLineInGroup = new ArrayList<>();

        for (int i = 0; i < elements.getChildren().size() - 1; i++) {
            if (elements.getChildren().get(i) instanceof Circle) {
                allVertsInGroup.add((Circle) elements.getChildren().get(i));
            }
           /* if(elements.getChildren().get(i) instanceof Line){
                allLineInGroup.add((Line) elements.getChildren().get(i));
            }*/
        }


        for (Shape shape : allVertsInGroup) {

            if (shape != block) {
                Shape intersect = Shape.intersect(block, shape);
                if (intersect.getBoundsInLocal().getWidth() != -1) {
                    collisionDetected = true;
                }
            } else return false;
        }


       /* for (Shape shape : allLineInGroup) {

            if (shape != block) {
                Shape intersect = Shape.intersect(block, shape);
                if (intersect.getBoundsInLocal().getWidth() != -1) {
                    collisionDetected = true;
                }
            }
            else return false;
        }*/


        if (collisionDetected) {
            return true;
        } else {
            return false;
        }
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

