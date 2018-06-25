package simulation;

import java.lang.reflect.Array;
import java.util.ArrayList;
import backend.Car;

public class PathConstructor {
    Graph graph;
    int SIM_SIZE;
    ArrayList<Integer> IntPath;
    simulationPath simPath;
    int dir;
    int offset = 13;
    Car car;


    public PathConstructor(ArrayList<Integer> IntPath, Graph graph, int SIM_SIZE, Car car)
    {
        this.car = car;
        this.graph = graph;
        this.SIM_SIZE = SIM_SIZE;
        this.IntPath = IntPath;

    }

    public simulationPath constructPath() {
        //if a path has 2 points minimum

        if (IntPath.size() == 2) {
            for (int i = 0; i < IntPath.size(); i++) {
                int correctionX = 0;
                int correctionY = 0;
                if (i == 0) {
                    int curX = graph.getNodeByIndex(IntPath.get(0)).x * SIM_SIZE;
                    int curY = graph.getNodeByIndex(IntPath.get(0)).y * SIM_SIZE;
                    int newX = graph.getNodeByIndex(IntPath.get(1)).x * SIM_SIZE;
                    int newY = graph.getNodeByIndex(IntPath.get(1)).y * SIM_SIZE;

                    dir = checkDirection(curX, curY, newX, newY);

                    if (dir == 6) {
                        correctionY = +5;
                    }

                    if (dir == 4) {
                        correctionY = -9;
                    }

                    if (dir == 8) {
                        correctionX = -1;
                    }

                    if (dir == 2) {
                        correctionX = -17;
                    }
                } else {
                    int curX = graph.getNodeByIndex(IntPath.get(0)).x * SIM_SIZE;
                    int curY = graph.getNodeByIndex(IntPath.get(0)).y * SIM_SIZE;
                    int newX = graph.getNodeByIndex(IntPath.get(1)).x * SIM_SIZE;
                    int newY = graph.getNodeByIndex(IntPath.get(1)).y * SIM_SIZE;

                    int dir = checkDirection(curX, curY, newX, newY);

                    if (dir == 6) {
                        correctionY = +5;
                    }

                    if (dir == 4) {
                        correctionY = -9;
                    }

                    if (dir == 8) {
                        correctionX = -1;
                    }

                    if (dir == 2) {
                        correctionX = -17;
                    }
                }
                if (i == 0) {
                    simPath = new simulationPath(graph.getNodeByIndex(IntPath.get(i)).x * SIM_SIZE + SIM_SIZE / 2 + correctionX,
                            graph.getNodeByIndex(IntPath.get(i)).y * SIM_SIZE + SIM_SIZE / 2 + correctionY);
                } else {

                    simPath.addtoPath(graph.getNodeByIndex(IntPath.get(i)).x * SIM_SIZE + SIM_SIZE / 2 + correctionX, graph.getNodeByIndex(IntPath.get(i)).y * SIM_SIZE + SIM_SIZE / 2 + correctionY, dir);
                }

                int pathX = graph.getNodeByIndex(IntPath.get(i)).x * SIM_SIZE + SIM_SIZE / 2 + correctionX;
                int pathY = graph.getNodeByIndex(IntPath.get(i)).y * SIM_SIZE + SIM_SIZE / 2 + correctionY;
//                System.out.println("Added to path: " + pathX + ", " + pathY + " with corrections: " + correctionX + ", " + correctionY);
            }


        }

        if (IntPath.size() > 2) {
            int correctionX = 0;
            int correctionY = 0;

            for (int i = 0; i < IntPath.size(); i++) {
                //determine starting position
                if (i == 0) {
                    int curX = graph.getNodeByIndex(IntPath.get(0)).x * SIM_SIZE;
                    int curY = graph.getNodeByIndex(IntPath.get(0)).y * SIM_SIZE;
                    int newX = graph.getNodeByIndex(IntPath.get(1)).x * SIM_SIZE;
                    int newY = graph.getNodeByIndex(IntPath.get(1)).y * SIM_SIZE;

                    dir = checkDirection(curX, curY, newX, newY);

                    if (dir == 6) {
                        correctionY = +5;
                    }

                    if (dir == 4) {
                        correctionY = -9;
                    }

                    if (dir == 8) {
                        correctionX = -1;
                    }

                    if (dir == 2) {
                        correctionX = -17;
                    }


                }

                //check previous and next direction and determine point on intersection car needs to travel through
                else if (i < IntPath.size() - 1) {
                    int curX = graph.getNodeByIndex(IntPath.get(i - 1)).x * SIM_SIZE;
                    int curY = graph.getNodeByIndex(IntPath.get(i - 1)).y * SIM_SIZE;
                    int newX = graph.getNodeByIndex(IntPath.get(i)).x * SIM_SIZE;
                    int newY = graph.getNodeByIndex(IntPath.get(i)).y * SIM_SIZE;

                    dir = checkDirection(curX, curY, newX, newY);

                    curX = graph.getNodeByIndex(IntPath.get(i)).x * SIM_SIZE;
                    curY = graph.getNodeByIndex(IntPath.get(i)).y * SIM_SIZE;
                    newX = graph.getNodeByIndex(IntPath.get(i + 1)).x * SIM_SIZE;
                    newY = graph.getNodeByIndex(IntPath.get(i + 1)).y * SIM_SIZE;

                    int newDir = checkDirection(curX, curY, newX, newY);


                    if (dir == 6) {
                        correctionY = +5;

                        if (newDir == 2) {
                            correctionX = -17;
                        }

                        if (newDir == 8) {
                            correctionX = -1;
                        }

                    }

                    if (dir == 4) {
                        correctionY = -9;

                        if (newDir == 2) {
                            correctionX = -17;
                        }

                        if (newDir == 8) {
                            correctionX = -1;
                        }
                    }

                    if (dir == 8) {
                        correctionX = -1;

                        if (newDir == 6) {
                            correctionY = +5;
                        }

                        if (newDir == 4) {
                            correctionY = -9;
                        }
                    }

                    if (dir == 2) {
                        correctionX = -17;

                        if (newDir == 6) {
                            correctionY = +5;
                        }

                        if (newDir == 4) {
                            correctionY = -9;
                        }
                    }
                }
                else if (i == IntPath.size() - 1) {

                    int curX = graph.getNodeByIndex(IntPath.get(i - 1)).x * SIM_SIZE;
                    int curY = graph.getNodeByIndex(IntPath.get(i - 1)).y * SIM_SIZE;
                    int newX = graph.getNodeByIndex(IntPath.get(i)).x * SIM_SIZE;
                    int newY = graph.getNodeByIndex(IntPath.get(i)).y * SIM_SIZE;

                    dir = checkDirection(curX, curY, newX, newY);


                    if (dir == 6) {
                        correctionY = +5;
                    }

                    if (dir == 4) {
                        correctionY = -9;
                    }

                    if (dir == 8) {
                        correctionX = -1;
                    }

                    if (dir == 2) {
                        correctionX = -17;
                    }

                }


                //set pthstep

                if (i == 0) {

                    simPath = new simulationPath(graph.getNodeByIndex(IntPath.get(i)).x * SIM_SIZE + SIM_SIZE / 2 + correctionX,
                            graph.getNodeByIndex(IntPath.get(i)).y * SIM_SIZE + SIM_SIZE / 2 + correctionY);
                } else {

                    int pathX = graph.getNodeByIndex(IntPath.get(i)).x * SIM_SIZE + SIM_SIZE / 2 + correctionX;
                    int pathY = graph.getNodeByIndex(IntPath.get(i)).y * SIM_SIZE + SIM_SIZE / 2 + correctionY;
//                    System.out.println("Added to path: " + pathX + ", " + pathY + " with corrections: " + correctionX + ", " + correctionY + "DIR: " + dir);
                    simPath.addtoPath(graph.getNodeByIndex(IntPath.get(i)).x * SIM_SIZE + SIM_SIZE / 2 + correctionX, graph.getNodeByIndex(IntPath.get(i)).y * SIM_SIZE + SIM_SIZE / 2 + correctionY, dir);
                }


            }


        }

        simPath.setLanes();

        prePosition();

        return simPath;
    }

