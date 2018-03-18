package com.mindmap.jane.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the AdverbVar entity.
 */
public class AdverbVarDTO implements Serializable {

    private String id;

    private String value;

    private String higherDegree;

    private String highestDegree;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getHigherDegree() {
        return higherDegree;
    }

    public void setHigherDegree(String higherDegree) {
        this.higherDegree = higherDegree;
    }

    public String getHighestDegree() {
        return highestDegree;
    }

    public void setHighestDegree(String highestDegree) {
        this.highestDegree = highestDegree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdverbVarDTO adverbVarDTO = (AdverbVarDTO) o;

        return Objects.equals(id, adverbVarDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AdverbVarDTO{" +
            "id=" + id +
            ", value='" + value + "'" +
            ", higherDegree='" + higherDegree + "'" +
            ", highestDegree='" + highestDegree + "'" +
            '}';
    }
}
