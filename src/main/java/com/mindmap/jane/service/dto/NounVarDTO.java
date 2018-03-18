package com.mindmap.jane.service.dto;


import com.mindmap.jane.domain.enumeration.GenderQualifier;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the NounVar entity.
 */
public class NounVarDTO implements Serializable {

    private String id;

    private String topic;

    private GenderQualifier gender;

    private GenderQualifier secondGender;

    private Boolean varietyAble;

    private Boolean noSingular;

    private Boolean noPlural;

    private List<CasesVarDTO> casesVars;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public GenderQualifier getGender() {
        return gender;
    }

    public void setGender(GenderQualifier gender) {
        this.gender = gender;
    }

    public GenderQualifier getSecondGender() {
        return secondGender;
    }

    public void setSecondGender(GenderQualifier secondGender) {
        this.secondGender = secondGender;
    }

    public Boolean isVarietyAble() {
        return varietyAble;
    }

    public void setVarietyAble(Boolean varietyAble) {
        this.varietyAble = varietyAble;
    }

    public Boolean isNoSingular() {
        return noSingular;
    }

    public void setNoSingular(Boolean noSingular) {
        this.noSingular = noSingular;
    }

    public Boolean isNoPlural() {
        return noPlural;
    }

    public void setNoPlural(Boolean noPlural) {
        this.noPlural = noPlural;
    }

    public List<CasesVarDTO> getCasesVars() {
        return casesVars;
    }

    public void setCasesVars(List<CasesVarDTO> casesVars) {
        this.casesVars = casesVars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NounVarDTO that = (NounVarDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "NounVarDTO{" +
            "id='" + id + '\'' +
            ", topic='" + topic + '\'' +
            ", gender=" + gender +
            ", secondGender=" + secondGender +
            ", varietyAble=" + varietyAble +
            ", noSingular=" + noSingular +
            ", noPlural=" + noPlural +
            ", casesVars=" + casesVars +
            '}';
    }
}
