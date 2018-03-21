package simulation;


import java.util.ArrayList;
import java.util.Arrays;

public class DummyGraph {

    public DummyGraph(){}

    public Graph createDummyGraph(){

        Node n1 = new Node("n1", 50, 50);
        Node n2 = new Node("n2", 120, 50);
        Node n3 = new Node("n3", 50, 120);

        Graph grp = new Graph();

        grp.addNode(n1);
        grp.addNode(n2);
        grp.addNode(n3);

        grp.addEdge(n1, n2);
        grp.addEdge(n1, n3);
        grp.addEdge(n2, n1);
        grp.addEdge(n2, n3);
        grp.addEdge(n3, n1);
        grp.addEdge(n3, n2);

        Node n4 = new Node(120,120);
        grp.addNode(n4);

        grp.addEdge(n2,n4);
        grp.addEdge(n3,n4);
        grp.addEdge(n4,n2);
        grp.addEdge(n4,n3);


        return grp;
    }

    public Graph createModelTestGraph(){

        Node n1 = new Node( 50, 50);
        Node n2 = new Node( 150, 50);
        Node n3 = new Node(250, 50);
        Node n4 = new Node(50,150);
        Node n5 = new Node(150,150 );
        Node n6 = new Node(250,150 );
        Node n7 = new Node(50,250 );
        Node n8 = new Node(150,250 );
        Node n9 = new Node(250,250 );

        Graph grp = new Graph();

        grp.addMultipleNodes(new ArrayList<>(Arrays.asList(n1,n2,n3,n4,n5,n6,n7,n8,n9)));

        for (int i = 0; i < grp.nodes.size(); i++) {
            grp.nodes.get(i).name = "n" + i;
        }

        grp.addEdge(n1, n2);
        grp.addEdge(n1, n3);
        grp.addEdge(n2, n1);
        grp.addEdge(n2, n3);
        grp.addEdge(n3, n1);
        grp.addEdge(n3, n2);


        return grp;
    }

}
