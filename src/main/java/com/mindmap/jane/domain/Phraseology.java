package com.mindmap.jane.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Phraseology.
 */
@Document(collection = "phraseology")
public class Phraseology implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    private Link link;

    public Phraseology(Link link) {
        this.link = link;
    }

    public Phraseology() {
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

    public Phraseology value(Link value) {
        this.link = value;
        return this;
    }

    public void setLink(Link link) {
        this.link = link;
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
        Phraseology phraseology = (Phraseology) o;
        if (phraseology.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), phraseology.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Phraseology{" +
            "id=" + getId() +
            ", link='" + getLink() + "'" +
            "}";
    }
}
