package com;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Stax {

    private final XMLStreamReader reader;

    public Stax(String path) throws FileNotFoundException, XMLStreamException {
        File xml = new File(path);
        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        reader = xmlInputFactory.createXMLStreamReader(new FileReader(xml));
    }
}
