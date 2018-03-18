package com.mindmap.jane.service.dto;


import com.mindmap.jane.domain.Link;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Collocation entity.
 */
public class CollocationDTO implements Serializable {

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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CollocationDTO collocationDTO = (CollocationDTO) o;

        return Objects.equals(id, collocationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CollocationDTO{" +
            "id=" + getId() +
            ", link='" + getLink() + "'" +
            "}";
    }
}
