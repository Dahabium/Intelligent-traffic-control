package simulation;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XMLCreator {

    public XMLCreator(){}

    public void createXML(Graph graph){

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Graph");
            doc.appendChild(rootElement);

            for (int i = 0; i < graph.nodes.size()-1; i++) {

                // Vertex elements
                Element node = doc.createElement("Node");
                node.appendChild(doc.createTextNode("Node number " + Integer.toString(i)));

                // set attribute to Vertex element
//                node.setAttribute("node id", Integer.toString(i) );



                // edges elements
                for (int j = 0; j < graph.nodes.get(i).connections.size()-1; j++) {

                    Element edge = doc.createElement("edges");
                    edge.appendChild(doc.createTextNode("Start " + graph.nodes.get(i).connections.get(j).start.name + " end " +
                            graph.nodes.get(i).connections.get(j).end.name));

                    node.appendChild(edge);

                }

                rootElement.appendChild(node);
            }


            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("createdGraph.xml"));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }

    }
}
