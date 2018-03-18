package com.mindmap.jane.service.dto;


import com.mindmap.jane.domain.enumeration.CasesFormQualifier;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CasesVar entity.
 */
public class CasesVarDTO implements Serializable {

    private String id;

    private CasesFormQualifier type;

    private String mianownik;

    private String dopelniacz;

    private String celownik;

    private String biernik;

    private String narzednik;

    private String miejscownik;

    private String wolacz;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CasesFormQualifier getType() {
        return type;
    }

    public void setType(CasesFormQualifier type) {
        this.type = type;
    }

    public String getMianownik() {
        return mianownik;
    }

    public void setMianownik(String mianownik) {
        this.mianownik = mianownik;
    }

    public String getDopelniacz() {
        return dopelniacz;
    }

    public void setDopelniacz(String dopelniacz) {
        this.dopelniacz = dopelniacz;
    }

    public String getCelownik() {
        return celownik;
    }

    public void setCelownik(String celownik) {
        this.celownik = celownik;
    }

    public String getBiernik() {
        return biernik;
    }

    public void setBiernik(String biernik) {
        this.biernik = biernik;
    }

    public String getNarzednik() {
        return narzednik;
    }

    public void setNarzednik(String narzednik) {
        this.narzednik = narzednik;
    }

    public String getMiejscownik() {
        return miejscownik;
    }

    public void setMiejscownik(String miejscownik) {
        this.miejscownik = miejscownik;
    }

    public String getWolacz() {
        return wolacz;
    }

    public void setWolacz(String wolacz) {
        this.wolacz = wolacz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CasesVarDTO casesVarDTO = (CasesVarDTO) o;

        return Objects.equals(id, casesVarDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CasesVarDTO{" +
            "id=" + id +
            ", type='" + type + "'" +
            ", mianownik='" + mianownik + "'" +
            ", dopelniacz='" + dopelniacz + "'" +
            ", celownik='" + celownik + "'" +
            ", biernik='" + biernik + "'" +
            ", narzednik='" + narzednik + "'" +
            ", miejscownik='" + miejscownik + "'" +
            ", wolacz='" + wolacz + "'" +
            '}';
    }
}
