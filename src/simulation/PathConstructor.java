package simulation;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PathConstructor {
    Graph graph;
    int SIM_SIZE;
    ArrayList<Integer> IntPath;
    simulationPath simPath;
    int dir;

    public PathConstructor(ArrayList<Integer> IntPath, Graph graph, int SIM_SIZE)
    {
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
                        correctionY = +8;
                    }

                    if (dir == 4) {
                        correctionY = -9;
                    }

                    if (dir == 8) {
                        correctionX = +2;
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
                        correctionY = +8;
                    }

                    if (dir == 4) {
                        correctionY = -9;
                    }

                    if (dir == 8) {
                        correctionX = +2;
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
                System.out.println("Added to path: " + pathX + ", " + pathY + " with corrections: " + correctionX + ", " + correctionY);
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
                        correctionY = +8;
                    }

                    if (dir == 4) {
                        correctionY = -9;
                    }

                    if (dir == 8) {
                        correctionX = +2;
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
                        correctionY = +8;

                        if (newDir == 2) {
                            correctionX = -17;
                        }

                        if (newDir == 8) {
                            correctionX = +2;
                        }

                    }

                    if (dir == 4) {
                        correctionY = -9;

                        if (newDir == 2) {
                            correctionX = -17;
                        }

                        if (newDir == 8) {
                            correctionX = +2;
                        }
                    }

                    if (dir == 8) {
                        correctionX = +2;

                        if (newDir == 6) {
                            correctionY = +8;
                        }

                        if (newDir == 4) {
                            correctionY = -9;
                        }
                    }

                    if (dir == 2) {
                        correctionX = -17;

                        if (newDir == 6) {
                            correctionY = +8;
                        }

                        if (newDir == 4) {
                            correctionY = -9;
                        }
                    }
                } else if (i == IntPath.size() - 1) {

                    int curX = graph.getNodeByIndex(IntPath.get(i - 1)).x * SIM_SIZE;
                    int curY = graph.getNodeByIndex(IntPath.get(i - 1)).y * SIM_SIZE;
                    int newX = graph.getNodeByIndex(IntPath.get(i)).x * SIM_SIZE;
                    int newY = graph.getNodeByIndex(IntPath.get(i)).y * SIM_SIZE;

                    dir = checkDirection(curX, curY, newX, newY);


                    if (dir == 6) {
                        correctionY = +8;
                    }

                    if (dir == 4) {
                        correctionY = -9;
                    }

                    if (dir == 8) {
                        correctionX = +2;
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
                    System.out.println("Added to path: " + pathX + ", " + pathY + " with corrections: " + correctionX + ", " + correctionY + "DIR: " + dir);
                    simPath.addtoPath(graph.getNodeByIndex(IntPath.get(i)).x * SIM_SIZE + SIM_SIZE / 2 + correctionX, graph.getNodeByIndex(IntPath.get(i)).y * SIM_SIZE + SIM_SIZE / 2 + correctionY, dir);
                }


            }


        }

        return simPath;
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


}
