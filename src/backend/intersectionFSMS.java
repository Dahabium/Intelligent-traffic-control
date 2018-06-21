package backend;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import simulation.Node;

import java.util.ArrayList;

public class intersectionFSMS {

    public final int CROSS_SECTION = 1;
    public final int T_SECTION = 2;
    public final int NORTH = 8;
    public final int SOUTH = 2;
    public final int EAST = 6;
    public final int WEST = 4;
    final int MAX_ALLOWED_GREEN = 16000;
    final int MIN_ALLOWED_GREEN = 8000;
    final int MAX_ALLOWED_RED = 18000;
    final int MIN_ALLOWED_RED = 10000;

    final int RED = 1;
    final int YELLOW = 2;
    final int GREEN = 3;
    public Node intersection;
    public int intersectionType;
    Road horizontal1, horizontal2;
    Road vertical1, vertical2;
    Map map;
    ArrayList<Road> unstructured;


    public intersectionFSMS(ArrayList<Road> input) {

        this.unstructured = input;
        this.intersection = input.get(0).end;

        //regular cross-intersection
        if (unstructured.size() == 4) {

            for (int i = 0; i < 4; i++) {

                System.out.println("DIRECTION " + input.get(i).getDirection());

                if (unstructured.get(i).getDirection() == 6) {
                    this.horizontal1 = unstructured.get(i);
                }
                if (unstructured.get(i).getDirection() == 4) {
                    this.horizontal2 = unstructured.get(i);
                }
                if (unstructured.get(i).getDirection() == 2) {
                    this.vertical1 = unstructured.get(i);
                }
                if (unstructured.get(i).getDirection() == 8) {
                    this.vertical2 = unstructured.get(i);
                }
            }
            this.intersectionType = CROSS_SECTION;
        }
        //T-Section
        if (unstructured.size() == 3) {

            for (int i = 0; i < 3; i++) {
                if (unstructured.get(i).getDirection() == 6) {
                    this.horizontal1 = unstructured.get(i);
                }
                if (unstructured.get(i).getDirection() == 4) {
                    this.horizontal2 = unstructured.get(i);
                }
                if (unstructured.get(i).getDirection() == 2) {
                    this.vertical1 = unstructured.get(i);
                }
                if (unstructured.get(i).getDirection() == 8) {
                    this.vertical2 = unstructured.get(i);
                }
            }
            this.intersectionType = T_SECTION;
        }

        setHorizontalRed(12000, true);

    }

    public intersectionFSMS(Node intersection, Map map) {

        this.intersection = intersection;
        this.map = map;
        unstructured = new ArrayList<>();

        connect();

    }

    public void runFSMforLeftTurn(int place, int time) {

        if (intersectionType == CROSS_SECTION) {

            if (place == NORTH) {

                this.horizontal1.getTrafficLight().runYellow();
                this.horizontal2.getTrafficLight().runYellow();
                this.vertical1.getTrafficLight().runYellow();

                this.vertical2.getTrafficLight().runGreen();
            }
            if (place == SOUTH) {

                this.horizontal1.getTrafficLight().runYellow();
                this.horizontal2.getTrafficLight().runRed();
                this.vertical1.getTrafficLight().runGreen();

                this.vertical2.getTrafficLight().runRed();
            }
            if (place == WEST) {

                //if the others sides have currently green - make sure they first turn yellow and then red! when red -> then green
                if (this.horizontal1.getTrafficLight().currentstate == RED && this.horizontal2.getTrafficLight().currentstate == RED) {

                    this.horizontal1.getTrafficLight().runGreenAfterDelay(this.horizontal1.getTrafficLight().yellowTime);
                    this.horizontal2.getTrafficLight().runRed();
                    this.vertical1.getTrafficLight().runYellow();
                    this.vertical2.getTrafficLight().runYellow();

                    runFSM_Horizontal_Red_Delay(4000);

                }

                else if (this.horizontal1.getTrafficLight().currentstate == GREEN && this.horizontal2.getTrafficLight().currentstate == GREEN) {

                    this.horizontal1.getTrafficLight().runGreen();
                    this.horizontal2.getTrafficLight().runYellow();

                    this.vertical1.getTrafficLight().runRed();
                    this.vertical2.getTrafficLight().runRed();

                    runFSM_Horizontal_Red_Delay(4000);
                }



            }

            if (place == EAST) {

                if (this.horizontal1.getTrafficLight().currentstate == RED && this.horizontal2.getTrafficLight().currentstate == RED) {

                    this.horizontal1.getTrafficLight().runRed();
                    this.horizontal2.getTrafficLight().runGreenAfterDelay(this.horizontal1.getTrafficLight().yellowTime);
                    this.vertical1.getTrafficLight().runYellow();
                    this.vertical2.getTrafficLight().runYellow();

                    runFSM_Horizontal_Red_Delay(4000);

                }

//                else if (this.horizontal1.getTrafficLight().currentstate == GREEN && this.horizontal2.getTrafficLight().currentstate == GREEN) {
//
//                    this.horizontal1.getTrafficLight().runYellow();
//                    this.horizontal2.getTrafficLight().runGreen();
//
//                    this.vertical1.getTrafficLight().runRed();
//                    this.vertical2.getTrafficLight().runRed();
//
//                    runFSM_Horizontal_Red_Delay(4000);
//                }
            }
        }

    }

