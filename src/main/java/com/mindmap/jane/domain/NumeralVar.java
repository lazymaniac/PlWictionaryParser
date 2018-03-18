package com.mindmap.jane.domain;

import com.mindmap.jane.domain.enumeration.NumeralTypeEnum;

import java.io.Serializable;
import java.util.Objects;

public class NumeralVar implements Serializable {

    private String id;

    private NumeralTypeEnum numeralType;

    private CasesVar variety;

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

    public CasesVar getVariety() {
        return variety;
    }

    public void setVariety(CasesVar variety) {
        this.variety = variety;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumeralVar that = (NumeralVar) o;
        return Objects.equals(id, that.id) &&
            numeralType == that.numeralType &&
            Objects.equals(variety, that.variety);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numeralType);
    }

    @Override
    public String toString() {
        return "NumeralVar{" +
            "id='" + id + '\'' +
            ", numeralType=" + numeralType +
            '}';
    }
}
