package simulation;

import backend.Car;
import backend.Model;
import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.ArrayList;

public class AnimationParts {
//    DoubleProperty carVelocity = new SimpleDoubleProperty();
    double catVelocity;
    final LongProperty lastUpdateTime = new SimpleLongProperty();
    ArrayList<Integer> IntPath;
    Circle agent;
    ImageView imgView;
    simulationPath simPath;
    AnimationTimer animationTimer;

    int pathIterator;


    int previousPosition, temp;

    public AnimationParts(ArrayList<Integer> IntPath, Graph graph, Board board) {

        this.IntPath = IntPath;
        this.agent = new Circle(25);
        agent.setFill(Color.RED);

        Car car = new Car(graph.getNodeByIndex(0), graph.getNodeByIndex(8), graph);
        Model model = new Model();


        catVelocity = car.getDesVel();

        pathIterator = 1;

        javafx.scene.image.Image img = new javafx.scene.image.Image("car.PNG", 25, 25, true, false);
        imgView = new ImageView(img);


        //if a path has 2 points minimum
        if (IntPath.size() >= 2) {

            for (int i = 0; i < IntPath.size(); i++) {

                //set the start of the car movement animation
                if (i == 0) {
                    MoveTo moveTo = new MoveTo(graph.getNodeByIndex(IntPath.get(i)).x * board.SIM_SIZE + board.SIM_SIZE / 2,
                            graph.getNodeByIndex(IntPath.get(i)).y * board.SIM_SIZE + board.SIM_SIZE / 2);
                    previousPosition = 10;

                    simPath = new simulationPath(graph.getNodeByIndex(IntPath.get(i)).x * board.SIM_SIZE + board.SIM_SIZE / 2,
                            graph.getNodeByIndex(IntPath.get(i)).y * board.SIM_SIZE + board.SIM_SIZE / 2);

                } else {

                    int dir = checkDirection( graph.getNodeByIndex(IntPath.get(i)).x, graph.getNodeByIndex(IntPath.get(i)).y,
                            graph.getNodeByIndex(IntPath.get(i - 1)).x, graph.getNodeByIndex(IntPath.get(i - 1)).y);


                    LineTo lineTo = new LineTo(graph.getNodeByIndex(IntPath.get(i)).x * board.SIM_SIZE + board.SIM_SIZE / 2,
                            graph.getNodeByIndex(IntPath.get(i)).y * board.SIM_SIZE + board.SIM_SIZE / 2);

                    simPath.addtoPath(graph.getNodeByIndex(IntPath.get(i)).x * board.SIM_SIZE + board.SIM_SIZE / 2,
                            graph.getNodeByIndex(IntPath.get(i)).y * board.SIM_SIZE + board.SIM_SIZE / 2, dir);

                }

            }
        }

        imgView.setTranslateX(simPath.startX);
        imgView.setTranslateY(simPath.startY);


        animationTimer = new AnimationTimer() {


            @Override
            public void handle(long now) {


                if (lastUpdateTime.get() > 0) {

                    int xCoord = simPath.path.get(pathIterator).get(0);
                    int yCoord = simPath.path.get(pathIterator).get(1);

                    final double elapsedSeconds = (now - lastUpdateTime.get()) / 1_000_000_000.0;




                    final double deltaX = elapsedSeconds * car.getVel();

                    final double oldX = imgView.getTranslateX();
                    final double oldY = imgView.getTranslateY();

                    final double newX = oldX + deltaX;
                    final double newY = oldY + deltaX;

                    if (newX < xCoord || newY < yCoord) {

                        if (newX < xCoord){
                            imgView.setTranslateX(newX);
                        }

                        else if(newY < yCoord) {
                            imgView.setTranslateY(newY);
                        }

                        System.out.println(imgView.getTranslateX() + "  " + imgView.getTranslateY());


                    }


                    else {

                        if(pathIterator < simPath.path.size()-1){

                            imgView.setTranslateX(Math.round(newX));
                            imgView.setTranslateY(Math.round(newY));


                            int oldDir = simPath.directions.get(pathIterator-1);
                            pathIterator++;
                            int newDir = simPath.directions.get(pathIterator-1);
//
                            if(oldDir == 6){
                                if(newDir == 8){
                                    imgView.setRotate(270);
                                }
                                if(newDir == 2){
                                    imgView.setRotate(90);
                                }
                            }
                            if(oldDir == 4){
                                if(newDir == 8){
                                    imgView.setRotate(270);
                                }
                                if(newDir == 2){
                                    imgView.setRotate(90);
                                }
                            }

                            if(oldDir == 2){
                                if(newDir == 4){
                                    imgView.setRotate(180);
                                }
                                if(newDir == 6){
                                    imgView.setRotate(0);
                                }
                            }
                            if(oldDir == 8){
                                if(newDir == 4){
                                    imgView.setRotate(180);
                                }
                                if(newDir == 6){
                                    imgView.setRotate(0);
                                }
                            }


                            System.out.println(simPath.directions);

                        }
                        else{
                            stop();
                        }

                    }



                    double dist = Math.sqrt(Math.pow((imgView.getTranslateX() - simPath.getX(pathIterator)), 2) + (Math.pow(imgView.getTranslateY() - simPath.getY(pathIterator), 2)));
                    car.setVel((car.getVel() + (model.acceleration(car, dist, 15)* 0.016)));
                    System.out.println(dist + " " + car.getVel());

                }

                lastUpdateTime.set(now);

            }

        };


//        PathTransition transition = new PathTransition();
//
//        transition.setDuration(Duration.millis(10000));
//        transition.setNode(this.imgView);
//        transition.setPath(this.path);
//
//        transition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
//
//        transition.setCycleCount(1);
//
//        //Setting auto reverse value to true
//        transition.setAutoReverse(false);
//
//        //Playing the animation
//        transition.play();


    }


    private int checkDirection(int oldX, int oldY, int newX, int newY) {

        //north
        if (oldX == newX && oldY > newY) {
            return 2;
        }
        //south
        if (oldX == newX && oldY < newY) {
            return 8;
        }
        //east
        if (oldX < newX && oldY == newY) {
            //turn east
            return 4;
        }
        //west
        if (oldX > newX && oldY == newY) {
            return 6;
        }

        return 0;

    }

    public Circle getAnimatedCircle() {
        return this.agent;
    }

    public ImageView getAnimatedCar() {
        return this.imgView;
    }
}
