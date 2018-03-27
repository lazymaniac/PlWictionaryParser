package com.mindmap.jane.domain;

import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Phraseology.
 */
public class Phraseology implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("link")
    private Link link;

    public Phraseology(Link link) {
        this.link = link;
    }

    public Phraseology() {
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phraseology that = (Phraseology) o;
        return Objects.equals(link, that.link);
    }

    @Override
    public int hashCode() {

        return Objects.hash(link);
    }

    @Override
    public String toString() {
        return "Phraseology{" +
            "link=" + link +
            '}';
    }
}
