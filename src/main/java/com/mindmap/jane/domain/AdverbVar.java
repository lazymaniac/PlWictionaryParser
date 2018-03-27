package com.mindmap.jane.domain;

import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AdverbVar.
 */
public class AdverbVar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("value")
    private String value;

    @Field("higher_degree")
    private String higherDegree;

    @Field("highest_degree")
    private String highestDegree;

    public AdverbVar(String value, String higherDegree, String highestDegree) {
        this.value = value;
        this.higherDegree = higherDegree;
        this.highestDegree = highestDegree;
    }

    public AdverbVar() {
    }
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public String getValue() {
        return value;
    }

    public AdverbVar value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getHigherDegree() {
        return higherDegree;
    }

    public AdverbVar higherDegree(String higherDegree) {
        this.higherDegree = higherDegree;
        return this;
    }

    public void setHigherDegree(String higherDegree) {
        this.higherDegree = higherDegree;
    }

    public String getHighestDegree() {
        return highestDegree;
    }

    public AdverbVar highestDegree(String highestDegree) {
        this.highestDegree = highestDegree;
        return this;
    }

    public void setHighestDegree(String highestDegree) {
        this.highestDegree = highestDegree;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdverbVar adverbVar = (AdverbVar) o;
        return Objects.equals(value, adverbVar.value) &&
            Objects.equals(higherDegree, adverbVar.higherDegree) &&
            Objects.equals(highestDegree, adverbVar.highestDegree);
    }

    @Override
    public int hashCode() {

        return Objects.hash(value, higherDegree, highestDegree);
    }

    @Override
    public String toString() {
        return "AdverbVar{" +
            "value='" + value + '\'' +
            ", higherDegree='" + higherDegree + '\'' +
            ", highestDegree='" + highestDegree + '\'' +
            '}';
    }
}
