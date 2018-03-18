package com.mindmap.jane.domain;

import com.mindmap.jane.domain.enumeration.CasesFormQualifier;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CasesVar.
 */
@Document(collection = "cases_var")
public class CasesVar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @Field("casesType")
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

    public CasesVar(String mianownik, String dopelniacz, String celownik, String biernik, String narzednik, String miejscownik, String wolacz) {
        this.mianownik = mianownik;
        this.dopelniacz = dopelniacz;
        this.celownik = celownik;
        this.biernik = biernik;
        this.narzednik = narzednik;
        this.miejscownik = miejscownik;
        this.wolacz = wolacz;
    }

    public CasesVar(CasesFormQualifier casesType) {
        this.casesType = casesType;
    }

    public CasesVar() {
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CasesVar casesVar = (CasesVar) o;
        if (casesVar.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, casesVar.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CasesVar{" +
            "id=" + getId() +
            ", casesType='" + getCasesType() + "'" +
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
