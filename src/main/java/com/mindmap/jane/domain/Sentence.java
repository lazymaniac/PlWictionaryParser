package com.mindmap.jane.domain;

import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Sentence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("original_form")
    private String originalForm;

    @Field("links")
    private List<Link> links;

    public Sentence() {
    }

    public Sentence(String originalForm) {
        this.originalForm = originalForm;
    }

    public Sentence(List<Link> links) {
        this.links = links;
    }

    public String getOriginalForm() {
        return originalForm;
    }

    public void setOriginalForm(String originalForm) {
        this.originalForm = originalForm;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public Sentence links(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sentence sentence = (Sentence) o;
        return Objects.equals(originalForm, sentence.originalForm) &&
            Objects.equals(links, sentence.links);
    }

    @Override
    public int hashCode() {

        return Objects.hash(originalForm, links);
    }

    @Override
    public String toString() {
        return "Sentence{" +
            "originalForm='" + originalForm + '\'' +
            ", links=" + links +
            '}';
    }
}
