package com.mindmap.jane.service.dto;


import com.mindmap.jane.domain.Link;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Antonym entity.
 */
public class AntonymDTO implements Serializable {

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

        AntonymDTO antonymDTO = (AntonymDTO) o;

        return Objects.equals(id, antonymDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AntonymDTO{" +
            "id=" + id +
            ", link='" + link + "'" +
            '}';
    }
}
