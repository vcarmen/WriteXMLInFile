import java.util.stream.Stream;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello Program!");
        Integer numRecords = 500;
        Integer numOfRecordsPerFile = 100;
        Integer firstElement = 1;
        Integer lastElement = numOfRecordsPerFile;

        /*
         * for (int i = numOfRecordsPerFile; i <= numRecords; i = i +
         * numOfRecordsPerFile) { CreateXMLData(numOfRecordsPerFile, index); index = i +
         * 1; }
         */

        while (lastElement <= numRecords) {
            CreateXMLData(firstElement, lastElement);
            firstElement = lastElement + 1;
            lastElement = lastElement + numOfRecordsPerFile;
        }
        if (firstElement <= numRecords) {
            CreateXMLData(firstElement, numRecords);
        }

    }

    public static void CreateXMLData(int index, int numRecords) {
        String fileName = "QUALITY." + index + ".xml";
        System.out.println(fileName + " - Records starting from: " + index + " to " + numRecords);

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // quality elements
            Element rootElement = doc.createElement("Quality_Set");
            doc.appendChild(rootElement);

            // Quality_Record
            while (index <= numRecords) {

                Element quality = doc.createElement("Quality_Record");

                Element quality_id = doc.createElement("Quality_ID");
                quality_id.setTextContent("QUALITY00" + index);

                Element state_product = doc.createElement("State_Product");
                state_product.setTextContent("Hand" + index);

                Element range_product = doc.createElement("Range_Product");
                range_product.setTextContent("High" + index);

                quality.appendChild(quality_id);
                quality.appendChild(state_product);
                quality.appendChild(range_product);

                rootElement.appendChild(quality);

                index++;
            }

            // Write XML content in file
            WriteXMLData(doc, fileName);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void WriteXMLData(Document doc, String fileName) {
        try {
            // Write XML content in file
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(source, result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}