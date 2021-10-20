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

    public static List<KeyValue> getKeyValuePairs(String filepath) {
        List<KeyValue> keyValuePairs = new ArrayList();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try {
            documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(filepath);

            document.getDocumentElement().normalize();

            //System.out.println("Root Element: " + document.getDocumentElement().getNodeName());
            Element RootElement = document.getDocumentElement();

            //NodeList list = document.getElementsByTagName("paths");
            NodeList allNodes = RootElement.getChildNodes();

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
     * @return a list of all children of the parent element
     */
    private static void getElementChildren(Element element, List<KeyValue> keyValueList) {
        //List<KeyValue> keyValuePairList = new ArrayList();

        List<Element> childElements = getElementNodesFromList(element.getChildNodes());

            if (!childElements.isEmpty()) {
                for(Element childElement:childElements) {
                    //List<Element> grandChildElements = getElementNodesFromList(element.getChildNodes());
                    getElementChildren(childElement, keyValueList);
                }
                //System.out.println(String.format("%s has child: %s", element.getNodeName(), childElement.getNodeName()));
            } else {
                String key = element.getNodeName();
                String value = element.getTextContent();

                KeyValue newKeyValue = new KeyValue(key, value);

                keyValueList.add(newKeyValue);
            }
    }

    private static List<Element> getElementNodesFromList(NodeList nodeList)
    {
        List<Element> elementList = new ArrayList();

        for (int i = 0; i < nodeList.getLength(); i++) {
            if(nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                elementList.add((Element) nodeList.item(i));
            }
        }

        return elementList;
    }
}