    public void prePosition()
    {
        for(int i = 0; i<simPath.path.size(); i++)
        {
            if(i+2 < simPath.path.size())
            {
                int curdir =  checkDirection(simPath.path.get(i).get(0), simPath.path.get(i).get(1),simPath.path.get(i+1).get(0), simPath.path.get(i+1).get(1));
                int newdir = checkDirection(simPath.path.get(i+1).get(0), simPath.path.get(i+1).get(1),simPath.path.get(i+2).get(0), simPath.path.get(i+2).get(1));



                if(curdir == 6)
                {
                    if(newdir == 2)
                    {
                        simPath.setLaneIndex(1, i);
                    }

                    if(newdir == 8)
                    {
                        simPath.setLaneIndex(0, i);
                    }
                }

                if(curdir == 4)
                {
                    if(newdir == 2)
                    {
                        simPath.setLaneIndex(0, i);
                    }

                    if(newdir == 8)
                    {
                        simPath.setLaneIndex(1, i);
                    }
                }

                if(curdir == 2)
                {
                    if(newdir == 6)
                    {
                        simPath.setLaneIndex(0, i);
                    }

                    if(newdir == 4)
                    {
                        simPath.setLaneIndex(1, i);
                    }
                }

                if(curdir == 8)
                {
                    if(newdir == 4)
                    {
                        simPath.setLaneIndex(0, i);
                    }

                    if(newdir == 6)
                    {
                        simPath.setLaneIndex(1, i);
                    }
                }


            }
        }

        correct();

        setLanes();
        
    }

