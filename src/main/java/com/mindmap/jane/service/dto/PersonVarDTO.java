package com.mindmap.jane.service.dto;


import com.mindmap.jane.domain.enumeration.PersonVarTypeEnum;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PersonVar entity.
 */
public class PersonVarDTO implements Serializable {

    private String id;

    private PersonVarTypeEnum varType;

    private String per1sing;

    private String per2sing;

    private String per3sing;

    private String per1plur;

    private String per2plur;

    private String per3plur;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PersonVarTypeEnum getVarType() {
        return varType;
    }

    public void setVarType(PersonVarTypeEnum varType) {
        this.varType = varType;
    }

    public String getPer1sing() {
        return per1sing;
    }

    public void setPer1sing(String per1sing) {
        this.per1sing = per1sing;
    }

    public String getPer2sing() {
        return per2sing;
    }

    public void setPer2sing(String per2sing) {
        this.per2sing = per2sing;
    }

    public String getPer3sing() {
        return per3sing;
    }

    public void setPer3sing(String per3sing) {
        this.per3sing = per3sing;
    }

    public String getPer1plur() {
        return per1plur;
    }

    public void setPer1plur(String per1plur) {
        this.per1plur = per1plur;
    }

    public String getPer2plur() {
        return per2plur;
    }

    public void setPer2plur(String per2plur) {
        this.per2plur = per2plur;
    }

    public String getPer3plur() {
        return per3plur;
    }

    public void setPer3plur(String per3plur) {
        this.per3plur = per3plur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonVarDTO personVarDTO = (PersonVarDTO) o;

        return Objects.equals(id, personVarDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonVarDTO{" +
            "id=" + getId() +
            ", varType='" + getVarType() + "'" +
            ", per1sing='" + getPer1sing() + "'" +
            ", per2sing='" + getPer2sing() + "'" +
            ", per3sing='" + getPer3sing() + "'" +
            ", per1plur='" + getPer1plur() + "'" +
            ", per2plur='" + getPer2plur() + "'" +
            ", per3plur='" + getPer3plur() + "'" +
            "}";
    }
}
