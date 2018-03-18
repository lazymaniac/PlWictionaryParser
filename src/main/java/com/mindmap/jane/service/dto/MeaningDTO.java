package com.mindmap.jane.service.dto;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the Meaning entity.
 */
public class MeaningDTO implements Serializable {

    private String id;

    private List<String> meaningQualifiers = new ArrayList<>();

    private Boolean hasLinkToWikipedia;

    private SentenceDTO sentence;

    private List<SynonymDTO> synonyms = new ArrayList<>();

    private List<ExampleDTO> examples = new ArrayList<>();

    private List<AntonymDTO> antonyms = new ArrayList<>();

    private List<PhraseologyDTO> phraseology = new ArrayList<>();

    private List<CollocationDTO> collocations = new ArrayList<>();

    private List<CognateDTO> cognates = new ArrayList<>();

    private List<NounVarDTO> nounVars = new ArrayList<>();

    private List<AdjectiveVarDTO> adjectiveVars = new ArrayList<>();

    private List<AdverbVarDTO> adverbVars = new ArrayList<>();

    private List<VerbVarDTO> verbVars = new ArrayList<>();

    private List<PronounVarDTO> pronounVars = new ArrayList<>();

    private List<NumeralVarDTO> numeralVars = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getHasLinkToWikipedia() {
        return hasLinkToWikipedia;
    }

    public void setHasLinkToWikipedia(Boolean hasLinkToWikipedia) {
        this.hasLinkToWikipedia = hasLinkToWikipedia;
    }

    public List<String> getMeaningQualifiers() {
        return meaningQualifiers;
    }

    public void setMeaningQualifiers(List<String> meaningQualifiers) {
        this.meaningQualifiers = meaningQualifiers;
    }

    public SentenceDTO getSentence() {
        return sentence;
    }

    public void setSentence(SentenceDTO sentence) {
        this.sentence = sentence;
    }

    public List<SynonymDTO> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<SynonymDTO> synonyms) {
        this.synonyms = synonyms;
    }

    public List<ExampleDTO> getExamples() {
        return examples;
    }

    public void setExamples(List<ExampleDTO> examples) {
        this.examples = examples;
    }

    public List<AntonymDTO> getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(List<AntonymDTO> antonyms) {
        this.antonyms = antonyms;
    }

    public List<PhraseologyDTO> getPhraseology() {
        return phraseology;
    }

    public void setPhraseology(List<PhraseologyDTO> phraseology) {
        this.phraseology = phraseology;
    }

    public List<CollocationDTO> getCollocations() {
        return collocations;
    }

    public void setCollocations(List<CollocationDTO> collocations) {
        this.collocations = collocations;
    }

    public List<CognateDTO> getCognates() {
        return cognates;
    }

    public void setCognates(List<CognateDTO> cognates) {
        this.cognates = cognates;
    }

    public List<NounVarDTO> getNounVars() {
        return nounVars;
    }

    public void setNounVars(List<NounVarDTO> nounVars) {
        this.nounVars = nounVars;
    }

    public List<AdjectiveVarDTO> getAdjectiveVars() {
        return adjectiveVars;
    }

    public void setAdjectiveVars(List<AdjectiveVarDTO> adjectiveVars) {
        this.adjectiveVars = adjectiveVars;
    }

    public List<AdverbVarDTO> getAdverbVars() {
        return adverbVars;
    }

    public void setAdverbVars(List<AdverbVarDTO> adverbVars) {
        this.adverbVars = adverbVars;
    }

    public List<VerbVarDTO> getVerbVars() {
        return verbVars;
    }

    public void setVerbVars(List<VerbVarDTO> verbVars) {
        this.verbVars = verbVars;
    }

    public List<PronounVarDTO> getPronounVars() {
        return pronounVars;
    }

    public void setPronounVars(List<PronounVarDTO> pronounVars) {
        this.pronounVars = pronounVars;
    }

    public List<NumeralVarDTO> getNumeralVars() {
        return numeralVars;
    }

    public void setNumeralVars(List<NumeralVarDTO> numeralVars) {
        this.numeralVars = numeralVars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeaningDTO that = (MeaningDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MeaningDTO{" +
            "id='" + id + '\'' +
            ", meaningQualifiers=" + meaningQualifiers +
            ", hasLinkToWikipedia=" + hasLinkToWikipedia +
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
