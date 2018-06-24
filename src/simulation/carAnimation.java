package simulation;

import backend.Car;
import backend.CollisionDetection;
import backend.Model;
import javafx.animation.*;
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
    PathConstructor pathConstructor;
    Car car;

    int pathIterator;
    int previousPosition;
    int dir;


    public carAnimation(Graph graph, Board board, Model model, Car car, CollisionDetection collisionDetection) {


        this.car = car;
        this.IntPath = car.getPath();
        carVelocity = car.getDesVel();

        pathIterator = 1;

//        System.out.println("INTPATH " + IntPath + " PATHITERATOR " + pathIterator);

        //set the initial road for the car
        //TODO use roads instead of edges
        car.setLocEdge(graph.getEdge(graph.getNodeByIndex(IntPath.get(pathIterator - 1)), graph.getNodeByIndex(IntPath.get(pathIterator))));

        int carsizeWidth = 25;
        int carsizeHight = 13;

        javafx.scene.image.Image img = new javafx.scene.image.Image("car.PNG", carsizeWidth, carsizeHight, true, false);
        imgView = new ImageView(img);


        this.pathConstructor = new PathConstructor(IntPath, graph, board.SIM_SIZE);
        simPath = pathConstructor.constructPath();

        car.setLocX(simPath.startX + carsizeWidth / 2);
        car.setLocY(simPath.startY + carsizeHight / 2);

        imgView.setTranslateX(simPath.startX);
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

        if (simPath.directions.get(0) == 6) {
            imgView.setRotate(360);
        }

        animationTimer = new AnimationTimer() {


            @Override
            public void handle(long now) {

                // insert in the if statement to make the care move only on green : && car.getLocRoad().getTrafficLight().getCurrentstate() == 3
                if (lastUpdateTime.get() > 0) {


                    int xCoord = simPath.path.get(pathIterator).get(0);
                    int yCoord = simPath.path.get(pathIterator).get(1);

                    final double elapsedSeconds = (now - lastUpdateTime.get()) / 1_000_000_000.0;

                    final double delta = elapsedSeconds * car.getVel() *2;

                    final double oldX = imgView.getTranslateX() ;
                    final double oldY = imgView.getTranslateY() ;
                    double newX = oldX + delta;
                    double newY = oldY + delta;

                    double dist = 100000;
                    double carFrontVelocity = 0;

                    if (simPath.directions.get(pathIterator - 1) == 4) {
                        newX = oldX - delta;
                    }

                    if (simPath.directions.get(pathIterator - 1) == 8) {
                        newY = oldY - delta;
                    }


                    if (simPath.directions.get(pathIterator - 1) == 6 && newX < xCoord) {
                        imgView.setTranslateX(newX);
                        car.setLocX(newX + carsizeWidth/2);

                    } else if (simPath.directions.get(pathIterator - 1) == 4 && newX > xCoord) {
                        imgView.setTranslateX(newX);
                        car.setLocX(newX + carsizeWidth/2);

                    } else if (simPath.directions.get(pathIterator - 1) == 2 && newY < yCoord) {
                        imgView.setTranslateY(newY);
                        car.setLocY(newY + carsizeHight/2);

                    } else if (simPath.directions.get(pathIterator - 1) == 8 && newY > yCoord) {
                        imgView.setTranslateY(newY);
                        car.setLocY(newY + carsizeHight/2);

                    } else {

                        if (pathIterator < simPath.path.size() - 1) {

                            imgView.setTranslateX(Math.round(xCoord));
                            imgView.setTranslateY(Math.round(yCoord));


                            int oldDir = simPath.directions.get(pathIterator - 1);

                            pathIterator++;

                            int newDir = simPath.directions.get(pathIterator - 1);

                            car.setLocEdge(graph.getEdge(graph.getNodeByIndex(IntPath.get(pathIterator - 1)),
                                    graph.getNodeByIndex(IntPath.get(pathIterator))));


                            if (oldDir == 6) {
                                if (newDir == 8) {
//                                    final Rotate rotationTransform = new Rotate(0, imgView.getX(), imgView.getY());
//                                    imgView.getTransforms().add(rotationTransform);
//
//                                    final Timeline rotationAnimation = new Timeline();
//                                    rotationAnimation.getKeyFrames()
//                                            .add(
//                                                    new KeyFrame(
//                                                            Duration.seconds(0.5),
//                                                            new KeyValue(
//                                                                    rotationTransform.angleProperty(),
//                                                                    -80
//                                                            )
//                                                    )
//                                            );
//                                    rotationAnimation.setCycleCount(1);
//                                    rotationAnimation.play();


                                    imgView.setRotate(270);
//                                    while(currentRotation != 270){
//                                        imgView.setRotate(currentRotation - 10);
//
//                                    }
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
                            car.stopTime = System.currentTimeMillis();
                            System.out.println("Time taken for car to reach destination " + car.getElapsedTimeTotal());
                            System.out.println("delete car! ");
                            car.destinationReached = true;
                        }

                    }


                    //if there is a car in the front, on current road
                    if (collisionDetection.returnCarInFront(car) != null) {
                        //calculate the distance between current car and the car in the front .

                        dist = (Math.sqrt(Math.pow((car.getLocX() - collisionDetection.returnCarInFront(car).getLocX()), 2) + (Math.pow(car.getLocY() - collisionDetection.returnCarInFront(car).getLocY(), 2)))) - 25 - 25;

                        carFrontVelocity = collisionDetection.returnCarInFront(car).getVel();
                        //round a small number
                        if (carFrontVelocity < 0.1) {
                            carFrontVelocity = 0;
                        }

                    }
                    else {
                        //else check the distance in the front node (...)
                        if (car.getPercentageOnCurrentRoad() > 30 && collisionDetection.returnCarInFront(car) == null) {

                            if (car.getLocRoad().existsTrafficLight() == false) {
                                //continue going full speed
                            }
                            else if (car.getLocRoad().getTrafficLight().getCurrentstate() != 3) {

                                if(car.getCurentDirection() == 4 && simPath.directions.get(pathIterator) == 2){
                                    dist = Math.sqrt(Math.pow((car.getLocX() - simPath.getX(pathIterator)), 2) + (Math.pow(car.getLocY() - simPath.getY(pathIterator), 2))) - 65;
                                    carFrontVelocity = 0;

                                }
                                if(car.getCurentDirection() == 8 && simPath.directions.get(pathIterator) == 4){
                                    dist = Math.sqrt(Math.pow((car.getLocX() - simPath.getX(pathIterator)), 2) + (Math.pow(car.getLocY() - simPath.getY(pathIterator), 2))) - 60;
                                    carFrontVelocity = 0;
                                }

                                else {
                                    dist = Math.sqrt(Math.pow((car.getLocX() - simPath.getX(pathIterator)), 2) + (Math.pow(car.getLocY() - simPath.getY(pathIterator), 2))) - 40;
                                    carFrontVelocity = 0;

                                }

                                if (car.getVel() < 1 && car.getVel() >= 0 && car.timeAtIntersectionStart == 0) {
                                    car.setVel(car.getVel()-0.1);
                                    car.timeAtIntersectionStart = System.currentTimeMillis();
                                }

                            }
                            else if (car.getLocRoad().getTrafficLight().getCurrentstate() == 3 && car.timeAtIntersectionEnd != 0) {
                                car.timeAtIntersectionEnd = System.currentTimeMillis();
                                car.addTimeToIntersection();

                                System.out.println("Car stood at intersection " + car.totalTimeAtIntersections);

                            }

                        }


                        //decelerate before doing a turn untill "10 meters per second" .
                        if (car.getPercentageOnCurrentRoad() > 60 && (pathIterator < simPath.path.size() - 1) &&
                                nextActionIsTurn(simPath.directions.get(pathIterator - 1), simPath.directions.get(pathIterator))) {

                            if (car.getVel() > 10) {

                                car.setVel(car.getVel() - 0.1);

                            }
                        }

                        //rough draft of side collision detection.... its carried by the collisionDetection class without IDM.
                        //just by increasing the margins of normal collisiondetection.
                        if (collisionDetection.frontCarCollisionDetection(car)) {

                            if (car.getVel() > 0) {

                                car.setVel(car.getVel() - 0.2);
                            }
                        }
//                        System.out.println("X " + car.getLocX() + "  Y: "+ car.getLocY());

                        //stop the car properly before the goal
                        if (pathIterator == simPath.path.size() - 1 && car.getPercentageOnCurrentRoad() > 70) {
                            dist = Math.sqrt(Math.pow((imgView.getTranslateX() - simPath.getX(pathIterator)), 2) + (Math.pow(imgView.getTranslateY() - simPath.getY(pathIterator), 2)));

                            if (dist < 0.1 && car.destinationReached == false) {
                                car.stopTime = System.currentTimeMillis();
                                car.destinationReached = true;
                            }
                        }
                    }


                    //"Dist " + dist + "  CarFrontVelocity " + "  local edge : " + car.getLocEdge() + "   " +  carFrontVelocity +

                    car.setVel((car.getVel() + (model.acceleration(car, dist, carFrontVelocity) * 0.016)));

                    //try to detect a collision here
                    if (collisionDetection.collisionDetection(car)) {

                        stopCarAnimation();

                        System.out.println("COLLISION");
                    }
                }


                lastUpdateTime.set(now);

            }

        };


    }


    public boolean nextActionIsTurn(int oldDir, int newDir) {

        if (oldDir == 6) {
            if (newDir == 8) {
                return true;
            }
            if (newDir == 2) {
                return true;
            }
        }
        if (oldDir == 4) {
            if (newDir == 8) {
                return true;
            }
            if (newDir == 2) {
                return true;
            }
        }

        if (oldDir == 2) {
            if (newDir == 4) {
                return true;
            }
            if (newDir == 6) {
                return true;
            }
        }
        if (oldDir == 8) {
            if (newDir == 4) {
                return true;
            }
            if (newDir == 6) {
                return true;
            }
        }
        return false;

    }


    //TODO Fix this. (when hitting a stop button this doesent stop the car
    public void stopCarAnimation() {

        System.out.println("CAR ANIMATION STOPPED!");
        car.setVel(0);
        animationTimer.stop();

    }

    private int checkDirection(int oldX, int oldY, int newX, int newY) {

        //north
        if (oldX == newX && oldY < newY) {
            System.out.println("Direction is: " + 2);
            return 2;
        }
        //south
        if (oldX == newX && oldY > newY) {
            System.out.println("Direction is: " + 8);
            return 8;
        }
        //east
        if (oldX > newX && oldY == newY) {
            //turn east
            System.out.println("Direction is: " + 4);
            return 4;
        }
        //west
        if (oldX < newX && oldY == newY) {
            System.out.println("Direction is: " + 6);
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
