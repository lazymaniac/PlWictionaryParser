package com.mindmap.jane.domain;

import com.mindmap.jane.domain.enumeration.AdjectiveDegreeQualifier;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A AdjectiveDegreeVar.
 */
public class AdjectiveDegreeVar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("adjective_degree")
    private AdjectiveDegreeQualifier adjectiveDegree;

    @Field("cases_vars")
    private List<CasesVar> casesVars = new ArrayList<>();

    public AdjectiveDegreeVar(AdjectiveDegreeQualifier adjectiveDegree) {
        this.adjectiveDegree = adjectiveDegree;
    }

    public AdjectiveDegreeVar() {
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public AdjectiveDegreeQualifier getAdjectiveDegree() {
        return adjectiveDegree;
    }

    public void setAdjectiveDegree(AdjectiveDegreeQualifier adjectiveDegree) {
        this.adjectiveDegree = adjectiveDegree;
    }

    public List<CasesVar> getCasesVars() {
        return casesVars;
    }

    public void setCasesVars(List<CasesVar> casesVars) {
        this.casesVars = casesVars;
    }

    public void addCasesVar(CasesVar casesVar) {
        this.casesVars.add(casesVar);
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjectiveDegreeVar that = (AdjectiveDegreeVar) o;
        return adjectiveDegree == that.adjectiveDegree &&
            Objects.equals(casesVars, that.casesVars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjectiveDegree, casesVars);
    }

    @Override
    public String toString() {
        return "AdjectiveDegreeVar{" +
            "adjectiveDegree=" + adjectiveDegree +
            ", casesVars=" + casesVars +
            '}';
    }
}
