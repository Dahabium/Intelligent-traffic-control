package simulation;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class Node {

    public String name;
    public double Xpos,Ypos;
    public int index;
    public int type = 0;
    public int x, y;



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
    public Node(int index, double Xpos, double Ypos, int x, int y){
        this.Xpos = Xpos;
        this.Ypos = Ypos;
		this.x = x;
		this.y = y;
        this.index = index;
    }
	public Node(int index, double Xpos, double Ypos, int x, int y, int type){
		this.Xpos = Xpos;
		this.Ypos = Ypos;
		this.x = x;
		this.y = y;
		this.index = index;
		this.type = type;
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
