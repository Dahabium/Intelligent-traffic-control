package backend;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.util.Duration;
import simulation.TrafficLight;

import java.util.Timer;
import java.util.TimerTask;

public class FSMTrafficLight {


    public final int RED = 1;
    public final int YELLOW = 2;
    public final int GREEN = 3;
    public TrafficLight trafficLightGui;
    public int redTime, yellowTime, greenTime;
    public int currentstate;
    Timeline redTimeLine;
    Timeline yellowTimeLine;
    //1 - red, 2 - yellow, 3- green


    //1 > 3 > 2 > 1 > 3 > 2
    // Red > Green > Yellow > Red > Green > Yellow > ...
    Timeline greenTimeLine;


    public FSMTrafficLight(int redTime, int greenTime, int yellowTime, int currentstate, int XPos, int YPos) {

        this.trafficLightGui = new TrafficLight(XPos, YPos);

        this.redTime = redTime;
        this.yellowTime = yellowTime;
        this.greenTime = greenTime;

        this.currentstate = currentstate;

    }


    public void runRed() {
        //check...
        currentstate = RED;

        this.trafficLightGui.changeTrafficLightColor(RED);

        this.redTimeLine = new Timeline(new KeyFrame(Duration.millis(redTime), ev -> {

            if (currentstate == RED) {
                currentstate = GREEN;

                runGreen();
            }

        }));

        redTimeLine.setCycleCount(1);
        redTimeLine.play();

    }

    public void runGreen() {
        //check...
        currentstate = GREEN;

        this.trafficLightGui.changeTrafficLightColor(GREEN);

        this.greenTimeLine = new Timeline(new KeyFrame(Duration.millis(greenTime), ev -> {

            if (currentstate == GREEN) {
                currentstate = YELLOW;

                runYellow();
            }

        }));

        greenTimeLine.setCycleCount(1);
        greenTimeLine.play();

    }

    public void runGreenAfterDelay(int delay) {

        Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(delay), ev -> {
            System.out.println("Rungreen After Delay");
            runGreen();
        }));

        timeline2.setCycleCount(1);
        timeline2.play();
    }


    public void runYellow() {

        currentstate = YELLOW;

        this.trafficLightGui.changeTrafficLightColor(YELLOW);

        this.yellowTimeLine = new Timeline(new KeyFrame(Duration.millis(yellowTime), ev -> {

            if (currentstate == YELLOW) {

                currentstate = RED;

                runRed();

            }
        }
        ));

        yellowTimeLine.setCycleCount(1);
        yellowTimeLine.play();


    }

    public int getCurrentstate() {
        return currentstate;
    }

    public void setCurrentstate(int newstate) {
        this.currentstate = newstate;
    }

    public void setGreenTime(int greeenTime) {
        this.greenTime = greeenTime;
    }

    public void setYellowTime(int yellowTime) {
        this.yellowTime = yellowTime;
    }

    public void setRedTime(int redTime) {
        this.redTime = redTime;
    }

    public void setTimingSequences(int redTime, int greenTime, int yellowTime) {
        this.redTime = redTime;
        this.greenTime = greenTime;
        this.yellowTime = yellowTime;
    }

    public Group getTrafficLightGui() {
        return this.trafficLightGui.getTrafficlight();
    }

    public void stopTimeLines() {
        this.redTimeLine.stop();
        this.greenTimeLine.stop();
        if (this.yellowTimeLine != null) {

            this.yellowTimeLine.stop();
        }
    }

}
