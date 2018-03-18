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




    //to fix
    public boolean checkNodesAround(double x, double y){
        System.out.println("diff " + x + "  " + y);
        if(circles.size() > 0){
            for (int i = 0; i < circles.size(); i++) {
                if(x >= circles.get(i).getCenterX() + 30 || y <= circles.get(i).getCenterX() - 30 ||
                        x >= circles.get(i).getCenterY() + 30 || y <= circles.get(i).getCenterY() - 30){
                    return true;
                }
            }
        }
        if(circles.size() == 0){
            return true;
        }
        else return false;
    }


}
