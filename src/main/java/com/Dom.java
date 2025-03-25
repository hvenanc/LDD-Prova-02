package com;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

//Letra D
public class Dom {

    private Document doc;
    private final Map<Integer, String> categoryMap = new HashMap<>();

    public Dom(String path) {

        File xml = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(xml);
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(Dom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void searchData() {
        NodeList category = doc.getElementsByTagName("category");
        for (int i = 0; i < category.getLength(); i++) {
            Element categoryItem = (Element) category.item(i);
             Integer id = Integer.valueOf(categoryItem.getAttribute("id"));
             String name = categoryItem.getAttribute("name");
             categoryMap.putIfAbsent(id, name);
        }
    }

    public void writeHtml(String fileName) {

        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.newDocument();

            Element olElement = document.createElement("ol");
            document.appendChild(olElement);

            for(Map.Entry<Integer, String> entry : categoryMap.entrySet()) {

                Element liElement = document.createElement("li");
                liElement.setAttribute("id", entry.getKey().toString());
                liElement.setTextContent(entry.getValue());
                olElement.appendChild(liElement);

            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(fileName));

            transformer.transform(source, result);

            System.out.println("Arquivo HTML gerado com sucesso!");


        } catch (ParserConfigurationException | TransformerException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {

        Dom dom = new Dom("src/main/files/category.xml");
        dom.searchData();
        dom.writeHtml("LetraD.html");
    }
}
