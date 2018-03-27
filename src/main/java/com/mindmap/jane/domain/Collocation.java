package com.mindmap.jane.domain;

import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Collocation. Template {{kolokacje}}
 */
public class Collocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("sentence")
    private Sentence sentence;

    public Collocation(Sentence sentence) {
        this.sentence = sentence;
    }

    public Collocation() {
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Collocation that = (Collocation) o;
        return Objects.equals(sentence, that.sentence);
    }

    @Override
    public int hashCode() {

        return Objects.hash(sentence);
    }

    @Override
    public String toString() {
        return "Collocation{" +
            "sentence=" + sentence +
            '}';
    }
}
