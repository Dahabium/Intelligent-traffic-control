package backend;


import javafx.scene.Group;
import simulation.TrafficLight;

import java.util.Timer;
import java.util.TimerTask;

public class FSMTrafficLight {


    public TrafficLight trafficLightGui;

    public final int RED = 1;
    public final int YELLOW = 2;
    public final int GREEN = 3;


    public int redTime, yellowTime, greenTime;

    //1 - red, 2 - yellow, 3- green

    //1 > 3 > 2 > 1 > 3 > 2
    // Red > Green > Yellow > Red > Green > Yellow > ...

    public int currentstate;


    public FSMTrafficLight(int redTime, int greenTime, int yellowTime, int currentstate, int XPos, int YPos) {

        this.trafficLightGui = new TrafficLight(XPos,YPos);

        this.redTime = redTime;
        this.yellowTime = yellowTime;
        this.greenTime = greenTime;

        this.currentstate = currentstate;

    }


    public void runRed() {
        //check...
        currentstate = RED;

        this.trafficLightGui.changeTrafficLightColor(RED);

        final Timer timer = new Timer();
        final TimerTask task = new TimerTask() {

            @Override
            public void run() {
                if (currentstate == RED) {

                    timer.cancel();
                    timer.purge();

                    currentstate = GREEN;

                    runGreen();

                }
            }
        };


        timer.schedule(task, redTime);

    }

    public void runGreen() {
        //check...
        currentstate = GREEN;

        this.trafficLightGui.changeTrafficLightColor(GREEN);


        final Timer timer = new Timer();
        final TimerTask task = new TimerTask() {

            @Override
            public void run() {
                if (currentstate == GREEN) {
                    timer.cancel();
                    timer.purge();

                    currentstate = YELLOW;

                    runYellow();

                }
            }
        };

        timer.schedule(task, greenTime);

    }


    public void runYellow() {

        this.trafficLightGui.changeTrafficLightColor(YELLOW);

        final Timer timer = new Timer();


        final TimerTask task = new TimerTask() {

            @Override
            public void run() {
                if (currentstate == YELLOW) {

                    timer.cancel();
                    timer.purge();

                    currentstate = RED;

                    runRed();

                }
            }
        };

        timer.schedule(task, yellowTime);

    }

    public int getCurrentstate(){
        return currentstate;
    }

    public void setGreenTime(int greeenTime){
        this.greenTime = greeenTime;
    }

    public void setYellowTime(int yellowTime){
        this.yellowTime = yellowTime;
    }

    public void setRedTime(int redTime){
        this.redTime = redTime;
    }

    public void setTimingSequences(int redTime,int greenTime, int yellowTime){
        this.redTime = redTime;
        this.greenTime = greenTime;
        this.yellowTime = yellowTime;
    }


    public void setCurrentstate(int newstate){
        this.currentstate = newstate;
    }

    public Group getTrafficLightGui(){
        return this.trafficLightGui.getTrafficlight();
    }

}
