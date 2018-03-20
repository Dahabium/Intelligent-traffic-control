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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    static GraphicsContext gc = null;
    Path path;
    Scene startScene, runScene, drawScene;
    private boolean release = false;

    //Variable control is here to show which mode is being used in CreateGraph window
    //mode 0 - nothing
    //mode 1 - create verts
    //mode 2 - create edges
    //mode 3 - delete verts
    private int control = 0;
    private Graph graph;

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Intelligent Traffic Control");
        primaryStage.setHeight(500);
        primaryStage.setWidth(500);

        Group root = new Group();

        Group drawSceneElements = new Group();

        startScene = new Scene(root);
        drawScene = new Scene(drawSceneElements, 300, 250);

        primaryStage.setScene(startScene);

        Canvas canvas = new Canvas(500, 500);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Button drawGraphbtn = new Button("Create Configuration");
        drawGraphbtn.setOnAction(e -> primaryStage.setScene(drawScene));

        Button loadGraphbtn = new Button("Load Configuration");
        loadGraphbtn.setOnAction(e -> System.out.println("loading from file"));

        Button testbtn = new Button("Test Graph");
        testbtn.setOnAction(event -> primaryStage.setScene(runScene));

        Button backToMenubtn = new Button("Go to main menu");
        backToMenubtn.setOnAction(e -> primaryStage.setScene(startScene));

        GridPane StartMenuPlacer = new GridPane();

        Button interSectbtn = new Button("Create Intersections");
        Button joinbtn = new Button("Join Intersections");
        Button deletebtn = new Button("Delete");
        Button saveConfigbtn = new Button("Save this configuration");
        Button prnt = new Button("Print check");


        saveConfigbtn.setOnMouseClicked(event -> {
            if (!drawSceneElements.getChildren().isEmpty()){
                //create new xml
                XMLCreator xmlCreator = new XMLCreator();

                xmlCreator.createXML(graph);
            }
        });


        GridPane CreateConfigMenuPlacer = new GridPane();

        CreateConfigMenuPlacer.add(interSectbtn, 0, 0);
        CreateConfigMenuPlacer.add(joinbtn, 1, 0);
        CreateConfigMenuPlacer.add(deletebtn, 2, 0);
        CreateConfigMenuPlacer.add(saveConfigbtn, 0, 1);
        CreateConfigMenuPlacer.add(backToMenubtn, 1, 1);
        CreateConfigMenuPlacer.add(prnt, 2, 1);
        CreateConfigMenuPlacer.setVgap(10);
        CreateConfigMenuPlacer.setHgap(10);

        drawSceneElements.getChildren().add(CreateConfigMenuPlacer);


        graph = new Graph();

        interSectbtn.setOnMouseClicked(event -> {
            control = 1;
            System.out.println(control);
        });
        joinbtn.setOnMouseClicked(event -> {
            control = 2;
            System.out.println(control);
        });
        deletebtn.setOnMouseClicked(event -> {
            control = 3;
            System.out.println(control);
        });
        prnt.setOnMouseClicked(event -> {
            graph.printAdjecency();
        });



        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {

                if (control == 1 && mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED) {

                    Circle vertex = new Circle(mouseEvent.getSceneX(), mouseEvent.getSceneY(), 12);
                    vertex.setFill(Color.BLUE);
                    vertex.setStroke(Color.BLACK);

                    //Before showing a new vertex in gui, check if it doesent intersect with other nodes
                    //Still buggy - works all the time except the first time lol.
                    if(!checkShapeIntersection(vertex, drawSceneElements)){
                        graph.addNode(new Node(mouseEvent.getSceneX(), mouseEvent.getSceneY()));
                        drawSceneElements.getChildren().add(vertex);
                    }

                    //Add a listener to a vertex that will trigger if its being pressed to delete it
                    vertex.addEventHandler(MouseEvent.MOUSE_CLICKED, arg0 -> {

                        if (control == 3) {

                            //remove edges
                            if(graph.getNodeAtCoord(vertex.getCenterX(),vertex.getCenterY()).connections.size() > 0){

                                for (int i = 0; i < drawSceneElements.getChildren().size()-1; i++) {

                                    if(drawSceneElements.getChildren().get(i) instanceof Line){

                                        drawSceneElements.getChildren().remove(drawSceneElements.getChildren().get(i));
                                    }
                                }
                            }

                            graph.removeNode(graph.getNodeAtCoord(vertex.getCenterX(),vertex.getCenterY()));
                            drawSceneElements.getChildren().remove(vertex);
                            System.out.println(drawSceneElements.getChildren());

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
                            } else {
                                //get the last line object and set the end of this line
                                //highlighting
                                vertex.setStrokeWidth(3.0);
                                vertex.setStroke(Color.GREEN);
                                graph.addLineEnd(vertex);

                                //Collision detection for lines. Not working!
                                /*if(!checkShapeIntersection(graph.lines.get(graph.lines.size()-1),drawSceneElements)){
                                    System.out.println("INTERSECT");
                                }*/

                                double stX = graph.lines.get(graph.lines.size()-1).getStartX();
                                double stY = graph.lines.get(graph.lines.size()-1).getStartY();
                                double ndX = graph.lines.get(graph.lines.size()-1).getEndX();
                                double ndY = graph.lines.get(graph.lines.size()-1).getEndY();

                                if (stX != ndX && stY != ndY) {
                                    arrow = new Arrow(stX, stY, ndX, ndY);
                                    //add arrow to gui
                                    drawSceneElements.getChildren().add(arrow);
                                    release = false;
                                }
                            }
                        }
                    });
                }

            }
        };

        drawScene.setOnMouseDragged(mouseHandler);
        drawScene.setOnMouseClicked(mouseHandler);


        StartMenuPlacer.setHgap(10);
        StartMenuPlacer.setVgap(10);
        StartMenuPlacer.setPadding(new Insets(10, 10, 10, 10));

        StartMenuPlacer.add(drawGraphbtn, 1, 1);
        StartMenuPlacer.add(loadGraphbtn, 1, 2);
        StartMenuPlacer.add(testbtn, 1, 3);

        root.getChildren().add(StartMenuPlacer);

        Label label2 = new Label("Go to graph");
        Button button2 = new Button("Go to main menu");
        button2.setOnAction(e -> primaryStage.setScene(startScene));

        VBox layout2 = new VBox(20);
        layout2.getChildren().addAll(label2, button2);
        runScene = new Scene(layout2, 300, 250);

        layout2.getChildren().add(canvas);


        final long startNanoTime = System.nanoTime();


        //Tester class for crating graph
        DummyGraph dummyGraph = new DummyGraph();
        Graph graph1 = dummyGraph.createDummyGraph();

        graph1.printAdjecency();
        graph1.showGraph(gc);



        primaryStage.setScene(startScene);
        primaryStage.show();
    }


    public GraphicsContext getGC() {
        return gc;
    }


    //Simple Collision detection, only for vertecies now

    private boolean checkShapeIntersection(Shape block, Group elements) {

        boolean collisionDetected = false;

        ArrayList<Circle> allVertsInGroup = new ArrayList<>();
//        ArrayList<Line> allLineInGroup = new ArrayList<>();

        for (int i = 0; i < elements.getChildren().size()-1; i++) {
            if(elements.getChildren().get(i) instanceof Circle){
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
            }
            else return false;
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

