package com.mindmap.jane.domain;

import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Example. Template {{przykłady}}
 */
public class Example implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("sentence")
    private Sentence sentence;

    public Example(Sentence sentence) {
        this.sentence = sentence;
    }

    public Example() {
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public Sentence getSentence() {
        return sentence;
    }

    public Example sentence(Sentence value) {
        this.sentence = value;
        return this;
    }

    public void setSentence(Sentence sentence) {
        this.sentence = sentence;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Example example = (Example) o;
        return Objects.equals(sentence, example.sentence);
    }

    @Override
    public int hashCode() {

        return Objects.hash(sentence);
    }

    @Override
    public String toString() {
        return "Example{" +
            "sentence=" + sentence +
            '}';
    }
}