    public void correct()
    {
        boolean outer = false;

        for(int i = simPath.laneIndices.size()-1; i>-1; i--)
        {
            if(i>0 && simPath.laneIndices.get(i-1) == 0 && simPath.directions.get(i)== simPath.directions.get(i-1) && outer)
            {
                simPath.laneIndices.remove(i-1);
                simPath.laneIndices.add(i-1, 1);
                if(i == 1)
                {
                    car.StartingLane = 1;
                }
            }
            if(simPath.laneIndices.get(i) == 1) outer = true;
            else outer = false;

        }
    }

    public void setLanes()
    {

        for(int i = 0; i < simPath.laneIndices.size(); i++)
        {
            if(i < simPath.directions.size()-1) {

                int dir = checkDirection(simPath.getX(i), simPath.getY(i), simPath.getX(i+1),simPath.getY(i+1));


                int newdir = simPath.directions.get(i + 1);

                if (dir == 6) {
                    if (simPath.laneIndices.get(i) == 1) {
                        if (i == 0) {
                            simPath.startX = simPath.getX(i);
                            simPath.startY = simPath.getY(i) + offset;
                        }
                        simPath.addtoPathAt(simPath.getX(i), simPath.getY(i) + offset, dir, i);
                        simPath.addtoPathAt(simPath.getX(i + 1), simPath.getY(i + 1) + offset, newdir, i + 1);
                    }
                }
                if (dir == 4) {
                    if (simPath.laneIndices.get(i) == 1) {
                        if (i == 0) {
                            simPath.startX = simPath.getX(i);
                            simPath.startY = simPath.getY(i) - offset;
                        }
                        simPath.addtoPathAt(simPath.getX(i), simPath.getY(i) - offset, dir, i);
                        simPath.addtoPathAt(simPath.getX(i + 1), simPath.getY(i + 1) - offset, newdir, i + 1);
                    }
                }
                if (dir == 8) {
                    if (simPath.laneIndices.get(i) == 1) {
                        if (i == 0) {
                            simPath.startX = simPath.getX(i) + offset;
                            simPath.startY = simPath.getY(i);
                        }
                        simPath.addtoPathAt(simPath.getX(i) + offset, simPath.getY(i), dir, i);
                        simPath.addtoPathAt(simPath.getX(i + 1) + offset, simPath.getY(i + 1), newdir, i + 1);

                    }
                }
                if (dir == 2) {
                    if (simPath.laneIndices.get(i) == 1) {
                        if (i == 0) {
                            simPath.startX = simPath.getX(i) - offset;
                            simPath.startY = simPath.getY(i);
                        }
                        simPath.addtoPathAt(simPath.getX(i) - offset, simPath.getY(i), dir, i);
                        simPath.addtoPathAt(simPath.getX(i + 1) - offset, simPath.getY(i + 1), newdir, i + 1);
                    }
                }

            }

        }

        System.out.println("Sizes are: Path = "+ simPath.path.size() + " directions = " + simPath.directions.size() + " lanes = " + simPath.laneIndices.size());


    }

    private int checkDirection(int oldX, int oldY, int newX, int newY) {

        //north
        if (oldX == newX && oldY < newY) {
//            System.out.println("Direction is: " + 2);
            return 2;
        }
        //south
        if (oldX == newX && oldY > newY) {
//            System.out.println("Direction is: " + 8);
            return 8;
        }
        //east
        if (oldX > newX && oldY == newY) {
            //turn east
//            System.out.println("Direction is: " +5);
            return 4;
        }
        //west
        if (oldX < newX && oldY == newY) {
//            System.out.println("Direction is: " + 6);
            return 6;
        }


        return 0;

    }



    private int changeLane()
    {
        int index = 0;

        return index;
    }


}
