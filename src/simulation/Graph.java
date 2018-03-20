package simulation;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    public List<Node> nodes;
    public List<Edge> edges;

    ArrayList<Line> lines;

    //Constructor with no input parameters.
    public Graph(){
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();

        lines = new ArrayList<>();

    }

    public void addNode(Node node){
        this.nodes.add(node);
    }

    public void removeNode(Node node){
        this.nodes.remove(node);
    }

//    public void addEdge(Node start, Node end,int incominglanes, int outcominglanes, double weight){
//        start.connections.add(new Edge(start,end, incominglanes, outcominglanes, weight));
//
//    }

    public void addEdge(Node start, Node end){
        edges.add(new Edge(start,end));
    }

    public void removeEdge(Edge edge){
        edges.remove(edge);
    }

    public void showGraph(GraphicsContext gc){

        //Show the Vertexes in GUI
        for (int i = 0; i < this.nodes.size(); i++) {

            gc.fillOval(this.nodes.get(i).Xpos, this.nodes.get(i).Ypos, 30, 30);
            gc.setFill(Color.WHITE);
            gc.fillText(this.nodes.get(i).name, this.nodes.get(i).Xpos + 7, this.nodes.get(i).Ypos + 15);
            gc.setFill(Color.BLACK);

        }

        //Show the edges between vertexes in GUI
        for (int i = 0; i < this.edges.size(); i++) {
            gc.strokeLine(this.edges.get(i).start.Xpos + 15, this.edges.get(i).start.Ypos + 15,
                    this.edges.get(i).end.Xpos + 15, this.edges.get(i).end.Ypos + 15);

        }
    }

    public void printAdjecency(){
        for (int i = 0; i < this.edges.size(); i++) {
            System.out.println("node " + this.edges.get(i).start.name + " is connected to " + this.edges.get(i).end.name);
        }
    }

    public Node convertCircleToNode(Circle circle){
        Node node = new Node(circle.getCenterX(),circle.getCenterY());

        return node;
    }

    public void addLineStart(Circle start) {

        for (int i = 0; i < nodes.size(); i++) {
            if(start.getCenterX() == nodes.get(i).Xpos &&
                    start.getCenterY() == nodes.get(i).Ypos){
                Line line = new Line();

                line.setStartX(start.getCenterX());
                line.setStartY(start.getCenterY());

                lines.add(line);
            }
        }

    }
    public void addLineEnd(Circle end){
        for (int i = 0; i < nodes.size() ; i++) {
            if(end.getCenterX() == nodes.get(i).Xpos && end.getCenterY() == nodes.get(i).Ypos) {
                lines.get(lines.size()-1).setEndX(end.getCenterX());
                lines.get(lines.size()-1).setEndY(end.getCenterY());
            }
        }
    }

    public Node getNodeAtCoord(double x, double y){
        for (int i = 0; i < this.nodes.size()-1; i++) {
            if(this.nodes.get(i).Xpos == x && this.nodes.get(i).Ypos == y){
                return this.nodes.get(i);
            }
        }

        return null;
    }


}
