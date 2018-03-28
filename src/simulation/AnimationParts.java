package simulation;

import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.util.ArrayList;

public class AnimationParts {
    ArrayList<Integer> IntPath;
    Path path;

    Circle agent;
    ImageView imgView;

    int previousPosition,temp;

    public AnimationParts(ArrayList<Integer> IntPath, Graph graph, Board board) {

        this.IntPath = IntPath;
        this.agent = new Circle(25);
        this.path = new Path();
        agent.setFill(Color.RED);

        javafx.scene.image.Image img = new javafx.scene.image.Image("car.PNG", 25 , 25, true, false);
        imgView = new ImageView(img);


        //if a path has 2 points minimum
        if (IntPath.size() >= 2) {

            for (int i = 0; i < IntPath.size(); i++) {

                //set the start of the car movement animation
                if (i == 0) {
                    MoveTo moveTo = new MoveTo(graph.getNodeByIndex(IntPath.get(i)).x * board.SIM_SIZE + board.SIM_SIZE / 2, graph.getNodeByIndex(IntPath.get(i)).y * board.SIM_SIZE + board.SIM_SIZE / 2);
                    path.getElements().add(moveTo);
                    previousPosition = 10;
                }
                else {

                    checkDirection(imgView, graph.getNodeByIndex(IntPath.get(i)).x, graph.getNodeByIndex(IntPath.get(i)).y,
                            graph.getNodeByIndex(IntPath.get(i-1)).x, graph.getNodeByIndex(IntPath.get(i-1)).y);


                    System.out.println("Direction " + previousPosition);

                    LineTo lineTo = new LineTo(graph.getNodeByIndex(IntPath.get(i)).x * board.SIM_SIZE + board.SIM_SIZE / 2, graph.getNodeByIndex(IntPath.get(i)).y * board.SIM_SIZE + board.SIM_SIZE / 2);
                    path.getElements().add(lineTo);

                }

            }
//            path.getElements().add(new ClosePath());
        }


        PathTransition transition = new PathTransition();
        transition.setDuration(Duration.millis(10000));
        transition.setNode(this.imgView);
        transition.setPath(this.path);

        transition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

        transition.setCycleCount(1);

        //Setting auto reverse value to true
        transition.setAutoReverse(false);

        //Playing the animation
        transition.play();


    }


    private void checkDirection(ImageView imgView, int oldX, int oldY, int newX, int newY) {

        //north
        if(oldX == newX && oldY > newY){
            previousPosition = 2;
        }
        //south
        if(oldX == newX && oldY < newY){
            previousPosition = 8;
        }
        //east
        if(oldX < newX && oldY == newY){
            //turn east
            previousPosition = 4;
        }
        //west
        if(oldX > newX && oldY == newY){
            previousPosition = 6;
        }

    }

    public Circle getAnimatedCircle() {
        return this.agent;
    }
    public ImageView getAnimatedCar() {
        return this.imgView;
    }
}
