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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    static GraphicsContext gc = null;
    private boolean release = false;
    private int control = 0;
    Path path;

    Scene startScene, runScene, drawScene;

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Intelligent Traffic Control");
        primaryStage.setHeight(500);
        primaryStage.setWidth(500);

        Group root = new Group();

        Group drawSceneElements = new Group();

        startScene = new Scene(root);
        drawScene = new Scene(drawSceneElements,300,250);

        primaryStage.setScene(startScene);

        Canvas canvas = new Canvas(500,500);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Button drawGraphbtn =  new Button("Create Configuration");
        drawGraphbtn.setOnAction(e-> primaryStage.setScene(drawScene));

        Button loadGraphbtn= new Button("Load Configuration");
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

        GridPane CreateConfigMenuPlacer = new GridPane();

        CreateConfigMenuPlacer.add(interSectbtn,0,0);
        CreateConfigMenuPlacer.add(joinbtn,1,0);
        CreateConfigMenuPlacer.add(deletebtn,2,0);
        CreateConfigMenuPlacer.add(saveConfigbtn,0,1);
        CreateConfigMenuPlacer.add(backToMenubtn,1,1);
        CreateConfigMenuPlacer.setVgap(10);
        CreateConfigMenuPlacer.setHgap(10);

        drawSceneElements.getChildren().add(CreateConfigMenuPlacer);



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

        GraphicsGraph graphicsGraph = new GraphicsGraph();


        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent mouseEvent) {
                if (control == 1) {

                    Circle vertex = new Circle(mouseEvent.getSceneX(), mouseEvent.getSceneY(), 12);
                    vertex.setFill(Color.BLUE);
                    vertex.setStroke(Color.BLACK);

                    graphicsGraph.addVertex(mouseEvent.getSceneX(), mouseEvent.getSceneY());

                    drawSceneElements.getChildren().add(vertex);

                    //Add a listener to a vertex that will trigger if its being pressed to delete it
                    vertex.addEventHandler(MouseEvent.MOUSE_CLICKED, arg0 -> {

                        if (control == 3){

                            drawSceneElements.getChildren().remove(vertex);

                            graphicsGraph.removeVertex(vertex);
                        }

                        if(control == 2){

                            Line line;
                            
                            if(!release){

                                graphicsGraph.addLineStart(vertex);
                                vertex.setStroke(Color.RED);

                                release = true;
                            }
                            else {
                                vertex.setStroke(Color.GREEN);
                                graphicsGraph.addLineEnd(vertex);
                                double stX = graphicsGraph.lines.get(graphicsGraph.lines.size()-1).getStartX();
                                double stY = graphicsGraph.lines.get(graphicsGraph.lines.size()-1).getStartY();
                                double ndX = graphicsGraph.lines.get(graphicsGraph.lines.size()-1).getEndX();
                                double ndY = graphicsGraph.lines.get(graphicsGraph.lines.size()-1).getEndY();
                                line = new Line(stX, stY,ndX,ndY);

                                drawSceneElements.getChildren().add(line);
                                release = false;
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

        StartMenuPlacer.add(drawGraphbtn,1,1);
        StartMenuPlacer.add(loadGraphbtn,1,2);
        StartMenuPlacer.add(testbtn,1,3);

        root.getChildren().add(StartMenuPlacer);

        Label label2= new Label("Go to graph");
        Button button2= new Button("Go to main menu");
        button2.setOnAction(e -> primaryStage.setScene(startScene));

        VBox layout2= new VBox(20);
        layout2.getChildren().addAll(label2, button2);
        runScene = new Scene(layout2,300,250);

        layout2.getChildren().add(canvas);


        final long startNanoTime = System.nanoTime();


        List<Node> nodeList = new ArrayList<>(4);

        Node n1 = new Node("n1",50,50);
        Node n2 = new Node("n2",120, 50);
        Node n3 = new Node("n3",50, 120 );
        Node n4 = new Node("n4",120,120);

        nodeList.add(n1);
        nodeList.add(n2);
        nodeList.add(n3);
        nodeList.add(n4);

        Graph grp = new Graph(nodeList);

        grp.addEdge(n1,n2,1,1,1);
        grp.addEdge(n1,n3,1,2,1);
        grp.addEdge(n2,n1,1,1,1);
        grp.addEdge(n2,n3,1,1,1);
        grp.addEdge(n2,n4,1,2,1);
        grp.addEdge(n3,n1,1,1,1);
        grp.addEdge(n3,n2,1,1,1);
        grp.addEdge(n3,n4,1,1,1);
        grp.addEdge(n4,n2,1,1,1);
        grp.addEdge(n4,n3,1,1,1);
//        grp.addEdge(n4,n1,1,1,1);

        for (int i = 0; i < grp.nodes.get(0).connections.size(); i++) {
            System.out.println("node " + grp.nodes.get(0).name  + " is connected to " + grp.nodes.get(0).connections.get(i).end.name);
        }

        //Show the intersections in GUI
        for (int i = 0; i < grp.nodes.size(); i++){
            gc.fillOval(grp.nodes.get(i).Xpos, grp.nodes.get(i).Ypos,30,30);
            gc.setFill(Color.WHITE);
            gc.fillText(grp.nodes.get(i).name, grp.nodes.get(i).Xpos+7, grp.nodes.get(i).Ypos+15);
            gc.setFill(Color.BLACK);

        }

        //Show the roads between intersections
        for (int i = 0; i < grp.nodes.size(); i++) {
            //look at edges outcoming from each node
            for (int j = 0; j < grp.nodes.get(i).connections.size(); j++) {
                for (int k = 0; k < grp.nodes.get(i).connections.get(j).outcominglanes; k++) {
                    gc.strokeLine(grp.nodes.get(i).connections.get(j).start.Xpos +15+k*2, grp.nodes.get(i).connections.get(j).start.Ypos +15+k*2,
                            grp.nodes.get(i).connections.get(j).end.Xpos+15+k*2, grp.nodes.get(i).connections.get(j).end.Ypos+15+k*2);
                }

            }
        }



        primaryStage.setScene(startScene);
        primaryStage.show();
    }


    public GraphicsContext getGC(){
        return gc;
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

