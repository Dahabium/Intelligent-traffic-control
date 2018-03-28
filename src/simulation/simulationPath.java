package simulation;

import java.util.ArrayList;
import java.util.List;


public class simulationPath {
    int startX,startY;
    ArrayList<ArrayList<Integer>> path;

    public simulationPath(int startX, int startY){
        this.startX = startX;
        this.startY = startY;

        ArrayList<Integer> startpoint = new ArrayList<>(2);

        startpoint.add(startX);
        startpoint.add(startY);

        path = new ArrayList<>();
        path.add(startpoint);
        System.out.println("startpoint " +startpoint);
    }

    public void addtoPath(int x, int y){
        ArrayList<Integer> point = new ArrayList<>(2);
        point.add(x);
        point.add(y);

        path.add(point);
    }


}
