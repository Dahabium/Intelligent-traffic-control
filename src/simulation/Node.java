package simulation;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class Node {

    public String name;
    public double Xpos,Ypos;
    public int index;


    public Node(String name, int Xpos, int Ypos){
        this.name = name;
        this.Xpos = Xpos;
        this.Ypos = Ypos;

    }

    public Node(double Xpos, double Ypos){
        this.Xpos = Xpos;
        this.Ypos = Ypos;
    }

    public Node(int index, double Xpos, double Ypos){
        this.Xpos = Xpos;
        this.Ypos = Ypos;
        this.index = index;
    }
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public double getXpos() {
		return Xpos;
	}

	public void setXpos(double xpos) {
		Xpos = xpos;
	}

	public double getYpos() {
		return Ypos;
	}

	public void setYpos(double ypos) {
		Ypos = ypos;
	}
    
    


}
