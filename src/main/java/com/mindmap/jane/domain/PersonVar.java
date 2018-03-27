package com.mindmap.jane.domain;

import com.mindmap.jane.domain.enumeration.PersonVarTypeEnum;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonVar.
 */
@Document(collection = "person_var")
public class PersonVar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("var_type")
    private PersonVarTypeEnum varType;

    @Field("per_1_sing")
    private String per1sing;

    @Field("per_2_sing")
    private String per2sing;

    @Field("per_3_sing")
    private String per3sing;

    @Field("per_1_plur")
    private String per1plur;

    @Field("per_2_plur")
    private String per2plur;

    @Field("per_3_plur")
    private String per3plur;

    public PersonVar(PersonVarTypeEnum varType, String per1sing, String per2sing, String per3sing, String per1plur, String per2plur, String per3plur) {
        this.varType = varType;
        this.per1sing = per1sing;
        this.per2sing = per2sing;
        this.per3sing = per3sing;
        this.per1plur = per1plur;
        this.per2plur = per2plur;
        this.per3plur = per3plur;
    }

    public PersonVar() {
    }

    public PersonVar(PersonVarTypeEnum varType) {
        this.varType = varType;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public PersonVarTypeEnum getVarType() {
        return varType;
    }

    public PersonVar varType(PersonVarTypeEnum varType) {
        this.varType = varType;
        return this;
    }

    public void setVarType(PersonVarTypeEnum varType) {
        this.varType = varType;
    }

    public String getPer1sing() {
        return per1sing;
    }

    public PersonVar per1sing(String per1sing) {
        this.per1sing = per1sing;
        return this;
    }

    public void setPer1sing(String per1sing) {
        this.per1sing = per1sing;
    }

    public String getPer2sing() {
        return per2sing;
    }

    public PersonVar per2sing(String per2sing) {
        this.per2sing = per2sing;
        return this;
    }

    public void setPer2sing(String per2sing) {
        this.per2sing = per2sing;
    }

    public String getPer3sing() {
        return per3sing;
    }

    public PersonVar per3sing(String per3sing) {
        this.per3sing = per3sing;
        return this;
    }

    public void setPer3sing(String per3sing) {
        this.per3sing = per3sing;
    }

    public String getPer1plur() {
        return per1plur;
    }

    public PersonVar per1plur(String per1plur) {
        this.per1plur = per1plur;
        return this;
    }

    public void setPer1plur(String per1plur) {
        this.per1plur = per1plur;
    }

    public String getPer2plur() {
        return per2plur;
    }

    public PersonVar per2plur(String per2plur) {
        this.per2plur = per2plur;
        return this;
    }

    public void setPer2plur(String per2plur) {
        this.per2plur = per2plur;
    }

    public String getPer3plur() {
        return per3plur;
    }

    public PersonVar per3plur(String per3plur) {
        this.per3plur = per3plur;
        return this;
    }

    public void setPer3plur(String per3plur) {
        this.per3plur = per3plur;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonVar personVar = (PersonVar) o;
        return varType == personVar.varType &&
            Objects.equals(per1sing, personVar.per1sing) &&
            Objects.equals(per2sing, personVar.per2sing) &&
            Objects.equals(per3sing, personVar.per3sing) &&
            Objects.equals(per1plur, personVar.per1plur) &&
            Objects.equals(per2plur, personVar.per2plur) &&
            Objects.equals(per3plur, personVar.per3plur);
    }

    @Override
    public int hashCode() {

        return Objects.hash(varType, per1sing, per2sing, per3sing, per1plur, per2plur, per3plur);
    }

    @Override
    public String toString() {
        return "PersonVar{" +
            "varType=" + varType +
            ", per1sing='" + per1sing + '\'' +
            ", per2sing='" + per2sing + '\'' +
            ", per3sing='" + per3sing + '\'' +
            ", per1plur='" + per1plur + '\'' +
            ", per2plur='" + per2plur + '\'' +
            ", per3plur='" + per3plur + '\'' +
            '}';
    }
}
