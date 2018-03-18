package com.mindmap.jane.service.dto;


import com.mindmap.jane.domain.enumeration.AdjectiveDegreeQualifier;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the AdjectiveDegreeVar entity.
 */
public class AdjectiveDegreeVarDTO implements Serializable {

    private String id;

    private AdjectiveDegreeQualifier adjectiveDegree;

    private List<CasesVarDTO> casesVars;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AdjectiveDegreeQualifier getDegree() {
        return adjectiveDegree;
    }

    public void setDegree(AdjectiveDegreeQualifier degree) {
        this.adjectiveDegree = degree;
    }

    public List<CasesVarDTO> getCasesVars() {
        return casesVars;
    }

    public void setCasesVars(List<CasesVarDTO> casesVars) {
        this.casesVars = casesVars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdjectiveDegreeVarDTO adjectiveDegreeVarDTO = (AdjectiveDegreeVarDTO) o;

        return Objects.equals(id, adjectiveDegreeVarDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AdjectiveDegreeVarDTO{" +
            "id='" + id + '\'' +
            ", adjectiveDegree=" + adjectiveDegree +
            ", casesVars=" + casesVars +
            '}';
    }
}
