package simulation;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class Node {

    public String name;
    public List<Edge> connections;
    public Circle circle = new Circle(50.0f, Color.RED);
    public int Xpos,Ypos;


    public Node(String name, int Xpos, int Ypos){
        this.name = name;
        this.connections = new ArrayList<>();
        this.Xpos = Xpos;
        this.Ypos = Ypos;

    }




}
