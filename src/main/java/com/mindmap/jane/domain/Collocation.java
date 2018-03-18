package com.mindmap.jane.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Collocation. Template {{kolokacje}}
 */
@Document(collection = "collocation")
public class Collocation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    private Sentence sentence;

    public Collocation(Sentence sentence) {
        this.sentence = sentence;
    }

    public Collocation() {
    }


    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Sentence getSentence() {
        return sentence;
    }

    public void setSentence(Sentence sentence) {
        this.sentence = sentence;
    }

    public Collocation sentence(Sentence sentence) {
        this.sentence = sentence;
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
        Collocation collocation = (Collocation) o;
        if (collocation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), collocation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Collocation{" +
            "id=" + getId() +
            ", sentence='" + getSentence() + "'" +
            "}";
    }
}
