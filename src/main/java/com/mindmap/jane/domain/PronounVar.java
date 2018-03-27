package com.mindmap.jane.domain;

import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PronounVar.
 */
public class PronounVar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("cases_var")
    private CasesVar casesVar;

    public PronounVar() {
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

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

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PronounVar that = (PronounVar) o;
        return Objects.equals(casesVar, that.casesVar);
    }

    @Override
    public int hashCode() {

        return Objects.hash(casesVar);
    }

    @Override
    public String toString() {
        return "PronounVar{" +
            "casesVar=" + casesVar +
            '}';
    }
}
