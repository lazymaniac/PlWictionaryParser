package com.mindmap.jane.service.dto;

import com.mindmap.jane.domain.enumeration.NumeralTypeEnum;

import java.io.Serializable;
import java.util.Objects;

public class NumeralVarDTO implements Serializable {

    private String id;

    private NumeralTypeEnum numeralType;

    private CasesVarDTO variety;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NumeralTypeEnum getNumeralType() {
        return numeralType;
    }

    public void setNumeralType(NumeralTypeEnum numeralType) {
        this.numeralType = numeralType;
    }

    public CasesVarDTO getVariety() {
        return variety;
    }

    public void setVariety(CasesVarDTO variety) {
        this.variety = variety;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumeralVarDTO that = (NumeralVarDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "NumeralVarDTO{" +
            "id='" + id + '\'' +
            ", numeralType=" + numeralType +
            ", variety=" + variety +
            '}';
    }
}
