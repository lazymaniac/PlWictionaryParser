package com.mindmap.jane.domain;

import com.mindmap.jane.wiktionary.numeration.Numeration;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A Meaning.
 */
@Document(collection = "meaning")
public class Meaning implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private Numeration numeration;

    private List<String> partOfSpeechQualifiers = new ArrayList<>();

    private List<String> meaningQualifiers = new ArrayList<>();

    // true if {{wikipedia}} tag is present in the end of meaning
    private Boolean hasLinkToWikipedia;

    private Sentence sentence;

    private List<Synonym> synonyms = new ArrayList<>();

    private List<Example> examples = new ArrayList<>();

    private List<Antonym> antonyms = new ArrayList<>();

    private List<Phraseology> phraseology = new ArrayList<>();

    private List<Collocation> collocations = new ArrayList<>();

    private List<Cognate> cognates = new ArrayList<>();

    private List<NounVar> nounVars = new ArrayList<>();

    private List<AdjectiveVar> adjectiveVars = new ArrayList<>();

    private List<AdverbVar> adverbVars = new ArrayList<>();

    private List<VerbVar> verbVars = new ArrayList<>();

    private List<PronounVar> pronounVars = new ArrayList<>();

    private List<NumeralVar> numeralVars = new ArrayList<>();

    public Meaning(Numeration numeration, Sentence sentence) {
        this.numeration = numeration;
        this.sentence = sentence;
    }

    public Meaning() {
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Numeration getNumeration() {
        return numeration;
    }

    public void setNumeration(Numeration numeration) {
        this.numeration = numeration;
    }

    public List<String> getPartOfSpeechQualifiers() {
        return partOfSpeechQualifiers;
    }

    public void setPartOfSpeechQualifiers(List<String> partOfSpeechQualifiers) {
        this.partOfSpeechQualifiers = partOfSpeechQualifiers;
    }

    public List<String> getMeaningQualifiers() {
        return meaningQualifiers;
    }

    public void setMeaningQualifiers(List<String> meaningQualifiers) {
        this.meaningQualifiers = meaningQualifiers;
    }

    public boolean isHasLinkToWikipedia() {
        return hasLinkToWikipedia;
    }

    public void setHasLinkToWikipedia(boolean hasLinkToWikipedia) {
        this.hasLinkToWikipedia = hasLinkToWikipedia;
    }

    public Sentence getSentence() {
        return sentence;
    }

    public Meaning sentence(Sentence sentence) {
        this.sentence = sentence;
        return this;
    }

    public void setSentence(Sentence sentence) {
        this.sentence = sentence;
    }


    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    public List<Synonym> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<Synonym> synonyms) {
        this.synonyms = synonyms;
    }

    public Meaning synonyms(List<Synonym> synonyms) {
        this.synonyms = synonyms;
        return this;
    }

    public List<Example> getExamples() {
        return examples;
    }

    public void setExamples(List<Example> examples) {
        this.examples = examples;
    }

    public Meaning examples(List<Example> examples) {
        this.examples = examples;
        return this;
    }

    public List<Antonym> getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(List<Antonym> antonyms) {
        this.antonyms = antonyms;
    }

    public Meaning anotnyms(List<Antonym> antonyms) {
        this.antonyms = antonyms;
        return this;
    }

    public List<Phraseology> getPhraseology() {
        return phraseology;
    }

    public void setPhraseology(List<Phraseology> phraseology) {
        this.phraseology = phraseology;
    }

    public Meaning phraseologies(List<Phraseology> phraseologies) {
        this.phraseology = phraseologies;
        return this;
    }

    public List<Collocation> getCollocations() {
        return collocations;
    }

    public void setCollocations(List<Collocation> collocations) {
        this.collocations = collocations;
    }

    public Meaning collocations(List<Collocation> collocations) {
        this.collocations = collocations;
        return this;
    }

    public List<Cognate> getCognates() {
        return cognates;
    }

    public void setCognates(List<Cognate> cognates) {
        this.cognates = cognates;
    }

    public Meaning cognates(List<Cognate> cognates) {
        this.cognates = cognates;
        return this;
    }

    public List<NounVar> getNounVars() {
        return nounVars;
    }

    public void setNounVars(List<NounVar> nounVars) {
        this.nounVars = nounVars;
    }

    public Meaning nounVars(List<NounVar> nounVars) {
        this.nounVars = nounVars;
        return this;
    }

    public List<AdjectiveVar> getAdjectiveVars() {
        return adjectiveVars;
    }

    public void setAdjectiveVars(List<AdjectiveVar> adjectiveVars) {
        this.adjectiveVars = adjectiveVars;
    }

    public Meaning adjectiveVars(List<AdjectiveVar> adjectiveVars) {
        this.adjectiveVars = adjectiveVars;
        return this;
    }

    public List<AdverbVar> getAdverbVars() {
        return adverbVars;
    }

    public void setAdverbVars(List<AdverbVar> adverbVars) {
        this.adverbVars = adverbVars;
    }

    public Meaning adverbVars(List<AdverbVar> adverbVars) {
        this.adverbVars = adverbVars;
        return this;
    }

    public List<VerbVar> getVerbVars() {
        return verbVars;
    }

    public void setVerbVars(List<VerbVar> verbVars) {
        this.verbVars = verbVars;
    }

    public Meaning verbVars(List<VerbVar> verbVars) {
        this.verbVars = verbVars;
        return this;
    }

    public List<PronounVar> getPronounVars() {
        return pronounVars;
    }

    public void setPronounVars(List<PronounVar> pronounVars) {
        this.pronounVars = pronounVars;
    }

    public Meaning pronounVars(List<PronounVar> pronounVars) {
        this.pronounVars = pronounVars;
        return this;
    }

    public List<NumeralVar> getNumeralVars() {
        return numeralVars;
    }

    public void setNumeralVars(List<NumeralVar> numeralVars) {
        this.numeralVars = numeralVars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meaning meaning = (Meaning) o;
        return Objects.equals(id, meaning.id) &&
            Objects.equals(sentence, meaning.sentence) &&
            Objects.equals(synonyms, meaning.synonyms) &&
            Objects.equals(examples, meaning.examples) &&
            Objects.equals(antonyms, meaning.antonyms) &&
            Objects.equals(phraseology, meaning.phraseology) &&
            Objects.equals(collocations, meaning.collocations) &&
            Objects.equals(cognates, meaning.cognates) &&
            Objects.equals(nounVars, meaning.nounVars) &&
            Objects.equals(adjectiveVars, meaning.adjectiveVars) &&
            Objects.equals(adverbVars, meaning.adverbVars) &&
            Objects.equals(verbVars, meaning.verbVars) &&
            Objects.equals(pronounVars, meaning.pronounVars) &&
            Objects.equals(numeralVars, meaning.numeralVars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sentence, synonyms, examples, antonyms, phraseology, collocations, cognates, nounVars, adjectiveVars, adverbVars, verbVars, pronounVars, numeralVars);
    }

    @Override
    public String toString() {
        return "Meaning{" +
            "id='" + id + '\'' +
            ", sentence=" + sentence +
            ", synonyms=" + synonyms +
            ", examples=" + examples +
            ", antonyms=" + antonyms +
            ", phraseology=" + phraseology +
            ", collocations=" + collocations +
            ", cognates=" + cognates +
            ", nounVars=" + nounVars +
            ", adjectiveVars=" + adjectiveVars +
            ", adverbVars=" + adverbVars +
            ", verbVars=" + verbVars +
            ", pronounVars=" + pronounVars +
            ", numeralVars=" + numeralVars +
            '}';
    }
}
