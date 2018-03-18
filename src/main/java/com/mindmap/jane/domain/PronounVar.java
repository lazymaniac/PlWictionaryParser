package com.mindmap.jane.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PronounVar.
 */
@Document(collection = "pronoun_var")
public class PronounVar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    private CasesVar casesVar;

    public PronounVar() {
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public PronounVar casesVar(CasesVar casesVar) {
        this.casesVar = casesVar;
        return this;
    }

    public CasesVar getCasesVar() {
        return casesVar;
    }

    public void setCasesVar(CasesVar casesVar) {
        this.casesVar = casesVar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PronounVar that = (PronounVar) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, casesVar);
    }

    @Override
    public String toString() {
        return "PronounVar{" +
            "id='" + id + '\'' +
            ", casesVar=" + casesVar +
            '}';
    }
}