    private void runFSM_Horizontal_Red_Delay(int delay) {

            System.out.println("Gonna run a method in 3 seconds");

            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(delay), ev -> {
                System.out.println("Setting back to default!");


            this.horizontal1.getTrafficLight().stopTimeLines();
            this.horizontal2.getTrafficLight().stopTimeLines();

                setHorizontalRed(12000, true);
                this.runFSM_Horizontal_Red();


            }));

//            timeline.setDelay();
            timeline.setCycleCount(1);
            timeline.play();

    }

    public void runFSM_Horizontal_Red() {

        if (intersectionType == CROSS_SECTION) {
            this.horizontal1.getTrafficLight().runRed();
            this.horizontal2.getTrafficLight().runRed();

            this.vertical1.getTrafficLight().runGreen();
            this.vertical2.getTrafficLight().runGreen();
        }

    }

    public void runFSM_Vertical_Red() {

        if (intersectionType == CROSS_SECTION) {
            this.vertical1.getTrafficLight().runRed();
            this.vertical2.getTrafficLight().runRed();

            this.horizontal1.getTrafficLight().runGreen();
            this.horizontal2.getTrafficLight().runGreen();

        }

    }

    public void setHorizontalRed(int Green, boolean newCurrentState) {

        if (intersectionType == CROSS_SECTION) {

            this.horizontal1.getTrafficLight().setRedTime(Green + 2000);
            this.horizontal2.getTrafficLight().setRedTime(Green + 2000);

            this.vertical1.getTrafficLight().setRedTime(Green + 2000);
            this.vertical2.getTrafficLight().setRedTime(Green + 2000);

            this.horizontal1.getTrafficLight().setGreenTime(Green);
            this.horizontal2.getTrafficLight().setGreenTime(Green);

            this.vertical1.getTrafficLight().setGreenTime(Green);
            this.vertical2.getTrafficLight().setGreenTime(Green);

            this.horizontal1.getTrafficLight().setYellowTime(2000);
            this.horizontal2.getTrafficLight().setYellowTime(2000);

            this.vertical1.getTrafficLight().setYellowTime(2000);
            this.vertical2.getTrafficLight().setYellowTime(2000);


            if (newCurrentState) {

                //1 - red, 2 - yellow, 3- green
                this.horizontal1.getTrafficLight().currentstate = 1;
                this.horizontal2.getTrafficLight().currentstate = 1;

                this.vertical1.getTrafficLight().currentstate = 3;
                this.vertical2.getTrafficLight().currentstate = 3;
            }
        }

        if (intersectionType == T_SECTION) {

        }


    }

    public void setVerticalRed(int Green, boolean newCurrentState) {

        this.horizontal1.getTrafficLight().setRedTime(Green + 2000);
        this.horizontal2.getTrafficLight().setRedTime(Green + 2000);

        this.vertical1.getTrafficLight().setRedTime(Green + 2000);
        this.vertical2.getTrafficLight().setRedTime(Green + 2000);

        this.horizontal1.getTrafficLight().setGreenTime(Green);
        this.horizontal2.getTrafficLight().setGreenTime(Green);

        this.vertical1.getTrafficLight().setGreenTime(Green);
        this.vertical2.getTrafficLight().setGreenTime(Green);


        if (newCurrentState) {

            //1 - red, 2 - yellow, 3- green
            this.horizontal1.getTrafficLight().currentstate = 3;
            this.horizontal2.getTrafficLight().currentstate = 3;

            this.vertical1.getTrafficLight().currentstate = 1;
            this.vertical2.getTrafficLight().currentstate = 1;
        }

    }

    public void setVerticalSequence(int Red, int Green) {

        this.vertical1.getTrafficLight().setRedTime(Red);
        this.vertical2.getTrafficLight().setRedTime(Red);
        this.vertical1.getTrafficLight().setGreenTime(Green);
        this.vertical2.getTrafficLight().setGreenTime(Green);

    }

    //TODO limit maximum green time , min green time, max red time, min red time
    public void moreGreenHorizontal(int increment) {

        int oldGreenTimeHorizontal = this.horizontal1.trafficLight.greenTime;
        int oldRedTimeVertical = this.vertical1.trafficLight.redTime;

        this.horizontal1.getTrafficLight().setGreenTime(oldGreenTimeHorizontal + increment);
        this.horizontal2.getTrafficLight().setGreenTime(oldGreenTimeHorizontal + increment);


        this.vertical1.getTrafficLight().setRedTime(oldRedTimeVertical + increment);
        this.vertical2.getTrafficLight().setRedTime(oldRedTimeVertical + increment);
    }

    public void moreGreenVertical(int increment) {

        int oldGreenTimeVertical = this.vertical1.trafficLight.greenTime;
        int oldGreenTimeHorizontal = this.horizontal1.trafficLight.greenTime;
        int oldRedTimeHorizontal = this.horizontal1.trafficLight.redTime;
        int oldRedVertical = this.vertical1.trafficLight.redTime;

        this.vertical1.getTrafficLight().setGreenTime(oldGreenTimeVertical + increment);
        this.vertical2.getTrafficLight().setGreenTime(oldGreenTimeVertical + increment);

        this.horizontal1.getTrafficLight().setRedTime(oldRedTimeHorizontal + increment);
        this.horizontal2.getTrafficLight().setRedTime(oldRedTimeHorizontal + increment);

    }

    public void lessGreenHorizontal(int decrement) {

        int oldGreenTimeHorizontal = this.horizontal1.trafficLight.greenTime;
        int oldRedTimeVertical = this.vertical1.trafficLight.redTime;

        this.horizontal1.getTrafficLight().setGreenTime(oldGreenTimeHorizontal - decrement);
        this.horizontal2.getTrafficLight().setGreenTime(oldGreenTimeHorizontal - decrement);


        this.vertical1.getTrafficLight().setRedTime(oldRedTimeVertical - decrement);
        this.vertical2.getTrafficLight().setRedTime(oldRedTimeVertical - decrement);
    }

    public void lessGreenVertical(int decrement) {

        int oldGreenTimeVertical = this.vertical1.trafficLight.greenTime;
        int oldRedTimeHorizontal = this.horizontal1.trafficLight.redTime;

        this.vertical1.getTrafficLight().setGreenTime(oldGreenTimeVertical - decrement);
        this.vertical2.getTrafficLight().setGreenTime(oldGreenTimeVertical - decrement);

        this.horizontal1.getTrafficLight().setRedTime(oldRedTimeHorizontal - decrement);
        this.horizontal2.getTrafficLight().setRedTime(oldRedTimeHorizontal - decrement);
    }

    public void moreGreenLessRedHorizontal(int increment) {

        int oldGreenTimeVertical = this.vertical1.trafficLight.greenTime;
        int oldRedTimeHorizontal = this.horizontal1.trafficLight.redTime;
        int oldGreenTimeHorizontal = this.horizontal1.trafficLight.greenTime;
        int oldRedTimeVertical = this.vertical1.trafficLight.redTime;

        if (oldGreenTimeHorizontal + increment <= MAX_ALLOWED_GREEN) {
            this.horizontal1.getTrafficLight().setGreenTime(oldGreenTimeHorizontal + increment);
            this.horizontal2.getTrafficLight().setGreenTime(oldGreenTimeHorizontal + increment);
        } else {
            this.horizontal1.getTrafficLight().setGreenTime(MAX_ALLOWED_GREEN);
            this.horizontal2.getTrafficLight().setGreenTime(MAX_ALLOWED_GREEN);
        }

        if (oldRedTimeHorizontal - increment >= MIN_ALLOWED_RED) {
            this.horizontal1.getTrafficLight().setRedTime(oldRedTimeHorizontal - increment);
            this.horizontal2.getTrafficLight().setRedTime(oldRedTimeHorizontal - increment);
        } else {
            this.horizontal1.getTrafficLight().setRedTime(MIN_ALLOWED_RED);
            this.horizontal2.getTrafficLight().setRedTime(MIN_ALLOWED_RED);
        }

        if (oldGreenTimeVertical - increment >= MIN_ALLOWED_GREEN) {
            this.vertical1.getTrafficLight().setGreenTime(oldGreenTimeVertical - increment);
            this.vertical2.getTrafficLight().setGreenTime(oldGreenTimeVertical - increment);
        } else {
            this.vertical1.getTrafficLight().setGreenTime(MIN_ALLOWED_GREEN);
            this.vertical2.getTrafficLight().setGreenTime(MIN_ALLOWED_GREEN);
        }

        if (oldRedTimeVertical + increment <= MAX_ALLOWED_RED) {
            this.vertical1.getTrafficLight().setRedTime(oldRedTimeVertical + increment);
            this.vertical2.getTrafficLight().setRedTime(oldRedTimeVertical + increment);
        } else {
            this.vertical1.getTrafficLight().setRedTime(MAX_ALLOWED_RED);
            this.vertical2.getTrafficLight().setRedTime(MAX_ALLOWED_RED);
        }


    }


    public void moreGreenLessRedVertical(int increment) {

        int oldGreenTimeVertical = this.vertical1.trafficLight.greenTime;
        int oldRedTimeHorizontal = this.horizontal1.trafficLight.redTime;
        int oldGreenTimeHorizontal = this.horizontal1.trafficLight.greenTime;
        int oldRedTimeVertical = this.vertical1.trafficLight.redTime;


        this.horizontal1.getTrafficLight().setGreenTime(oldGreenTimeHorizontal - increment);
        this.horizontal2.getTrafficLight().setGreenTime(oldGreenTimeHorizontal - increment);

        this.horizontal1.getTrafficLight().setRedTime(oldRedTimeHorizontal + increment);
        this.horizontal2.getTrafficLight().setRedTime(oldRedTimeHorizontal + increment);

        this.vertical1.getTrafficLight().setGreenTime(oldGreenTimeVertical + increment);
        this.vertical2.getTrafficLight().setGreenTime(oldGreenTimeVertical + increment);

        this.vertical1.getTrafficLight().setRedTime(oldRedTimeVertical - increment);
        this.vertical2.getTrafficLight().setRedTime(oldRedTimeVertical - increment);
    }


    public void connect() {


        unstructured = map.getIncomingRoads(intersection);


        for (int i = 0; i < unstructured.size(); i++) {

            if (unstructured.get(i).getDirection() == 6) {
                this.horizontal1 = unstructured.get(i);
            }
            if (unstructured.get(i).getDirection() == 4) {
                this.horizontal2 = unstructured.get(i);
            }
            if (unstructured.get(i).getDirection() == 2) {
                this.vertical1 = unstructured.get(i);
            }
            if (unstructured.get(i).getDirection() == 8) {
                this.vertical2 = unstructured.get(i);
            }
        }


    }

    public void connect2HorizontalRoads(Road a, Road b) {

        if ((a.getDirection() == 6 && b.getDirection() == 4) || (a.getDirection() == 4 && b.getDirection() == 6)) {
            this.horizontal1 = a;
            this.horizontal2 = b;
        }

        a.setRoadWithSameFSM(b);
        b.setRoadWithSameFSM(a);

    }

    public void connect2VerticalRoads(Road a, Road b) {

        if ((a.getDirection() == 8 && b.getDirection() == 2) || (a.getDirection() == 2 && b.getDirection() == 8)) {
            this.vertical1 = a;
            this.vertical2 = b;
        }

        a.setRoadWithSameFSM(b);
        b.setRoadWithSameFSM(a);
    }

    public ArrayList<Road> getAllFSMRoads() {

        ArrayList<Road> out = new ArrayList<>();

        if (horizontal1 != null && horizontal2 != null && vertical1 != null && vertical2 != null) {
            out.add(horizontal1);
            out.add(horizontal2);
            out.add(vertical1);
            out.add(vertical2);
        }

        return out;
    }

    public Node getIntersection() {
        return this.intersection;
    }

}
