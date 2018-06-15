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

        trafficlight.setLayoutX(XPos);
        trafficlight.setLayoutY(YPos);

        Rectangle rectangle = new Rectangle(30,90, Color.DARKGRAY);
        rectangle.setFill(Color.DARKGRAY);
        rectangle.setStroke(Color.BLACK);



        this.red = new SpecialCircle(RED, 15,17);
        this.yellow = new SpecialCircle(YELLOW,15,43);
        this.green = new SpecialCircle(GREEN,15,69);


//        red.setStroke(Color.BLACK);
//        red.setFill(Color.GRAY);
//        yellow.setStroke(Color.BLACK);
//        yellow.setFill(Color.GRAY);
//        green.setStroke(Color.BLACK);
//        green.setFill(Color.GRAY);

        trafficlight.getChildren().add(rectangle);

        trafficlight.getChildren().add(red.circle);
        trafficlight.getChildren().add(yellow.circle);
        trafficlight.getChildren().add(green.circle);

    }

    //trafficlight.getchildren.get( 1 - red, 2 - yellow, 3 - green, like activestate indexes)

//    public void changeTrafficLightColor(int color){
//        if (activeState != color){
//            activeState = color;
//
//            for (int i = 0; i < trafficlight.getChildren().size(); i++) {
//                if(trafficlight.getChildren().get(i) instanceof Circle){
//                    ((Circle) trafficlight.getChildren().get(i)).setFill(Color.GRAY);
//                }
//            }
//
//            if(color == 1 && trafficlight.getChildren().get(1) instanceof Circle){
//                ((Circle) trafficlight.getChildren().get(1)).setFill(Color.RED);
//                ((Circle) trafficlight.getChildren().get(2)).setFill(Color.GRAY);
//                ((Circle) trafficlight.getChildren().get(3)).setFill(Color.GRAY);
//
//            }
//            if (color == 2 &&  trafficlight.getChildren().get(2) instanceof Circle){
//
//                ((Circle) trafficlight.getChildren().get(2)).setFill(Color.YELLOW);
//                ((Circle) trafficlight.getChildren().get(3)).setFill(Color.GRAY);
//                ((Circle) trafficlight.getChildren().get(1)).setFill(Color.GRAY);
//            }
//            if(color == 3 &&  trafficlight.getChildren().get(3) instanceof Circle){
//                ((Circle) trafficlight.getChildren().get(3)).setFill(Color.GREEN);
//                ((Circle) trafficlight.getChildren().get(1)).setFill(Color.GRAY);
//                ((Circle) trafficlight.getChildren().get(2)).setFill(Color.GRAY);
//
//            }
//
//        }
//    }


    public void changeTrafficLightColor(int color){

        if (activeState != color){
            activeState = color;

//            this.red.greyOut();
//            this.yellow.greyOut();
//            this.green.greyOut();


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

    public void Graying(int color){

        if(color == 0){
            ((Circle) trafficlight.getChildren().get(1)).setFill(Color.GRAY);
            ((Circle) trafficlight.getChildren().get(2)).setFill(Color.GRAY);
            ((Circle) trafficlight.getChildren().get(3)).setFill(Color.GRAY);
        }
        if(color == 1 && trafficlight.getChildren().get(1) instanceof Circle){
            ((Circle) trafficlight.getChildren().get(2)).setFill(Color.GRAY);
            ((Circle) trafficlight.getChildren().get(3)).setFill(Color.GRAY);

        }
        if (color == 2 &&  trafficlight.getChildren().get(2) instanceof Circle){

            ((Circle) trafficlight.getChildren().get(3)).setFill(Color.GRAY);
            ((Circle) trafficlight.getChildren().get(1)).setFill(Color.GRAY);
        }
        if(color == 3 &&  trafficlight.getChildren().get(3) instanceof Circle){
            ((Circle) trafficlight.getChildren().get(1)).setFill(Color.GRAY);
            ((Circle) trafficlight.getChildren().get(2)).setFill(Color.GRAY);

        }

    }

    public Group getTrafficlight(){
        return this.trafficlight;
    }


}
