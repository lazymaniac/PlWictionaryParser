package com.mindmap.jane.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PronounVar entity.
 */
public class PronounVarDTO implements Serializable {

    private String id;

    private CasesVarDTO casesVar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CasesVarDTO getCasesVar() {
        return casesVar;
    }

    public void setCasesVar(CasesVarDTO casesVar) {
        this.casesVar = casesVar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PronounVarDTO that = (PronounVarDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PronounVarDTO{" +
            "id='" + id + '\'' +
            ", casesVar=" + casesVar +
            '}';
    }
}
