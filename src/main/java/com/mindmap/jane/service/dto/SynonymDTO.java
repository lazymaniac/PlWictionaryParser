package com.mindmap.jane.service.dto;


import com.mindmap.jane.domain.Link;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the Synonym entity.
 */
public class SynonymDTO implements Serializable {

    private String id;

    private LinkDTO link;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SynonymDTO that = (SynonymDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SynonymDTO{" +
            "id='" + id + '\'' +
            ", link=" + link +
            '}';
    }
}
