package com.mindmap.jane.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Synonym.
 */
@Document(collection = "synonym")
public class Synonym implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @Field("link")
    private Link link;

    public Synonym(Link link) {
        this.link = link;
    }

    public Synonym() {
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

    public Synonym value(Link value) {
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
        Synonym synonym = (Synonym) o;
        if (synonym.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), synonym.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Synonym{" +
            "id=" + getId() +
            ", link='" + getLink() + "'" +
            "}";
    }
}
