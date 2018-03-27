package com.mindmap.jane.domain;

import com.mindmap.jane.wiktionary.generators.form.ConjugationGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A VerbVar.
 */
public class VerbVar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("is_perfective")
    private Boolean isPerfective;

    @Field("is_reflexiv_verb")
    private Boolean isReflexivVerb;

    @Field("topic")
    private String topic;

    @Field("conjugation")
    private String conjugation;

    @Field("reflexiv_pronoun")
    private String reflexivPronoun;

    @Field("perfective")
    private String perfective;

    @Field("imperfective")
    private String imperfective;

    @Field("infinitive")
    private String infinitive;

    @Field("impersonal_form_past")
    private String impersonalFormPast;

    @Field("adverbial_participle_contemporary")
    private String adverbialParticipleContemporary;

    @Field("adverbial_participle_prior")
    private String adverbialParticiplePrior;

    @Field("gerund")
    private String gerund;

    @Field("person_vars")
    private List<PersonVar> personVars = new ArrayList<>();

    // TODO move to other class
    public final void fillConjugationVariety() {
        ConjugationGenerator kon = new ConjugationGenerator(this, conjugation);
        if (StringUtils.isNotBlank(topic)
            && StringUtils.isNotBlank(conjugation)) {
            kon.fillForm();
        }
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public Boolean isIsPerfective() {
        return isPerfective;
    }

    public VerbVar isPerfective(Boolean isPerfective) {
        this.isPerfective = isPerfective;
        return this;
    }

    public void setIsPerfective(Boolean isPerfective) {
        this.isPerfective = isPerfective;
    }

    public Boolean isIsReflexivVerb() {
        return isReflexivVerb;
    }

    public VerbVar isReflexivVerb(Boolean isReflexivVerb) {
        this.isReflexivVerb = isReflexivVerb;
        return this;
    }

    public void setIsReflexivVerb(Boolean isReflexivVerb) {
        this.isReflexivVerb = isReflexivVerb;
    }

    public String getTopic() {
        return topic;
    }

    public VerbVar topic(String topic) {
        this.topic = topic;
        return this;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getConjugation() {
        return conjugation;
    }

    public VerbVar conjugation(String conjugation) {
        this.conjugation = conjugation;
        return this;
    }

    public void setConjugation(String conjugation) {
        this.conjugation = conjugation;
    }

    public String getReflexivPronoun() {
        return reflexivPronoun;
    }

    public VerbVar reflexivPronoun(String reflexivPronoun) {
        this.reflexivPronoun = reflexivPronoun;
        return this;
    }

    public void setReflexivPronoun(String reflexivPronoun) {
        this.reflexivPronoun = reflexivPronoun;
    }

    public String getPerfective() {
        return perfective;
    }

    public VerbVar perfective(String perfective) {
        this.perfective = perfective;
        return this;
    }

    public void setPerfective(String perfective) {
        this.perfective = perfective;
    }

    public String getImperfective() {
        return imperfective;
    }

    public VerbVar imperfective(String imperfective) {
        this.imperfective = imperfective;
        return this;
    }

    public void setImperfective(String imperfective) {
        this.imperfective = imperfective;
    }

    public String getInfinitive() {
        return infinitive;
    }

    public VerbVar infinitive(String infinitive) {
        this.infinitive = infinitive;
        return this;
    }

    public void setInfinitive(String infinitive) {
        this.infinitive = infinitive;
    }

    public String getImpersonalFormPast() {
        return impersonalFormPast;
    }

    public VerbVar impersonalFromPast(String impersonalFromPast) {
        this.impersonalFormPast = impersonalFromPast;
        return this;
    }

    public void setImpersonalFormPast(String impersonalFormPast) {
        this.impersonalFormPast = impersonalFormPast;
    }

    public String getAdverbialParticipleContemporary() {
        return adverbialParticipleContemporary;
    }

    public VerbVar adverbialParticipleContemporary(String adverbialParticipleContemporary) {
        this.adverbialParticipleContemporary = adverbialParticipleContemporary;
        return this;
    }

    public void setAdverbialParticipleContemporary(String adverbialParticipleContemporary) {
        this.adverbialParticipleContemporary = adverbialParticipleContemporary;
    }

    public String getAdverbialParticiplePrior() {
        return adverbialParticiplePrior;
    }

    public VerbVar adverbialParticiplePrior(String adverbialParticiplePrior) {
        this.adverbialParticiplePrior = adverbialParticiplePrior;
        return this;
    }

    public void setAdverbialParticiplePrior(String adverbialParticiplePrior) {
        this.adverbialParticiplePrior = adverbialParticiplePrior;
    }

    public String getGerund() {
        return gerund;
    }

    public VerbVar gerund(String gerund) {
        this.gerund = gerund;
        return this;
    }

    public void setGerund(String gerund) {
        this.gerund = gerund;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public List<PersonVar> getPersonVars() {
        return personVars;
    }

    public void setPersonVars(List<PersonVar> personVars) {
        this.personVars = personVars;
    }

    public VerbVar personVars(List<PersonVar> personVars) {
        this.personVars = personVars;
        return this;
    }

    public void addPersonVar(PersonVar personVar) {
        this.personVars.add(personVar);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerbVar verbVar = (VerbVar) o;
        return Objects.equals(isPerfective, verbVar.isPerfective) &&
            Objects.equals(isReflexivVerb, verbVar.isReflexivVerb) &&
            Objects.equals(topic, verbVar.topic) &&
            Objects.equals(conjugation, verbVar.conjugation) &&
            Objects.equals(reflexivPronoun, verbVar.reflexivPronoun) &&
            Objects.equals(perfective, verbVar.perfective) &&
            Objects.equals(imperfective, verbVar.imperfective) &&
            Objects.equals(infinitive, verbVar.infinitive) &&
            Objects.equals(impersonalFormPast, verbVar.impersonalFormPast) &&
            Objects.equals(adverbialParticipleContemporary, verbVar.adverbialParticipleContemporary) &&
            Objects.equals(adverbialParticiplePrior, verbVar.adverbialParticiplePrior) &&
            Objects.equals(gerund, verbVar.gerund) &&
            Objects.equals(personVars, verbVar.personVars);
    }

    @Override
    public int hashCode() {

        return Objects.hash(isPerfective, isReflexivVerb, topic, conjugation, reflexivPronoun, perfective, imperfective, infinitive, impersonalFormPast, adverbialParticipleContemporary, adverbialParticiplePrior, gerund, personVars);
    }

    @Override
    public String toString() {
        return "VerbVar{" +
            "isPerfective=" + isPerfective +
            ", isReflexivVerb=" + isReflexivVerb +
            ", topic='" + topic + '\'' +
            ", conjugation='" + conjugation + '\'' +
            ", reflexivPronoun='" + reflexivPronoun + '\'' +
            ", perfective='" + perfective + '\'' +
            ", imperfective='" + imperfective + '\'' +
            ", infinitive='" + infinitive + '\'' +
            ", impersonalFormPast='" + impersonalFormPast + '\'' +
            ", adverbialParticipleContemporary='" + adverbialParticipleContemporary + '\'' +
            ", adverbialParticiplePrior='" + adverbialParticiplePrior + '\'' +
            ", gerund='" + gerund + '\'' +
            ", personVars=" + personVars +
            '}';
    }
}
