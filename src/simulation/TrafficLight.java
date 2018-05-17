package simulation;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class TrafficLight {

    public final int RED = 1;
    public final int YELLOW = 2;
    public final int GREEN = 3;


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

        trafficlight.setLayoutX(XPos);
        trafficlight.setLayoutY(YPos);

        Rectangle rectangle = new Rectangle(30,90, Color.DARKGRAY);
        rectangle.setFill(Color.DARKGRAY);
        rectangle.setStroke(Color.BLACK);


        Circle red = new Circle(12);
        red.setCenterX(15);
        red.setCenterY(17);
        Circle yellow = new Circle(12);
        yellow.setCenterX(15);
        yellow.setCenterY(43);
        Circle green = new Circle(12);
        green.setCenterX(15);
        green.setCenterY(69);


        red.setStroke(Color.BLACK);
        red.setFill(Color.GRAY);
        yellow.setStroke(Color.BLACK);
        yellow.setFill(Color.GRAY);
        green.setStroke(Color.BLACK);
        green.setFill(Color.GRAY);

        trafficlight.getChildren().add(rectangle);
        trafficlight.getChildren().addAll(red,yellow,green);


    }

    //trafficlight.getchildren.get( 1 - red, 2 - yellow, 3 - green, like activestate indexes)

    public void changeTrafficLightColor(int color){
        if (activeState != color){
            activeState = color;

            for (int i = 0; i < trafficlight.getChildren().size(); i++) {
                if(trafficlight.getChildren().get(i) instanceof Circle){
                    ((Circle) trafficlight.getChildren().get(i)).setFill(Color.GRAY);

                }
            }

            if(color == 1 && trafficlight.getChildren().get(1) instanceof Circle){
                ((Circle) trafficlight.getChildren().get(1)).setFill(Color.RED);
            }
            if (color == 2 &&  trafficlight.getChildren().get(2) instanceof Circle){
                ((Circle) trafficlight.getChildren().get(2)).setFill(Color.YELLOW);
            }
            if(color == 3 &&  trafficlight.getChildren().get(3) instanceof Circle){
                ((Circle) trafficlight.getChildren().get(3)).setFill(Color.GREEN);

            }

        }
    }

    public Group getTrafficlight(){
        return this.trafficlight;
    }

//
//    Color toJFXColor(int color) throws IllegalArgumentException {
//        switch (color){
//            case RED:
//                return Color.RED;
//            case YELLOW:
//                return Color.YELLOW;
//            case GREEN:
//                return Color.GREEN;
//            default:
//                throw new IllegalArgumentException("Color value not recognized. " + color );
//        }
//    }

}
