package simulation;

import backend.TrafficLight;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TrafficLights {
    boolean left, right, up, down;
    Circle tlL, tlU, tlR, tlD;

    public TrafficLights(boolean l, boolean u, boolean r, boolean d)
    {
        this.left = l;
        this.up = u;
        this.right = r;
        this.down = d;
        tlL = new Circle();
        tlU = new Circle();
        tlR = new Circle();
        tlD = new Circle();
    }

    public Circle getTrafficLight(String pos)
    {
        if(pos == "left")
        {


            return tlL;
        }
        if(pos == "right")
        {

            return tlR;
        }
        if(pos == "up")
        {

            return tlU;
        }
        if(pos == "down")
        {

            return tlD;
        }

        return null;
    }
}
