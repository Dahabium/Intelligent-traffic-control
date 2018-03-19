package simulation;
import org.w3c.dom.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.sun.org.apache.xml.internal.serialize.*;
import javax.xml.parsers.*;
import org.xml.sax.*;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class GraphLoader {

    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;
    private Graph graph;
    private String fileName;
    private ArrayList<int[]> edgeList = new ArrayList<>();
    public GraphLoader(String fileName)
    {
        this.fileName = fileName;

        try{
            load();
        }
        catch(ParserConfigurationException e){
            e.printStackTrace();
        }
        catch(SAXException e){

        }
        catch(IOException e){

        }



    }
    public void load() throws ParserConfigurationException, SAXException, IOException
    {
        graph = new Graph();
        File fxmlFile = new File("/Users/Rik/Documents/GitHub/Intelligent-traffic-control/" + fileName + ".xml");
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = builderFactory.newDocumentBuilder();
        Document document = dBuilder.parse(fxmlFile);
        document.normalize();

        NodeList rootNodes = document.getElementsByTagName("Node");
        for(int i = 0; i< rootNodes.getLength(); i++)
        {

            String temp = rootNodes.item(i).getTextContent();
            System.out.println(temp);
            List<String> stringList = Arrays.asList(temp.split(" "));
            String id = " ";
            double x = 0;
            double y = 0;


            id = stringList.get(0);
            x = parseDouble(stringList.get(1));
            y = parseDouble(stringList.get(2));
            System.out.println("id, x, y: " + id + ", " + x + ", " + y);

            Node tempNode = new Node(id, (int)x, (int)y);
            //nodes.add(tempNode);
            graph.addNode(tempNode);

                for (int j = 3; j < stringList.size() - 1; j += 2) {
                    int[] edgeCoords = new int[2];
                    System.out.println("blabla"+ stringList.get(j) + stringList.get(j+1));
                    edgeCoords[0] = parseInt(stringList.get(j));
                    edgeCoords[1] = parseInt(stringList.get(j + 1));
                    edgeList.add(edgeCoords);
                }

//            Node tempNode = new Node();
//            nodes.add(tempNode);
//            graph.addNode(tempNode);
//            Edges also need to be imported, IDK yet if theyre imported as one list or per object
        }

        for(int i = 0; i<edgeList.size(); i++) {
            Node start = null;
            Node end = null;
            for (int j = 0; j < graph.nodes.size(); j++) {
                if (edgeList.get(i)[0] == parseInt(graph.nodes.get(j).name));
                {
                    start = graph.nodes.get(j);
                }
                if(edgeList.get(i)[1] == parseInt(graph.nodes.get(j).name))
                {
                    end = graph.nodes.get(j);
                }

            }


            graph.addEdge(start, end);

        }

        create();

    }

    private void create()
    {


    }
}
