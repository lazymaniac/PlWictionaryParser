package com.mindmap.jane.domain;

import com.mindmap.jane.domain.enumeration.GenderQualifier;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A NounVar.
 */
@Document(collection = "noun_var")
public class NounVar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @Field("topic")
    private String topic;

    @Field("gender")
    private GenderQualifier gender;

    @Field("second_gender")
    private GenderQualifier secondGender;

    @Field("variety_able")
    private Boolean varietyAble;

    @Field("no_singular")
    private Boolean noSingular;

    @Field("no_plural")
    private Boolean noPlural;

    private List<CasesVar> casesVars = new ArrayList<>();

    public NounVar() {
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public NounVar topic(String topic) {
        this.topic = topic;
        return this;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public GenderQualifier getGender() {
        return gender;
    }

    public NounVar gender(GenderQualifier gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(GenderQualifier gender) {
        this.gender = gender;
    }

    public GenderQualifier getSecondGender() {
        return secondGender;
    }

    public NounVar secondGender(GenderQualifier secondGender) {
        this.secondGender = secondGender;
        return this;
    }

    public void setSecondGender(GenderQualifier secondGender) {
        this.secondGender = secondGender;
    }

    public Boolean isVarietyAble() {
        return varietyAble;
    }

    public NounVar varietyAble(Boolean varietyAble) {
        this.varietyAble = varietyAble;
        return this;
    }

    public void setVarietyAble(Boolean varietyAble) {
        this.varietyAble = varietyAble;
    }

    public Boolean isNoSingular() {
        return noSingular;
    }

    public NounVar noSingular(Boolean noSingular) {
        this.noSingular = noSingular;
        return this;
    }

    public void setNoSingular(Boolean noSingular) {
        this.noSingular = noSingular;
    }

    public Boolean isNoPlural() {
        return noPlural;
    }

    public NounVar noPlural(Boolean noPlural) {
        this.noPlural = noPlural;
        return this;
    }

    public void setNoPlural(Boolean noPlural) {
        this.noPlural = noPlural;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public List<CasesVar> getCasesVars() {
        return casesVars;
    }

    public void setCasesVars(List<CasesVar> casesVars) {
        this.casesVars = casesVars;
    }

    public NounVar casesVars(List<CasesVar> casesVars) {
        this.casesVars = casesVars;
        return this;
    }

    public void addCasesVar(CasesVar casesVar) {
        this.casesVars.add(casesVar);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NounVar nounVar = (NounVar) o;
        return Objects.equals(id, nounVar.id) &&
            Objects.equals(topic, nounVar.topic) &&
            gender == nounVar.gender &&
            secondGender == nounVar.secondGender &&
            Objects.equals(varietyAble, nounVar.varietyAble) &&
            Objects.equals(noSingular, nounVar.noSingular) &&
            Objects.equals(noPlural, nounVar.noPlural) &&
            Objects.equals(casesVars, nounVar.casesVars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, topic, gender, secondGender, varietyAble, noSingular, noPlural, casesVars);
    }

    @Override
    public String toString() {
        return "NounVar{" +
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
