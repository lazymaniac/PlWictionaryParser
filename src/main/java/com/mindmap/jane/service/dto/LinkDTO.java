package com.mindmap.jane.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class LinkDTO implements Serializable {

    private String id;

    /**
     * Index in Sentence.
     */
    private Integer index;

    private String baseForm;

    private String usedForm;

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

    public String getBaseForm() {
        return baseForm;
    }

    public void setBaseForm(String baseForm) {
        this.baseForm = baseForm;
    }

    public String getUsedForm() {
        return usedForm;
    }

    public void setUsedForm(String usedForm) {
        this.usedForm = usedForm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkDTO linkDTO = (LinkDTO) o;
        return Objects.equals(id, linkDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, index, baseForm, usedForm);
    }

    @Override
    public String toString() {
        return "LinkDTO{" +
            "id='" + id + '\'' +
            ", index=" + index +
            ", baseForm='" + baseForm + '\'' +
            ", usedForm='" + usedForm + '\'' +
            '}';
    }
}
