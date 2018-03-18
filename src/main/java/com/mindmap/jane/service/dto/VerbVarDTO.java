package com.mindmap.jane.service.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the VerbVar entity.
 */
public class VerbVarDTO implements Serializable {

    private String id;

    private Boolean isPerfective;

    private Boolean isReflexivVerb;

    private String topic;

    private String conjugation;

    private String reflexivPronoun;

    private String perfective;

    private String imperfective;

    private String infinitive;

    private String impersonalFromPast;

    private String adverbialParticipleContemporary;

    private String adverbialParticiplePrior;

    private String gerund;

    private List<PersonVarDTO> personVars;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIsPerfective() {
        return isPerfective;
    }

    public void setIsPerfective(Boolean isPerfective) {
        this.isPerfective = isPerfective;
    }

    public Boolean getIsReflexivVerb() {
        return isReflexivVerb;
    }

    public void setIsReflexivVerb(Boolean isReflexivVerb) {
        this.isReflexivVerb = isReflexivVerb;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getConjugation() {
        return conjugation;
    }

    public void setConjugation(String conjugation) {
        this.conjugation = conjugation;
    }

    public String getReflexivPronoun() {
        return reflexivPronoun;
    }

    public void setReflexivPronoun(String reflexivPronoun) {
        this.reflexivPronoun = reflexivPronoun;
    }

    public String getPerfective() {
        return perfective;
    }

    public void setPerfective(String perfective) {
        this.perfective = perfective;
    }

    public String getImperfective() {
        return imperfective;
    }

    public void setImperfective(String imperfective) {
        this.imperfective = imperfective;
    }

    public String getInfinitive() {
        return infinitive;
    }

    public void setInfinitive(String infinitive) {
        this.infinitive = infinitive;
    }

    public String getImpersonalFromPast() {
        return impersonalFromPast;
    }

    public void setImpersonalFromPast(String impersonalFromPast) {
        this.impersonalFromPast = impersonalFromPast;
    }

    public String getAdverbialParticipleContemporary() {
        return adverbialParticipleContemporary;
    }

    public void setAdverbialParticipleContemporary(String adverbialParticipleContemporary) {
        this.adverbialParticipleContemporary = adverbialParticipleContemporary;
    }

    public String getAdverbialParticiplePrior() {
        return adverbialParticiplePrior;
    }

    public void setAdverbialParticiplePrior(String adverbialParticiplePrior) {
        this.adverbialParticiplePrior = adverbialParticiplePrior;
    }

    public String getGerund() {
        return gerund;
    }

    public void setGerund(String gerund) {
        this.gerund = gerund;
    }

    public void setPerfective(Boolean perfective) {
        isPerfective = perfective;
    }

    public Boolean getReflexivVerb() {
        return isReflexivVerb;
    }

    public void setReflexivVerb(Boolean reflexivVerb) {
        isReflexivVerb = reflexivVerb;
    }

    public List<PersonVarDTO> getPersonVars() {
        return personVars;
    }

    public void setPersonVars(List<PersonVarDTO> personVars) {
        this.personVars = personVars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerbVarDTO that = (VerbVarDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "VerbVarDTO{" +
            "id='" + id + '\'' +
            ", isPerfective=" + isPerfective +
            ", isReflexivVerb=" + isReflexivVerb +
            ", topic='" + topic + '\'' +
            ", conjugation='" + conjugation + '\'' +
            ", reflexivPronoun='" + reflexivPronoun + '\'' +
            ", perfective='" + perfective + '\'' +
            ", imperfective='" + imperfective + '\'' +
            ", infinitive='" + infinitive + '\'' +
            ", impersonalFromPast='" + impersonalFromPast + '\'' +
            ", adverbialParticipleContemporary='" + adverbialParticipleContemporary + '\'' +
            ", adverbialParticiplePrior='" + adverbialParticiplePrior + '\'' +
            ", gerund='" + gerund + '\'' +
            ", personVars=" + personVars +
            '}';
    }
}
