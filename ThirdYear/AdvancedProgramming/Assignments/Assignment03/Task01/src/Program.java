import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Program {
    static void main() {
//        new Program();


        boolean [] [] array = new boolean[][]{{true, true}, {true, false}, {false, false}};

        System.out.println(Arrays.toString(calcANDrow(array)));
    }


    static boolean [] calcANDrow(boolean [][] array) {

        boolean [] result = new boolean[array.length];

        for (int i = 0; i < array.length; i++) {
            boolean rows = true;

            for (int j = 0; j < array[i].length; j++) {
                rows &= array[i][j];
            }
            result[i] = rows;
        }


        return result;
    }




















    public Program() {
        try {
            Document document = loadXML();
            NodeList personalist = document.getElementsByTagName("PERSONA");
            askPersona(personalist, document);
        } catch (Exception e) {
            System.out.println("Error initializing program: " + e.getMessage());
        }
    }

    Document loadXML() throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(new File("./Task01/Resources/hamlet.xml"));
        document.getDocumentElement().normalize();
        return document;
    }

    Document createPersonaDocument(String personaName, Document originalDoc)
            throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document newDoc = builder.newDocument();

        Element root = newDoc.createElement("lines");
        root.setAttribute("speaker", personaName);
        newDoc.appendChild(root);

        int speechCounter = 1;

        NodeList acts = originalDoc.getElementsByTagName("ACT");
        for (int i = 0; i < acts.getLength(); i++) {
            Element actElement = (Element) acts.item(i);
            String actTitle = actElement.getElementsByTagName("TITLE").item(0).getTextContent();

            Element actNode = newDoc.createElement("act");
            actNode.setAttribute("title", actTitle);
            root.appendChild(actNode);

            NodeList scenes = actElement.getElementsByTagName("SCENE");
            for (int j = 0; j < scenes.getLength(); j++) {
                Element sceneElement = (Element) scenes.item(j);
                String sceneTitle = sceneElement.getElementsByTagName("TITLE").item(0).getTextContent();

                Element sceneNode = newDoc.createElement("scene");
                sceneNode.setAttribute("title", sceneTitle);
                actNode.appendChild(sceneNode);

                NodeList speeches = sceneElement.getElementsByTagName("SPEECH");
                for (int k = 0; k < speeches.getLength(); k++) {
                    Element speechElement = (Element) speeches.item(k);
                    String speaker = speechElement.getElementsByTagName("SPEAKER")
                            .item(0).getTextContent().trim();

                    if (speaker.equals(personaName)) {
                        Element newSpeech = newDoc.createElement("speech");
                        newSpeech.setAttribute("id", String.valueOf(speechCounter++));
                        sceneNode.appendChild(newSpeech);

                        NodeList lines = speechElement.getElementsByTagName("LINE");
                        for (int l = 0; l < lines.getLength(); l++) {
                            String lineText = lines.item(l).getTextContent();
                            Element lineNode = newDoc.createElement("line");
                            lineNode.setTextContent(lineText);
                            newSpeech.appendChild(lineNode);
                        }
                    }
                }
            }
        }
        return newDoc;
    }

    void saveDocument(Document doc, String filename) throws TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(doc), new StreamResult(new File(filename)));
    }


    void askPersona(NodeList personalist, Document originalDoc) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the persona (e.g. Hamlet, Ophelia): ");
        String persona = scanner.nextLine().trim();

        boolean verdict = false;

        for (int i = 0; i < personalist.getLength(); i++) {
            Node node = personalist.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String rawText = element.getTextContent().trim();

                // Take only the part before the comma
                String nameOnly = rawText.contains(",")
                        ? rawText.substring(0, rawText.indexOf(",")).trim()
                        : rawText;

                if (nameOnly.equals(persona)) { // case-sensitive match
                    verdict = true;
                    break;
                }
            }
        }

        if (verdict) {
            System.out.println("Valid persona: " + persona);
            try {
                Document personaDoc = createPersonaDocument(persona, originalDoc);
                saveDocument(personaDoc, persona.toLowerCase() + ".xml");
                System.out.println("Saved speeches to " + persona.toLowerCase() + ".xml");
            } catch (Exception e) {
                System.out.println("Error creating persona XML: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid persona: " + persona);
        }
    }

//    NodeList loadXML() {
//
//        NodeList personalist = null;
//        try{
//            //create DBF and DB
//            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//
//            //Get the actual xml document from the Resources folder
//            Document document = dBuilder.parse(".//Task01/Resources/hamlet.xml");
//
//            document.getDocumentElement().normalize(); // normalize text representation
//
////            System.out.println("Root element: " + document.getDocumentElement().getNodeName());
//
//            personalist = document.getElementsByTagName("PERSONA");
//
////            System.out.println("Printing all the personas");
////            for (int i = 0; i < personalist.getLength(); i++) {
////                Node node = personalist.item(i);
////
////                if(node.getNodeType() == Node.ELEMENT_NODE){
////                    Element element = (Element) node;
////
////                    System.out.println(element.getTextContent());
////
////                }
////            }
//
//
////            for (int i = 0; i < nodelist.getLength(); i++) {
////                Node node = nodelist.item(i);
////
////                //This for some reason returns the entire play
////               if(node.getNodeType() == Node.ELEMENT_NODE){
////                   Element element = (Element) node;
////                   System.out.println("Element Name: " + element.getNodeName());
////                   System.out.println("Element Value: " + element.getTextContent());
////
////                   //for debugging
//////                   if(element.toString().equalsIgnoreCase("Persona")){
//////                       System.out.println(element.getNodeName());
//////                   }
////               }
////            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//
//
//        return personalist;
//
//    }
}
