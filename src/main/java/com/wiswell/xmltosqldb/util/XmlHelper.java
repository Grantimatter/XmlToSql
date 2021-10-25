package com.wiswell.xmltosqldb.util;

import com.wiswell.xmltosqldb.model.KeyValue;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlHelper {

    /**
     * Get all key value pairs from given xml document
     * @param filepath the path to the xml document
     * @return a list of all key value pairs within the xml document
     */
    public static List<KeyValue> getKeyValuePairs(String filepath) {
        List<KeyValue> keyValuePairs = new ArrayList<>();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try {
            documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(filepath);

            document.getDocumentElement().normalize();

            Element rootElement = document.getDocumentElement();

            NodeList allNodes = rootElement.getChildNodes();

            for (int i = 0; i < allNodes.getLength(); i++) {

                if(allNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) allNodes.item(i);

                    getElementChildren(element, keyValuePairs);

                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return keyValuePairs;
    }

    /**
     * Get all children from element passed in recursively
     * @param element The element to receive all children from
     * @return a list of all children that are of the 'Element' type of the parent Element
     */
    private static void getElementChildren(Element element, List<KeyValue> keyValueList) {

        List<Element> childElements = getElementNodesFromList(element.getChildNodes());

            if (!childElements.isEmpty()) {
                for(Element childElement:childElements) {
                    getElementChildren(childElement, keyValueList);
                }
            } else {
                String key = element.getNodeName();
                String value = element.getTextContent();

                // Only add pair to list if they both exist
                if(key.length() > 0 && value.length() > 0)
                {
                    KeyValue newKeyValue = new KeyValue(key, value);
                    keyValueList.add(newKeyValue);
                }
            }
    }

    /**
     * Takes in a NodeList and finds all nodes of the 'element' type
     * @param nodeList the NodeList to search for elements
     * @return a list of all elements within a NodeList
     */
    private static List<Element> getElementNodesFromList(NodeList nodeList)
    {
        List<Element> elementList = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            if(nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                elementList.add((Element) nodeList.item(i));
            }
        }

        return elementList;
    }
}
