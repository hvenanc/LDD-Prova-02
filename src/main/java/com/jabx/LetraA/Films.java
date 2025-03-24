package com.jabx.LetraA;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.File;
import java.util.List;

@XmlRootElement(name = "films")
@XmlAccessorType(XmlAccessType.FIELD)
public class Films {

    private List<Film> film;

    public Films() {
    }

    public List<Film> getFilm() {
        return film;
    }

    public void setFilm(List<Film> film) {
        this.film = film;
    }

    @Override
    public String toString() {
        return "Films{" +
                "film=" + film +
                '}';
    }

    public static void main(String[] args) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Films.class);
        Unmarshaller um = context.createUnmarshaller();
        Films f = (Films) um.unmarshal(new File("src/main/files/film.xml"));
        System.out.println(f);
    }
}
