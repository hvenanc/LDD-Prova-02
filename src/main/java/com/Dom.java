package com;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dom {

    private Document doc;

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
}
