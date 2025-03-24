package com;

import javax.xml.stream.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

//Letra E
public class Stax {

    private final XMLStreamReader reader;
    private final List<String> categoryIdList = new ArrayList<>();
    private final Map<Long, String> categoryMap = new HashMap<>();

    public Map<Long, String> getCategoryMap() {
        return categoryMap;
    }

    public Stax(String path) throws FileNotFoundException, XMLStreamException {
        File xml = new File(path);
        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        reader = xmlInputFactory.createXMLStreamReader(new FileReader(xml));
    }

    public void countCategoryFilms() throws XMLStreamException {

        while(reader.hasNext()) {
            int event = reader.next();
            if(event == XMLStreamConstants.START_ELEMENT) {
                if(reader.getLocalName().equals("film_category")) {
                    String categoryId = reader.getAttributeValue(null, "category_id");
                    categoryIdList.add(categoryId);
                }
            }
        }
        reader.close();
    }

    public void searchCategoryName() throws XMLStreamException {
        while(reader.hasNext()) {
            int event = reader.next();
            if(event == XMLStreamConstants.START_ELEMENT) {
                if(reader.getLocalName().equals("category")) {
                    Long categoryId = Long.valueOf(reader.getAttributeValue(null, "id"));
                    String categoryName = reader.getAttributeValue(null, "name");
                    categoryMap.putIfAbsent(categoryId, categoryName);
                }
            }
        }
        reader.close();
    }

    public Map<String, Long> groupByCategoryId() {

        return categoryIdList.stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

    }

    public void writeHtml(String fileName, Map<Long, String> categories) {

        try {

            XMLOutputFactory factory = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = factory.createXMLStreamWriter(new FileOutputStream(fileName), "UTF-8");

            writer.writeStartDocument("UTF-8", "1.0");

            writer.writeStartElement("table");
            writer.writeAttribute("border", "1");

            writer.writeStartElement("tr");

            writer.writeStartElement("th");
            writer.writeCharacters("Categoria");
            writer.writeEndElement();

            writer.writeStartElement("th");
            writer.writeCharacters("Quantidade");
            writer.writeEndElement();

            writer.writeEndElement();

            Map<String, Long> data = groupByCategoryId();
            List<Map.Entry<String, Long>> sortedEntries = new ArrayList<>(data.entrySet());
            sortedEntries.sort(Comparator.comparingInt(entry -> Integer.parseInt(entry.getKey())));


            for (Map.Entry<String, Long> entry : sortedEntries) {

                writer.writeStartElement("tr");
                writer.writeStartElement("td");
                var id = Long.valueOf(entry.getKey());
                writer.writeCharacters(categories.get(id));
                writer.writeEndElement();

                writer.writeStartElement("td");
                writer.writeCharacters(entry.getValue().toString());
                writer.writeEndElement();

                writer.writeEndElement();
            }

            writer.writeEndElement();
            writer.writeEndDocument();

            writer.flush();
            writer.close();

            System.out.println("Arquivo HTML gerado com sucesso!");

        } catch (XMLStreamException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws XMLStreamException, FileNotFoundException {

        Stax filmCategory = new Stax("src/main/files/film_category.xml");
        Stax categories = new Stax("src/main/files/category.xml");
        filmCategory.countCategoryFilms();
        categories.searchCategoryName();
        filmCategory.writeHtml("LetraE.html", categories.getCategoryMap());

    }
}
