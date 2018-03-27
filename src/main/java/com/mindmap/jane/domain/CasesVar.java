package com.mindmap.jane.domain;

import com.mindmap.jane.domain.enumeration.CasesFormQualifier;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CasesVar.
 */
public class CasesVar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("cases_type")
    private CasesFormQualifier casesType;

    @Field("mianownik")
    private String mianownik;

    @Field("dopelniacz")
    private String dopelniacz;

    @Field("celownik")
    private String celownik;

    @Field("biernik")
    private String biernik;

    @Field("narzednik")
    private String narzednik;

    @Field("miejscownik")
    private String miejscownik;

    @Field("wolacz")
    private String wolacz;

    public CasesVar(CasesFormQualifier casesType) {
        this.casesType = casesType;
    }

    public CasesVar() {
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public CasesFormQualifier getCasesType() {
        return casesType;
    }

    public CasesVar type(CasesFormQualifier type) {
        this.casesType = type;
        return this;
    }

    public void setCasesType(CasesFormQualifier casesType) {
        this.casesType = casesType;
    }

    public String getMianownik() {
        return mianownik;
    }

    public CasesVar mianownik(String mianownik) {
        this.mianownik = mianownik;
        return this;
    }

    public void setMianownik(String mianownik) {
        this.mianownik = mianownik;
    }

    public String getDopelniacz() {
        return dopelniacz;
    }

    public CasesVar dopelniacz(String dopelniacz) {
        this.dopelniacz = dopelniacz;
        return this;
    }

    public void setDopelniacz(String dopelniacz) {
        this.dopelniacz = dopelniacz;
    }

    public String getCelownik() {
        return celownik;
    }

    public CasesVar celownik(String celownik) {
        this.celownik = celownik;
        return this;
    }

    public void setCelownik(String celownik) {
        this.celownik = celownik;
    }

    public String getBiernik() {
        return biernik;
    }

    public CasesVar biernik(String biernik) {
        this.biernik = biernik;
        return this;
    }

    public void setBiernik(String biernik) {
        this.biernik = biernik;
    }

    public String getNarzednik() {
        return narzednik;
    }

    public CasesVar narzednik(String narzednik) {
        this.narzednik = narzednik;
        return this;
    }

    public void setNarzednik(String narzednik) {
        this.narzednik = narzednik;
    }

    public String getMiejscownik() {
        return miejscownik;
    }

    public CasesVar miejscownik(String miejscownik) {
        this.miejscownik = miejscownik;
        return this;
    }

    public void setMiejscownik(String miejscownik) {
        this.miejscownik = miejscownik;
    }

    public String getWolacz() {
        return wolacz;
    }

    public CasesVar wolacz(String wolacz) {
        this.wolacz = wolacz;
        return this;
    }

    public void setWolacz(String wolacz) {
        this.wolacz = wolacz;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CasesVar casesVar = (CasesVar) o;
        return casesType == casesVar.casesType &&
            Objects.equals(mianownik, casesVar.mianownik) &&
            Objects.equals(dopelniacz, casesVar.dopelniacz) &&
            Objects.equals(celownik, casesVar.celownik) &&
            Objects.equals(biernik, casesVar.biernik) &&
            Objects.equals(narzednik, casesVar.narzednik) &&
            Objects.equals(miejscownik, casesVar.miejscownik) &&
            Objects.equals(wolacz, casesVar.wolacz);
    }

    @Override
    public int hashCode() {

        return Objects.hash(casesType, mianownik, dopelniacz, celownik, biernik, narzednik, miejscownik, wolacz);
    }

    @Override
    public String toString() {
        return "CasesVar{" +
            "casesType=" + casesType +
            ", mianownik='" + mianownik + '\'' +
            ", dopelniacz='" + dopelniacz + '\'' +
            ", celownik='" + celownik + '\'' +
            ", biernik='" + biernik + '\'' +
            ", narzednik='" + narzednik + '\'' +
            ", miejscownik='" + miejscownik + '\'' +
            ", wolacz='" + wolacz + '\'' +
            '}';
    }
}
