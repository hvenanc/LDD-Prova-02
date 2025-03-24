package com.jabx.LetraC;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.File;
import java.util.List;

@XmlRootElement(name = "film_categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class FilmCategories {


    private List<FilmCategory> categories;

    public FilmCategories() {
    }

    public List<FilmCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<FilmCategory> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "FilmCategories{" +
                "categories=" + categories +
                '}';
    }

    public static void main(String[] args) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(FilmCategories.class);
        Unmarshaller um = context.createUnmarshaller();
        FilmCategories fc = (FilmCategories) um.unmarshal(new File("src/main/files/film_category.xml"));
        System.out.println(fc);
    }
}
