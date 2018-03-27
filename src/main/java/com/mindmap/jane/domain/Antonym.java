package com.mindmap.jane.domain;

import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Antonym. Template {{antonimy}}
 */
public class Antonym implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("link")
    private Link link;

    public Antonym(Link link) {
        this.link = link;
    }

    public Antonym() {
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Antonym antonym = (Antonym) o;
        return Objects.equals(link, antonym.link);
    }

    @Override
    public int hashCode() {

        return Objects.hash(link);
    }

    @Override
    public String toString() {
        return "Antonym{" +
            "link=" + link +
            '}';
    }
}
