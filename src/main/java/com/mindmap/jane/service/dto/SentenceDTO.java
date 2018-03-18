package com.mindmap.jane.service.dto;

import java.util.List;
import java.util.Objects;

public class SentenceDTO {

    private String id;

    private String originalForm;

    private List<LinkDTO> links;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalForm() {
        return originalForm;
    }

    public void setOriginalForm(String originalForm) {
        this.originalForm = originalForm;
    }

    public List<LinkDTO> getLinks() {
        return links;
    }

    public void setLinks(List<LinkDTO> links) {
        this.links = links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SentenceDTO that = (SentenceDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SentenceDTO{" +
            "id='" + id + '\'' +
            ", originalForm='" + originalForm + '\'' +
            ", links=" + links +
            '}';
    }
}
