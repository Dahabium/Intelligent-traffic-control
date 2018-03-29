package backend;

import simulation.*;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandersalahmadibapost on 29/03/2018.
 */
public class Greedy {

    private ArrayList<Node> path = new ArrayList<Node>();

    public Greedy()
    {

    }

    public ArrayList<Node> getPath(Node start, Node end, Graph graph){

        path.add(start);

        List<Node> neighbours = graph.getAdjecents(start);

        int index = 0;
        double max = 1000;

        while (path.get(path.size() - 1) != end) {
            for (int i = 0; i < neighbours.size(); i++) {
                if (calcPytho(neighbours.get(i), end) < max) {
                    max = calcPytho(neighbours.get(i), end);
                    index = i;
                }
            }

            path.add(neighbours.get(index));
        }
        return path;
    }

    public double calcPytho(Node start, Node end) {


        return Math.sqrt(Math.pow((start.getXpos() - end.getXpos()), 2) + (Math.pow(start.getYpos() - end.getYpos(), 2)));
    }
}
