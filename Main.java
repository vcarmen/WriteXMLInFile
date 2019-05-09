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
        Integer numRecords = 100;
        CreateXMLData(numRecords);
    }

    public static void CreateXMLData(int numRecords) {
        System.out.println("It will create a xml object and write in file");
        String fileName = "QUALITY." + numRecords + ".xml";
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // quality elements
            Element rootElement = doc.createElement("Quality_Set");
            doc.appendChild(rootElement);

            // Quality_Record
            for (int i = 1; i <= numRecords; i++) {

                Element quality = doc.createElement("Quality_Record");

                Element quality_id = doc.createElement("Quality_ID");
                quality_id.setTextContent("QUALITY00" + i);

                Element state_product = doc.createElement("State_Product");
                state_product.setTextContent("Hand" + i);

                Element range_product = doc.createElement("Range_Product");
                range_product.setTextContent("High" + i);

                quality.appendChild(quality_id);
                quality.appendChild(state_product);
                quality.appendChild(range_product);

                rootElement.appendChild(quality);
            }

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