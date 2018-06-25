package simulation;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class TrafficLight {

    public final int RED = 1;
    public final int YELLOW = 2;
    public final int GREEN = 3;

    SpecialCircle red,yellow,green;

    //1 - red, 2 - yellow, 3- green

    private int activeState;

    private int XPos, YPos;

    private Group trafficlight;

    public TrafficLight(int XPos, int YPos)
    {
        //red by default
        this.activeState = 0;
        this.XPos = XPos;
        this.YPos = YPos;

        trafficlight = new Group();

        //trafficlight.setLayoutX(XPos);
        //trafficlight.setLayoutY(YPos);
        trafficlight.relocate(XPos, YPos);

        Rectangle rectangle = new Rectangle(30,90, Color.DARKGRAY);
        rectangle.setFill(Color.DARKGRAY);
        rectangle.setStroke(Color.BLACK);


        this.red = new SpecialCircle(RED, 15,17);
        this.yellow = new SpecialCircle(YELLOW,15,43);
        this.green = new SpecialCircle(GREEN,15,69);

        trafficlight.getChildren().add(rectangle);

        trafficlight.getChildren().add(red.circle);
        trafficlight.getChildren().add(yellow.circle);
        trafficlight.getChildren().add(green.circle);

    }

    public void changeTrafficLightColor(int color){

        if (activeState != color){
            activeState = color;

            if(color == 1){
                this.red.circle.setFill(Color.RED);
                this.yellow.circle.setFill(Color.GRAY);
                this.green.circle.setFill(Color.GRAY);

            }
            if (color == 2){

                this.yellow.circle.setFill(Color.YELLOW);
                this.red.circle.setFill(Color.GRAY);
                this.green.circle.setFill(Color.GRAY);
            }
            if(color == 3 ){

                this.green.circle.setFill(Color.GREEN);
                this.red.circle.setFill(Color.GRAY);
                this.yellow.circle.setFill(Color.GRAY);

            }

        }
    }

    public Group getTrafficlight(){
        return this.trafficlight;
    }


}
