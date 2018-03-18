package com.mindmap.jane.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

@Document(collection = "link")
public class Link implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    /**
     * Index in Sentence.
     */
    @Field(value = "index")
    private Integer index;

    @Field(value = "base_form")
    private String baseForm;

    @Field(value = "used_form")
    private String usedForm;

    public Link(String baseForm) {
        this.baseForm = baseForm;
        this.usedForm = baseForm;
    }

    public Link(String baseForm, String usedForm) {
        this.baseForm = baseForm;
        this.usedForm = usedForm;
    }

    public Link() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Link index(Integer index) {
        this.index = index;
        return this;
    }

    public String getBaseForm() {
        return baseForm;
    }

    public void setBaseForm(String baseForm) {
        this.baseForm = baseForm;
    }

    public Link baseForm(String baseForm) {
        this.baseForm = baseForm;
        return this;
    }

    public String getUsedForm() {
        return usedForm;
    }

    public void setUsedForm(String usedForm) {
        this.usedForm = usedForm;
    }

    public Link usedForm(String usedForm) {
        this.usedForm = usedForm;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(id, link.id) &&
            Objects.equals(index, link.index) &&
            Objects.equals(baseForm, link.baseForm) &&
            Objects.equals(usedForm, link.usedForm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, index, baseForm, usedForm);
    }

    @Override
    public String toString() {
        return "Link{" +
            "id='" + id + '\'' +
            ", index=" + index +
            ", baseForm='" + baseForm + '\'' +
            ", usedForm='" + usedForm + '\'' +
            '}';
    }
}
