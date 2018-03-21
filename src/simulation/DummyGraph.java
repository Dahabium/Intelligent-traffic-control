package simulation;


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

    public Graph createDummyGraphV2(){

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


        return grp;
    }

}
