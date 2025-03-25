package com;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//Letra F
public class Sax extends DefaultHandler {

    Map<Integer, String> filmsMap = new HashMap<>();
    Map<Integer, Integer> categoryFilmMap = new HashMap<>();
    Map<Integer, String> categoryNameMap = new HashMap<>();
    Integer filmId = null;
    Integer categoryId = null;
    String filmName = null;
    String categoryName = null;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(qName.equals("film")) {
            filmId = Integer.parseInt(attributes.getValue("id"));
            filmName = attributes.getValue("title");
            filmsMap.put(filmId, filmName);
        }
        else if(qName.equals("film_category")) {
            filmId = Integer.parseInt(attributes.getValue("film_id"));
            categoryId = Integer.parseInt(attributes.getValue("category_id"));
            categoryFilmMap.put(filmId, categoryId);
        }
        else if(qName.equals("category")) {
            categoryId = Integer.parseInt(attributes.getValue("id"));
            categoryName = attributes.getValue("name");
            categoryNameMap.put(categoryId, categoryName);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
    }

    public void writeHtml(String fileName) throws FileNotFoundException {

        try {

            XMLOutputFactory factory = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = factory.createXMLStreamWriter(new FileOutputStream(fileName), "UTF-8");

            writer.writeStartDocument("UTF-8", "1.0");

            writer.writeStartElement("table");
            writer.writeAttribute("border", "1");

            writer.writeStartElement("tr");

            writer.writeStartElement("th");
            writer.writeCharacters("Filme");
            writer.writeEndElement();

            writer.writeStartElement("th");
            writer.writeCharacters("Categoria");
            writer.writeEndElement();

            writer.writeEndElement();

            for(Map.Entry<Integer, String> films : filmsMap.entrySet()) {

                writer.writeStartElement("tr");

                writer.writeStartElement("td");
                writer.writeCharacters(films.getValue());
                writer.writeEndElement();

               var film_id = categoryFilmMap.get(films.getKey());
               var categoryName = categoryNameMap.get(film_id);

                writer.writeStartElement("td");
                writer.writeCharacters(categoryName);
                writer.writeEndElement();

                writer.writeEndElement();

            }

            writer.writeEndElement();
            writer.writeEndDocument();

            writer.flush();
            writer.close();
            System.out.println("Arquivo HTML gerado com sucesso!");

        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        Sax sax = new Sax();
        saxParser.parse(new File("src/main/files/film.xml"), sax);
        saxParser.parse(new File("src/main/files/film_category.xml"), sax);
        saxParser.parse(new File("src/main/files/category.xml"), sax);
        sax.writeHtml("LetraF.html");
    }
}
