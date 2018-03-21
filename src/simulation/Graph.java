package simulation;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

//for exporting to XML file
import org.w3c.dom.*;
import java.io.*;
import com.sun.org.apache.xml.internal.serialize.*;
import javax.xml.parsers.*;

public class Graph {

    public List<Node> nodes;
    public List<Edge> edges;

    ArrayList<Line> lines;

    //Constructor with no input parameters.
    public Graph(){
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();

        lines = new ArrayList<>();

    }

    public void addNode(Node node){
        this.nodes.add(node);
    }

    public void removeNode(Node node){
        this.nodes.remove(node);
    }

//    public void addEdge(Node start, Node end,int incominglanes, int outcominglanes, double weight){
//        start.connections.add(new Edge(start,end, incominglanes, outcominglanes, weight));
//
//    }
    public void export() throws ParserConfigurationException, FileNotFoundException, IOException
    {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document xmlDocument = documentBuilder.newDocument();
//        <Nodes>
//            <Node>
//                  ID, Xpos, Ypos

//                  <Edges>
//                      <Edge>
//                          ID, Egdge ID, Start ID, End ID

//                      </Edge>
//                  </Edges>
//            </Node>
//        </Nodes>
//        Element intersectionsXML = xmlDocument.createElement("Nodes");
//        Element intXML = xmlDocument.createElement("Node");
//        Element nodeID = xmlDocument.createElement("ID");
//        Element roadsXML = xmlDocument.createElement("Edges");
//        Element roXML = xmlDocument.createElement("Edge");
//        Element edgeID = xmlDocument.createElement("Edge ID");
//        Element startEnd = xmlDocument.createElement("Start End");
//        Text productnameText = xmlDocument.createTextNode("Graph");
//        Element xyCoords = xmlDocument.createElement("x and y");
//
//
//        xmlDocument.appendChild(intersectionsXML);
//        intersectionsXML.appendChild()

        Element xmlNodes = xmlDocument.createElement("Nodes");
        xmlDocument.appendChild(xmlNodes);
        for(int i = 0; i<nodes.size(); i++)
        {
            Element xmlNode = xmlDocument.createElement("Node");
            String id = Integer.toString(i);
            String xmlX = Double.toString(nodes.get(i).Xpos);
            String xmlY = Double.toString(nodes.get(i).Ypos);
            xmlNode.setTextContent(id + " " + xmlX + " " + xmlY);
            xmlNodes.appendChild(xmlNode);

            Element xmlEdges = xmlDocument.createElement("Edges");
            xmlNode.appendChild(xmlEdges);
            //loop through the edges of this node and add each to the edgeslist
            for(int j = 0; j<nodes.get(i).connections.size(); j++)
            {
                if(nodes.get(i).connections.get(j).start == nodes.get(i)) {

                    System.out.println("edge " + j);
                    Element xmlEdge = xmlDocument.createElement("Edge");
                    xmlEdges.appendChild(xmlEdge);

                    String from = id;
                    String to = " ";
                    for (int k = 0; k < nodes.size(); k++) {
                        if (nodes.get(i).connections.get(j).end == nodes.get(k)) {
                            to = Integer.toString(k);
                        }
                    }
                    xmlEdge.setTextContent(from + " " + to);
                }

                //Text edgeID = xmlDocument.createTextNode("Edge ID");
            }
            System.out.println("Node " + i);
        }

        OutputFormat output = new OutputFormat(xmlDocument);
        output.setIndenting(true);
        File xmlFile = new File("Graph1.xml");
        FileOutputStream outputStream = new FileOutputStream(xmlFile);
        XMLSerializer serializer = new XMLSerializer(outputStream, output);
        serializer.serialize(xmlDocument);



    }

    public void addEdge(Node start, Node end){
        edges.add(new Edge(start,end));
    }

    public void removeEdge(Edge edge){
        edges.remove(edge);
    }

    public void showGraph(GraphicsContext gc){

        //Show the Vertexes in GUI
        for (int i = 0; i < this.nodes.size(); i++) {

            gc.fillOval(this.nodes.get(i).Xpos, this.nodes.get(i).Ypos, 30, 30);
            gc.setFill(Color.WHITE);
            gc.fillText(this.nodes.get(i).name, this.nodes.get(i).Xpos + 7, this.nodes.get(i).Ypos + 15);
            gc.setFill(Color.BLACK);

        }

        //Show the edges between vertexes in GUI
        for (int i = 0; i < this.edges.size(); i++) {
            gc.strokeLine(this.edges.get(i).start.Xpos + 15, this.edges.get(i).start.Ypos + 15,
                    this.edges.get(i).end.Xpos + 15, this.edges.get(i).end.Ypos + 15);

        }
    }

    public void printAdjecency(){

        System.out.println("Number of nodes " + this.nodes.size());

        for (int i = 0; i < this.nodes.size(); i++) {
            System.out.println("Current node " + i + "  " + this.nodes.get(i).Xpos + "  " + this.nodes.get(i).Ypos);

        }

        System.out.println("Number of edges " + this.edges.size());

        for (int j = 0; j < this.edges.size(); j++) {
            System.out.println("Edge :" + j + " start  " + this.edges.get(j).start.Xpos + "  " + this.edges.get(j).start.Ypos + "  end "+
                    this.edges.get(j).end.Xpos + "  " + this.edges.get(j).end.Ypos);

        }


    }

    public List<Node> getAdjecents(Node node){
        List<Node> out = new ArrayList<>();
        for (int i = 0; i < this.edges.size(); i++) {
            if(this.edges.get(i).start == node){
                out.add(this.edges.get(i).end);
            }
        }

        return out;
    }

    public Node convertCircleToNode(Circle circle){
        Node node = new Node(circle.getCenterX(),circle.getCenterY());

        return node;
    }

    public void addLineStart(Circle start) {

        for (int i = 0; i < nodes.size(); i++) {
            if(start.getCenterX() == nodes.get(i).Xpos &&
                    start.getCenterY() == nodes.get(i).Ypos){

                Line line = new Line();

                line.setStartX(start.getCenterX());
                line.setStartY(start.getCenterY());

                lines.add(line);
            }
        }

    }

    //This method is used to create an edge in both graph class and visual graphics
    public void addLineEnd(Circle end){


            lines.get(lines.size() - 1).setEndX(end.getCenterX());
            lines.get(lines.size() - 1).setEndY(end.getCenterY());

            System.out.println("LINE START " + lines.get(lines.size() - 1).getStartX() + "  " + lines.get(lines.size() - 1).getStartY());
            System.out.println("LINE END " + lines.get(lines.size() - 1).getEndX() + "  " + lines.get(lines.size() - 1).getEndY());

            System.out.println(this.edges.size());

            addEdge(getNodeAtCoord(lines.get(lines.size() - 1).getStartX(), lines.get(lines.size() - 1).getStartY()),
                    getNodeAtCoord(lines.get(lines.size() - 1).getEndX(), lines.get(lines.size() - 1).getEndY()));

    }

    public Node getNodeAtCoord(double x, double y){
        for (int i = 0; i < this.nodes.size()-1; i++) {
            if(this.nodes.get(i).Xpos == x && this.nodes.get(i).Ypos == y){
                return this.nodes.get(i);
            }
        }

        return null;
    }


}
