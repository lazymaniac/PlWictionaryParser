package com.mindmap.jane.domain;

import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A AdjectiveVar.
 */
public class AdjectiveVar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("adjective_degree_vars")
    private List<AdjectiveDegreeVar> adjectiveDegreeVars = new ArrayList<>();

    public AdjectiveVar() {
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public List<AdjectiveDegreeVar> getAdjectiveDegreeVars() {
        return adjectiveDegreeVars;
    }

    public void setAdjectiveDegreeVars(List<AdjectiveDegreeVar> adjectiveDegreeVars) {
        this.adjectiveDegreeVars = adjectiveDegreeVars;
    }

    public AdjectiveVar adjectiveDegreeVar(List<AdjectiveDegreeVar> adjectiveDegreeVars) {
        this.adjectiveDegreeVars = adjectiveDegreeVars;
        return this;
    }

    public void addAdjectiveDegree(AdjectiveDegreeVar adjectiveDegreeVar) {
        this.adjectiveDegreeVars.add(adjectiveDegreeVar);
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjectiveVar that = (AdjectiveVar) o;
        return Objects.equals(adjectiveDegreeVars, that.adjectiveDegreeVars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjectiveDegreeVars);
    }

    @Override
    public String toString() {
        return "AdjectiveVar{" +
            "adjectiveDegreeVars=" + getAdjectiveDegreeVars() +
            '}';
    }
}
