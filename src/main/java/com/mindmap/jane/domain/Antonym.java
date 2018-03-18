package com.mindmap.jane.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Antonym. Template {{antonimy}}
 */
@Document(collection = "antonym")
public class Antonym implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @Field("link")
    private Link link;

    public Antonym(Link link) {
        this.link = link;
    }

    public Antonym() {
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public Antonym link(Link link) {
        this.link = link;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Antonym antonym = (Antonym) o;
        if (antonym.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, antonym.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Antonym{" +
            "id=" + id +
            ", value='" + link + "'" +
            '}';
    }
}
