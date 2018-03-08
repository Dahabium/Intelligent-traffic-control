package simulation;

import javafx.application.Application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    static GraphicsContext gc = null;

    public static void main(String[] args) {

        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("New program");
        primaryStage.setHeight(500);
        primaryStage.setWidth(500);
        Group root = new Group();
        Scene rootScene = new Scene(root);

        primaryStage.setScene(rootScene);

        Canvas canvas = new Canvas(500,500);
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();

        final long startNanoTime = System.nanoTime();


        //=====================
        List<simulation.Node> nodeList = new ArrayList<>(4);

//        for (int i = 0; i < nodeList.size(); i++) {
//            nodeList.add(new simulation.Node("n" + i));
//        }
        simulation.Node n1 = new simulation.Node("n1",50,50);
        simulation.Node n2 = new simulation.Node("n2",120, 50);
        simulation.Node n3 = new simulation.Node("n3",50, 120 );
        simulation.Node n4 = new simulation.Node("n4",120,120);

        nodeList.add(n1);
        nodeList.add(n2);
        nodeList.add(n3);
        nodeList.add(n4);

        Graph grp = new Graph(nodeList);

        grp.addEdge(n1,n2,1);
        grp.addEdge(n1,n3,1);
        grp.addEdge(n2,n1,1);
        grp.addEdge(n2,n3,1);
        grp.addEdge(n2,n4,1);
        grp.addEdge(n3,n1,1);
        grp.addEdge(n3,n2,1);
        grp.addEdge(n3,n4,1);
        grp.addEdge(n4,n2,1);
        grp.addEdge(n4,n3,1);
        grp.addEdge(n4,n1,1);


        for (int i = 0; i < grp.nodes.get(0).connections.size(); i++) {
            System.out.println("node " + grp.nodes.get(0).name  + " is connected to " + grp.nodes.get(0).connections.get(i).end.name);
        }

        //============

        int trackX = 0;
        int trackY = 0;

        //Show the intersections in GUI
        for (int i = 0; i < grp.nodes.size(); i++){
            gc.fillOval(grp.nodes.get(i).Xpos, grp.nodes.get(i).Ypos,30,30);
        }

        //Show the roads between intersections
        for (int i = 0; i < grp.nodes.size(); i++) {
            //look at edges outcoming from each node
            for (int j = 0; j < grp.nodes.get(i).connections.size(); j++) {

                gc.strokeLine(grp.nodes.get(i).connections.get(j).start.Xpos +15, grp.nodes.get(i).connections.get(j).start.Ypos +15,
                        grp.nodes.get(i).connections.get(j).end.Xpos+15, grp.nodes.get(i).connections.get(j).end.Ypos+15);

            }
        }


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

