package io.github.tnewman.xmltojson;

import io.github.tnewman.xmltojson.mapping.AttributeMapping;
import io.github.tnewman.xmltojson.mapping.ListMapping;
import io.github.tnewman.xmltojson.record.Attribute;
import io.github.tnewman.xmltojson.record.Record;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class XMLLoader {

    private DocumentBuilder documentBuilder;

    public XMLLoader() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try {
            this.documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new XMLToJSONException(e);
        }
    }

    public List<Record> loadAttributesFromXML(ListMapping listMapping, Path xmlFile) {
        try {
            Document doc = documentBuilder.parse(xmlFile.toFile());

            NodeList recordsNodeList = doc.getElementsByTagName(listMapping.getListElementAttributeName());

            List<Record> records = new ArrayList<>();

            for(int i = 0; i < recordsNodeList.getLength(); i++) {
                Element recordNode = (Element) recordsNodeList.item(0);
                List<Attribute> attributes = new ArrayList<>();

                for (AttributeMapping attributeMapping : listMapping.getAttributeMappings()) {
                    String itemString = recordNode.getElementsByTagName(attributeMapping.getSourceName()).item(0)
                            .getTextContent();

                    Object attributeValue;

                    switch (attributeMapping.getSourceType()) {
                        case INTEGER:
                            attributeValue = Integer.parseInt(itemString);
                            break;
                        case GENDER_CHARACTER:
                            attributeValue = itemString.toLowerCase().charAt(0);
                            break;
                        case STATE:
                        case STRING:
                            attributeValue = itemString;
                            break;
                        case DATE:
                            attributeValue = new SimpleDateFormat("mm/dd/yyyy").parse(itemString);
                            break;
                        default:
                            throw new XMLToJSONException("XML contains unparsable attribute: "
                                    + attributeMapping.getSourceType().name());
                    }

                    attributes.add(new Attribute(attributeMapping.getSourceName(), attributeValue));
                }

                records.add(new Record(attributes));
            }

            return records;
        } catch (IOException | ParseException | SAXException e) {
            throw new XMLToJSONException(e);
        }
    }
}
