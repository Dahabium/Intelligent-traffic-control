package simulation;

import java.util.ArrayList;


public class simulationPath {
    int startX,startY;
    ArrayList<ArrayList<Integer>> path;
    ArrayList<Integer> directions;

    public simulationPath(int startX, int startY){
        this.startX = startX;
        this.startY = startY;

        ArrayList<Integer> startpoint = new ArrayList<>(2);

        startpoint.add(startX);
        startpoint.add(startY);

        path = new ArrayList<>();
        directions = new ArrayList<>();
        path.add(startpoint);
        System.out.println("startpoint " +startpoint);
    }

    public void addtoPath(int x, int y, int direction){
        ArrayList<Integer> point = new ArrayList<>(2);
        point.add(x);
        point.add(y);

        directions.add(direction);

        path.add(point);
    }

    public int getX(int intex){
        return this.path.get(intex).get(0);
    }

    public int getY(int intex){
        return this.path.get(intex).get(1);
    }


}
