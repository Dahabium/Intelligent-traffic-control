package backend;

import simulation.*;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandersalahmadibapost on 29/03/2018.
 */
public class Greedy {

    private ArrayList<Node> path = new ArrayList<Node>();
    private ArrayList<Boolean> visited = new ArrayList<Boolean>();

    public Greedy()
    {

    }

    public ArrayList<Node> getPath(Node start, Node end, Graph graph){

        path.add(start);
        visited.set(start.getIndex(), true);

        List<Node> neighbours = graph.getAdjecents(start);

        int index = 0;
        double max = 1000;

        while (path.get(path.size() - 1) != end) {
            for (int i = 0; i < neighbours.size(); i++) {
                if (calcPytho(neighbours.get(i), end) < max && !visited.get(neighbours.get(i).getIndex())) {
                    max = calcPytho(neighbours.get(i), end);
                    index = i;
                }
            }

            if(!visited.get(neighbours.get(index).getIndex())) {
                path.add(neighbours.get(index));
                visited.set(neighbours.get(index).getIndex(), true);
            } else {

                if(path.size() > 0) {
                    visited.set(path.get(path.size() - 1).getIndex(), true);
                    path.remove(path.get(path.size() - 1));
                } else {
                    return;
                }
            }
        }
        return path;
    }

    public double calcPytho(Node start, Node end) {


        return Math.sqrt(Math.pow((start.getXpos() - end.getXpos()), 2) + (Math.pow(start.getYpos() - end.getYpos(), 2)));
    }
}
