package simulation;

import backend.Car;
import backend.CollisionDetection;
import backend.Model;
import javafx.animation.AnimationTimer;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

//separate animation class for each car.

public class carAnimation {


    final LongProperty lastUpdateTime = new SimpleLongProperty();
    double carVelocity;
    ArrayList<Integer> IntPath;
    ImageView imgView;
    simulationPath simPath;
    AnimationTimer animationTimer;

    Car car;

    int pathIterator;
    int previousPosition;


    public carAnimation(Graph graph, Board board, Model model, Car car, CollisionDetection collisionDetection) {


        this.car = car;


        this.IntPath = car.getPath();


        carVelocity = car.getDesVel();

        pathIterator = 1;

        javafx.scene.image.Image img = new javafx.scene.image.Image("car.PNG", 25, 13, true, false);
        imgView = new ImageView(img);

        //if a path has 2 points minimum
        if (IntPath.size() >= 2) {

            for (int i = 0; i < IntPath.size(); i++) {

                //set the start of the car movement animation
                if (i == 0) {

                    previousPosition = 10;

                    simPath = new simulationPath(graph.getNodeByIndex(IntPath.get(i)).x * board.SIM_SIZE + board.SIM_SIZE / 2,
                            graph.getNodeByIndex(IntPath.get(i)).y * board.SIM_SIZE + board.SIM_SIZE / 2);

                } else {

                    int dir = checkDirection(graph.getNodeByIndex(IntPath.get(i)).x, graph.getNodeByIndex(IntPath.get(i)).y,
                            graph.getNodeByIndex(IntPath.get(i - 1)).x, graph.getNodeByIndex(IntPath.get(i - 1)).y);


                    simPath.addtoPath(graph.getNodeByIndex(IntPath.get(i)).x * board.SIM_SIZE + board.SIM_SIZE / 2,
                            graph.getNodeByIndex(IntPath.get(i)).y * board.SIM_SIZE + board.SIM_SIZE / 2, dir);

                }

            }
        }

        car.setLocX(simPath.startX - 10);
        car.setLocY(simPath.startY);

        imgView.setTranslateX(simPath.startX - 10);
        imgView.setTranslateY(simPath.startY);

        //prerotate
        if (simPath.directions.get(0) == 8) {
            imgView.setRotate(270);
        }

        if (simPath.directions.get(0) == 4) {
            imgView.setRotate(180);
        }

        if (simPath.directions.get(0) == 2) {
            imgView.setRotate(90);
        }

        System.out.println("CAR POSITION BEFORE ANIMATION START    X: " + car.getLocX() + "  Y:" + car.getLocY() );

        animationTimer = new AnimationTimer() {


            @Override
            public void handle(long now) {

                if (lastUpdateTime.get() > 0) {


                    int xCoord = simPath.path.get(pathIterator).get(0) - 10;
                    int yCoord = simPath.path.get(pathIterator).get(1);

                    final double elapsedSeconds = (now - lastUpdateTime.get()) / 1_000_000_000.0;

                    final double delta = elapsedSeconds * car.getVel();


                    final double oldX = imgView.getTranslateX();
                    final double oldY = imgView.getTranslateY();
                    double newX = oldX + delta;
                    double newY = oldY + delta;

                    if (simPath.directions.get(pathIterator - 1) == 4) {
                        newX = oldX - delta;
                    }

                    if (simPath.directions.get(pathIterator - 1) == 8) {
                        newY = oldY - delta;
                    }


                    if (simPath.directions.get(pathIterator - 1) == 6 && newX < xCoord) {
                        imgView.setTranslateX(newX);
                        car.setLocX(newX);

                    } else if (simPath.directions.get(pathIterator - 1) == 4 && newX > xCoord) {
                        imgView.setTranslateX(newX);
                        car.setLocX(newX);

                    } else if (simPath.directions.get(pathIterator - 1) == 2 && newY < yCoord) {
                        imgView.setTranslateY(newY);
                        car.setLocY(newY);

                    } else if (simPath.directions.get(pathIterator - 1) == 8 && newY > yCoord) {
                        imgView.setTranslateY(newY);
                        car.setLocY(newY);

                    } else {

                        if (pathIterator < simPath.path.size() - 1) {

                            imgView.setTranslateX(Math.round(newX));
                            imgView.setTranslateY(Math.round(newY));


                            int oldDir = simPath.directions.get(pathIterator - 1);
                            pathIterator++;
                            int newDir = simPath.directions.get(pathIterator - 1);

                            if (oldDir == 6) {
                                if (newDir == 8) {
                                    imgView.setRotate(270);
                                }
                                if (newDir == 2) {
                                    imgView.setRotate(90);
                                }
                            }
                            if (oldDir == 4) {
                                if (newDir == 8) {
                                    imgView.setRotate(270);
                                }
                                if (newDir == 2) {
                                    imgView.setRotate(90);
                                }
                            }

                            if (oldDir == 2) {
                                if (newDir == 4) {
                                    imgView.setRotate(180);
                                }
                                if (newDir == 6) {
                                    imgView.setRotate(0);
                                }
                            }
                            if (oldDir == 8) {
                                if (newDir == 4) {
                                    imgView.setRotate(180);
                                }
                                if (newDir == 6) {
                                    imgView.setRotate(0);
                                }
                            }


                            System.out.println(simPath.directions);

                        } else {
                            stop();
                        }

                    }


                    double dist = Math.sqrt(Math.pow((imgView.getTranslateX() - simPath.getX(pathIterator)), 2) + (Math.pow(imgView.getTranslateY() - simPath.getY(pathIterator), 2)));
                    car.setVel((car.getVel() + (model.acceleration(car, dist, 15) * 0.016)));

                    //try to detect a collision here
                    if (collisionDetection.collisionDetection()) {

                        stopCarAnimation();
                        System.out.println("COLLISION");
                    }

                }

                lastUpdateTime.set(now);

            }

        };


    }

    public void stopCarAnimation() {
        System.out.println("CAR ANIMATION STOPPED!");
        car.setVel(0);
        animationTimer.stop();
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

    public ImageView getAnimatedCar() {
        return this.imgView;
    }

    public Car getBackendCar() {
        return this.car;
    }

}