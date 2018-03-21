package simulation;

import org.w3c.dom.*;

import java.io.*;
import java.util.*;

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
    private ArrayList<String[]> edgeList = new ArrayList<>();

    public GraphLoader(String fileName) {
        this.fileName = fileName;

        try {
            load();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {

        } catch (IOException e) {

        }

    }


    public void load() throws ParserConfigurationException, SAXException, IOException {

        graph = new Graph();
        File fxmlFile = new File(fileName + ".xml");
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = builderFactory.newDocumentBuilder();
        Document document = dBuilder.parse(fxmlFile);
        document.normalize();

        NodeList rootNodes = document.getElementsByTagName("Node");

        for (int i = 0; i < rootNodes.getLength(); i++) {

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

            Node tempNode = new Node(id, (int) x, (int) y);
            //nodes.add(tempNode);
            graph.addNode(tempNode);
            ArrayList<String> stLi = new ArrayList<>();
            for(int j = 0; j<stringList.size(); j++)
            {
                if(!stringList.get(j).isEmpty()) {
                    stLi.add(stringList.get(j));
                }
            }

            for(int j = 3; j<stLi.size()-1; j+=2)
            {
                System.out.println(stLi.get(j) + " blabla " + stLi.get(j+1));
                String[] startend = new String[2];

                startend[0] = stLi.get(j);
                startend[1] = stLi.get(j+1);

                edgeList.add(startend);


            }

//            Node tempNode = new Node();
//            nodes.add(tempNode);
//            graph.addNode(tempNode);
//            Edges also need to be imported, IDK yet if theyre imported as one list or per object
        }

        for (int i = 0; i < edgeList.size(); i++) {
            Node start = null;
            Node end = null;

            for (int j = 0; j < graph.nodes.size(); j++) {

                System.out.println(graph.nodes.get(j).name);

                if (Objects.equals(edgeList.get(i)[0], graph.nodes.get(j).name)){

                    start = graph.nodes.get(j);
                    System.out.println(edgeList.get(i)[0] + ", " + edgeList.get(i)[1]);
                    System.out.println("Start found");
                }
                if (Objects.equals(edgeList.get(i)[1], graph.nodes.get(j).name)) {
                    end = graph.nodes.get(j);
                    System.out.println("End found");
                }

            }

            System.out.println("Edge registered");
            graph.addEdge(start, end);

        }

    }



    public Graph getGraph() {
        for (int i = 0; i < graph.nodes.size(); i++) {
            System.out.println(graph.nodes.get(i).name + " = id");
        }

        for (int i = 0; i < graph.edges.size(); i++) {
            System.out.println(graph.edges.get(i).start + " " + graph.edges.get(i).end);
        }
        return graph;
    }

}
