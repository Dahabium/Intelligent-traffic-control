package simulation;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class Node {

    public String name;
    public List<Edge> connections;
    public double Xpos,Ypos;
//    public Circle circle = new Circle(50.0f, Color.RED);


    public Node(String name, int Xpos, int Ypos){
        this.name = name;
        this.connections = new ArrayList<>();
        this.Xpos = Xpos;
        this.Ypos = Ypos;

    }

    public Node(double Xpos, double Ypos){
        this.connections = new ArrayList<>();
        this.Xpos = Xpos;
        this.Ypos = Ypos;
    }





}
