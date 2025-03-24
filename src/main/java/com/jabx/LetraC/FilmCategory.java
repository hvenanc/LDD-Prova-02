package com.jabx.LetraC;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class FilmCategory {

    @XmlAttribute(name = "film_id")
    private Integer film_id;
    @XmlAttribute(name = "category_id")
    private Integer category_id;

    public FilmCategory() {
    }

    public Integer getFilm_id() {
        return film_id;
    }

    public void setFilm_id(Integer film_id) {
        this.film_id = film_id;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    @Override
    public String toString() {
        return "FilmCategory{" +
                "film_id=" + film_id +
                ", category_id=" + category_id +
                '}';
    }
}
