package simulation;

import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    static GraphicsContext gc = null;
    Scene rootScene, scene2, drawScene;

    public static void main(String[] args) {

        launch(args);
    }
    //branchee
    //new
    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("New program");
        primaryStage.setHeight(500);
        primaryStage.setWidth(500);

        Group root = new Group();
        Group subGroup = new Group();

        rootScene = new Scene(root);

        primaryStage.setScene(rootScene);


        Canvas canvas = new Canvas(500,500);
        Canvas canvas2 = new Canvas(500,500);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext gc2 = canvas2.getGraphicsContext2D();


        Button button1= new Button("Go to scene 2");
        button1.setOnAction(e -> primaryStage.setScene(scene2));

        Button drawGraphbtn =  new Button("Generate own graph");
        drawGraphbtn.setOnAction(e-> primaryStage.setScene(drawScene));

        Button button3 = new Button("Go to main menu");
        button3.setOnAction(e -> primaryStage.setScene(rootScene));


        VBox box = new VBox(20);

        VBox pane2 = new VBox();
        pane2.getChildren().add(button3);

        boolean drawNodes = true;

        double x[] = new double[10];
        double y[] = new double[10];

        int count =0;

        pane2.getChildren().add(canvas2);

        drawScene = new Scene(subGroup,300,250);

        subGroup.getChildren().add(pane2);

        drawScene.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {


            @Override
            public void handle(MouseEvent mouseEvent) {

                System.out.println(mouseEvent.getX() + "  " + mouseEvent.getY());


                if (!mouseEvent.isAltDown()) {

                    final Circle vertex = new Circle(mouseEvent.getSceneX(), mouseEvent.getSceneY(), 30);
                    vertex.setFill(Color.YELLOW);

                    subGroup.getChildren().add(vertex);
//                drawScene.getChadd(vertex);

                    vertex.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                        @Override
                        public void handle(final MouseEvent arg0) {
                            subGroup.getChildren().remove(vertex);
                        }

                    });

                    if(!mouseEvent.isControlDown()){

                        final Rectangle rect = new Rectangle(40,40);

                        System.out.println("vert " + vertex.getCenterX() + "  " +vertex.getCenterY());

                    }
                }

            }
        });



        box.getChildren().addAll(button1,drawGraphbtn);

        root.getChildren().add(box);

        Label label2= new Label("Go to graph");
        Button button2= new Button("Go to main menu");
        button2.setOnAction(e -> primaryStage.setScene(rootScene));

        VBox layout2= new VBox(20);
        layout2.getChildren().addAll(label2, button2);
        scene2 = new Scene(layout2,300,250);

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



        primaryStage.setScene(rootScene);
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

