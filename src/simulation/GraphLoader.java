package simulation;
import org.w3c.dom.*;
import java.io.*;
import java.util.ArrayList;

import com.sun.org.apache.xml.internal.serialize.*;
import javax.xml.parsers.*;
import org.xml.sax.*;

public class GraphLoader {

    private ArrayList<Node> nodes, edges;
    private Graph graph;

    public GraphLoader()
    {


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
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbuilder = builderFactory.newDocumentBuilder();
        Document document = dbuilder.parse(XMLReader.class.getResourceAsStream("/Graph1.xml"));
        document.normalize();

        NodeList rootNodes = document.getElementsByTagName("Node");
        for(int i = 0; i< rootNodes.getLength(); i++)
        {

            String temp = rootNodes.item(i).getTextContent();
            System.out.println(temp);
            String x;
            String y;
//            Node tempNode = new Node();
//            nodes.add(tempNode);
//            graph.addNode(tempNode);
//            Edges also need to be imported, IDK yet if theyre imported as one list or per object
        }





    }
}
