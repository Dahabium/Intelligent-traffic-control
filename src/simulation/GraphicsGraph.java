package simulation;


import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

public class GraphicsGraph {

    ArrayList<Circle> circles;
    ArrayList<Line> lines;


    public GraphicsGraph(){
        circles = new ArrayList<>();
        lines = new ArrayList<>();

    }
    public void addVertex(double x, double y){
        Circle vertex = new Circle(x, y, 12);
        vertex.setFill(Color.BLUE);
        vertex.setStroke(Color.BLACK);

        circles.add(vertex);
    }
    public void addLineStart(Circle start){
        for (int i = 0; i < circles.size(); i++) {
            if(start.getCenterX() == circles.get(i).getCenterX() && start.getCenterY() == circles.get(i).getCenterY()){
                Line line = new Line();
                line.setStartX(start.getCenterX());
                line.setStartY(start.getCenterY());

                lines.add(line);
            }
        }
    }

    public void addLineEnd(Circle end){
        for (int i = 0; i < circles.size() ; i++) {
            if(end.getCenterX() == circles.get(i).getCenterX() && end.getCenterY() == circles.get(i).getCenterY()) {
                lines.get(lines.size()-1).setEndX(end.getCenterX());
                lines.get(lines.size()-1).setEndY(end.getCenterY());
            }
        }
    }

    public void removeVertex(Circle vert){
        for (int i = 0; i < circles.size(); i++) {
            if(vert.getCenterX() == circles.get(i).getCenterX() && vert.getCenterY() == circles.get(i).getCenterY()){
                circles.remove(i);
            }
        }
    }

    public Circle getLastVertex(){
        return circles.get(circles.size()-1);
    }
    public void getLength(){
        System.out.println(circles.size());
    }

}
