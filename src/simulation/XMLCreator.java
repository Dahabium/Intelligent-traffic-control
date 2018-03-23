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

            for (int i = 0; i < graph.nodes.size(); i++) {

                // Vertex elements
                Element node = doc.createElement("Node");
                rootElement.appendChild(node);

                //set index as attribute to node element
                node.setAttribute("id", (String.valueOf(i)));

                //add a position element
                Element position = doc.createElement("Position");
                String positionString = String.valueOf(graph.nodes.get(i).Xpos) + String.valueOf(graph.nodes.get(i).Ypos);
                position.appendChild(doc.createTextNode(positionString));
                node.appendChild(position);

                //Edges group
                Element edges = doc.createElement("Edges");
                node.appendChild(edges);

                // edges elements
                for (int j = 0; j < graph.getAdjecents(graph.nodes.get(i)).size(); j++) {

                    Element edge = doc.createElement("Edge");
                    edge.appendChild(doc.createTextNode(graph.getAdjecents(graph.nodes.get(i)).get(j).Xpos +","+
                            graph.getAdjecents(graph.nodes.get(i)).get(j).Ypos ));
                    edges.appendChild(edge);

                }

            }


            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("file.xml"));

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
