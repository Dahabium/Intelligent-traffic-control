package simulation;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class Node {

    public String name;
    public double Xpos,Ypos;


    public Node(String name, int Xpos, int Ypos){
        this.name = name;
        this.Xpos = Xpos;
        this.Ypos = Ypos;

    }

    public Node(double Xpos, double Ypos){
        this.Xpos = Xpos;
        this.Ypos = Ypos;
    }


}
