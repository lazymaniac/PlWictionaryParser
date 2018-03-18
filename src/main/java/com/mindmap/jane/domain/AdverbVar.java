package com.mindmap.jane.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AdverbVar.
 */
@Document(collection = "adverb_var")
public class AdverbVar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @Field("value")
    private String value;

    @Field("higherDegree")
    private String higherDegree;

    @Field("highestDegree")
    private String highestDegree;

    public AdverbVar(String value, String higherDegree, String highestDegree) {
        this.value = value;
        this.higherDegree = higherDegree;
        this.highestDegree = highestDegree;
    }

    public AdverbVar() {
    }
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AdverbVar adverbVar = (AdverbVar) o;
        if (adverbVar.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, adverbVar.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AdverbVar{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", higherDegree='" + getHigherDegree() + "'" +
            ", highestDegree='" + getHighestDegree() + "'" +
            '}';
    }
}
