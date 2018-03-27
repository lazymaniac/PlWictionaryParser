package com.mindmap.jane.domain;

import com.mindmap.jane.domain.enumeration.NumeralTypeEnum;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

public class NumeralVar implements Serializable {

    @Field("numeral_type")
    private NumeralTypeEnum numeralType;

    @Field("cases_var")
    private CasesVar casesVar;

    public NumeralTypeEnum getNumeralType() {
        return numeralType;
    }

    public void setNumeralType(NumeralTypeEnum numeralType) {
        this.numeralType = numeralType;
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
        NumeralVar that = (NumeralVar) o;
        return numeralType == that.numeralType &&
            Objects.equals(casesVar, that.casesVar);
    }

    @Override
    public int hashCode() {

        return Objects.hash(numeralType, casesVar);
    }

    @Override
    public String toString() {
        return "NumeralVar{" +
            "numeralType=" + numeralType +
            ", casesVar=" + casesVar +
            '}';
    }
}
