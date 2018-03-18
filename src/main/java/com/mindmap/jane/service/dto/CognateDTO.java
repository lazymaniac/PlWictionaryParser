package com.mindmap.jane.service.dto;


import com.mindmap.jane.domain.enumeration.GenderQualifier;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Cognate entity.
 */
public class CognateDTO implements Serializable {

    private String id;

    private LinkDTO link;

    private GenderQualifier genderQualifier;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkDTO getLink() {
        return link;
    }

    public void setLink(LinkDTO link) {
        this.link = link;
    }

    public GenderQualifier getGenderQualifier() {
        return genderQualifier;
    }

    public void setGenderQualifier(GenderQualifier genderQualifier) {
        this.genderQualifier = genderQualifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CognateDTO cognateDTO = (CognateDTO) o;

        return Objects.equals(id, cognateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CognateDTO{" +
            "id=" + getId() +
            ", link='" + getLink() + "'" +
            ", genderQualifier='" + getGenderQualifier() + "'" +
            "}";
    }
}
