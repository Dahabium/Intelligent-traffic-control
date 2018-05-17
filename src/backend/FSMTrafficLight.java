package backend;


import simulation.TrafficLight;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FSMTrafficLight {

    public TrafficLight trafficLightGui;
    public int redTime, yellowTime, greeenTime;

    //1 - red, 2 - yellow, 3- green
    public int currentstate;

    public FSMTrafficLight(int redTime, int yellowTime, int greeenTime, int currentstate){
        this.redTime = redTime;
        this.yellowTime = yellowTime;
        this.greeenTime = greeenTime;

        this.currentstate = currentstate;


        simulateFSM();
    }

    public void simulateFSM(){

        final ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

        if(currentstate == 1){
            ses.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    System.out.println("RED! ");
                    currentstate = 2;
                }
            }, 0, 5, TimeUnit.SECONDS);
        }

        if(currentstate == 2){
            ses.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    System.out.println("YELLOW! ");
                    currentstate = 3;

                }
            }, 0, 3, TimeUnit.SECONDS);

        }

        if(currentstate == 3){
            ses.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    System.out.println("GREEN! ");
                }
            }, 0, 10, TimeUnit.SECONDS);

        }





    }
}
