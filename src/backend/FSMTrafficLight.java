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


    public int redTime, yellowTime, greeenTime;

    //1 - red, 2 - yellow, 3- green
    // Red > Green > Yellow > Red > Green > Yellow > ...

    public int currentstate;


    public FSMTrafficLight(int redTime, int yellowTime, int greeenTime, int currentstate) {

        this.redTime = redTime;
        this.yellowTime = yellowTime;
        this.greeenTime = greeenTime;

        this.currentstate = currentstate;

    }

    public FSMTrafficLight(int redTime, int yellowTime, int greeenTime, int currentstate, int XPos, int YPos) {

        this.trafficLightGui = new TrafficLight(XPos,YPos);

        this.redTime = redTime;
        this.yellowTime = yellowTime;
        this.greeenTime = greeenTime;

        this.currentstate = currentstate;

    }

    public void runRed() {

        System.out.println("RED START");
        this.trafficLightGui.changeTrafficLightColor(RED);

        final Timer timer = new Timer();
        final TimerTask task = new TimerTask() {

            @Override
            public void run() {
                if (currentstate == RED) {
                    System.out.println("RUNNING");
                } else {

                    timer.cancel();
                    timer.purge();
                    System.out.println("RED DONE");

                    runGreen();

                }
            }
        };

        timer.schedule(task, redTime);

        //change state to green
        currentstate = GREEN;

    }

    public void runGreen() {

        System.out.println("Green RUN");
        this.trafficLightGui.changeTrafficLightColor(GREEN);


        final Timer timer = new Timer();

        final TimerTask task = new TimerTask() {

            @Override
            public void run() {
                if (currentstate == GREEN) {
                    System.out.println("RUNNING");
                } else {

                    timer.cancel();
                    timer.purge();
                    System.out.println("Green DONE");

                    runYellow();

                }
            }
        };

        timer.schedule(task, greeenTime);


        //change to yellow
        currentstate = YELLOW;


    }


    public void runYellow() {

        System.out.println("YELLOW RUN");
        this.trafficLightGui.changeTrafficLightColor(YELLOW);

        final Timer timer = new Timer();

        final TimerTask task = new TimerTask() {

            @Override
            public void run() {
                if (currentstate == YELLOW) {
                    System.out.println("RUNNING");
                } else {

                    timer.cancel();
                    timer.purge();
                    System.out.println("Yellow DONE");

                    runRed();

                }
            }
        };

        timer.schedule(task, yellowTime);

        //turn back to red
        currentstate = RED;

    }

    public int getCurrentstate(){
        return currentstate;
    }


    public Group getTrafficLightGui(){
        return this.trafficLightGui.getTrafficlight();
    }

}
