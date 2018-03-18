package com.mindmap.jane.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A AdjectiveVar.
 */
@Document(collection = "adjective_var")
public class AdjectiveVar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private List<AdjectiveDegreeVar> adjectiveDegreeVars = new ArrayList<>();

    public AdjectiveVar() {
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
        return Objects.equals(id, that.id) &&
            Objects.equals(adjectiveDegreeVars, that.adjectiveDegreeVars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, adjectiveDegreeVars);
    }

    @Override
    public String toString() {
        return "AdjectiveVar{" +
            "id='" + getId() + '\'' +
            ", adjectiveDegreeVars=" + getAdjectiveDegreeVars() +
            '}';
    }
}
