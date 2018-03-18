package com.mindmap.jane.service.dto;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the AdjectiveVar entity.
 */
public class AdjectiveVarDTO implements Serializable {

    private String id;

    private List<AdjectiveDegreeVarDTO> adjectiveDegreeVars;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<AdjectiveDegreeVarDTO> getAdjectiveDegreeVars() {
        return adjectiveDegreeVars;
    }

    public void setAdjectiveDegreeVars(List<AdjectiveDegreeVarDTO> adjectiveDegreeVars) {
        this.adjectiveDegreeVars = adjectiveDegreeVars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjectiveVarDTO that = (AdjectiveVarDTO) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(adjectiveDegreeVars, that.adjectiveDegreeVars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, adjectiveDegreeVars);
    }

    @Override
    public String toString() {
        return "AdjectiveVarDTO{" +
            "id='" + id + '\'' +
            ", adjectiveDegreeVars=" + adjectiveDegreeVars +
            '}';
    }
}
