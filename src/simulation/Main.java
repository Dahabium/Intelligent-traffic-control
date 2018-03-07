package simulation;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static simulation.Graph.addEdge;
import static simulation.Graph.printGraph;


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


        // create the graph
        Graph graph = new Graph(5);
        addEdge(graph, 0, 1);
        addEdge(graph, 0, 4);
        addEdge(graph, 1, 2);
        addEdge(graph, 1, 3);
        addEdge(graph, 1, 4);
        addEdge(graph, 2, 3);
        addEdge(graph, 3, 4);

        // print the adjacency list representation of graph
        printGraph(graph);

        //=====================
        List<simulation.Node> nodeList = new ArrayList<>();

        simulation.Node n1 = new simulation.Node("n1");
        simulation.Node n2 = new simulation.Node("n2");
        simulation.Node n3 = new simulation.Node("n3");
        simulation.Node n4 = new simulation.Node("n4");

        nodeList.add(n1);
        nodeList.add(n2);
        nodeList.add(n3);
        nodeList.add(n4);

        Graphv2 grp = new Graphv2(nodeList);

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

        for (int i = 0; i < grp.nodes.get(0).connections.size(); i++) {
            System.out.println("node " + grp.nodes.get(0).name  + " is connected to " + grp.nodes.get(0).connections.get(i).end.name);
        }

        //============

        int trackX = 0;
        int trackY = 0;

        for (int i = 0; i < grp.nodes.size(); i++){

            gc.fillOval(trackX * 50, trackY * 50, 30,30);
            trackX++;
            trackY++;

        }


        primaryStage.show();
    }


    public GraphicsContext getGC(){
        return gc;
    }

}




/*
place in start to use graphstrem

        SwingNode content = new SwingNode();

        Graph graph = new SingleGraph("Tutorial 1");
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CA", "C", "A");
        graph.display();



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

