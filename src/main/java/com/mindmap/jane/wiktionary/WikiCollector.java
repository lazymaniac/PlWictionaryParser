package com.mindmap.jane.wiktionary;

import com.mindmap.jane.domain.*;
import com.mindmap.jane.wiktionary.dictionary.Dictionary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by Sebastian Fabisz on 2015-02-23.
 */
@Service
// TODO is this call necessary due to document based databse?
public class WikiCollector {

    public WikiCollector() {
    }

    public Set<WikiUnit> collectWikiUnits(Dictionary dictionary) {
        return dictionary.getWikiUnits();
    }

    public List<Meaning> collectImportance(Dictionary dictionary) {
        return getMeaningStream(dictionary)
            .collect(toList());
    }


    public List<AdverbVar> collectAdverbVar(Dictionary dictionary) {
        return getMeaningStream(dictionary)
            .flatMap(meaning -> meaning.getAdverbVars().stream())
            .collect(toList());
    }

    public List<VerbVar> collectVerbVar(Dictionary dictionary) {
        return getMeaningStream(dictionary)
            .flatMap(meaning -> meaning.getVerbVars().stream())
            .collect(toList());
    }

    public List<PersonVar> collectPersonVars(Dictionary dictionary) {
        return getMeaningStream(dictionary)
            .flatMap(meaning -> meaning.getVerbVars().stream())
            .flatMap(verbVar -> verbVar.getPersonVars().stream())
            .collect(toList());
    }

    public List<AdjectiveVar> collectAdjectiveVar(Dictionary dictionary) {
        return getMeaningStream(dictionary)
            .flatMap(meaning -> meaning.getAdjectiveVars().stream())
            .collect(toList());
    }

    public List<AdjectiveDegreeVar> collectAdjectiveVarDegrees(Dictionary dictionary) {
        return getMeaningStream(dictionary)
            .flatMap(meaning -> meaning.getAdjectiveVars().stream())
            .flatMap(adjectiveVar -> adjectiveVar.getAdjectiveDegreeVars().stream())
            .collect(toList());
    }

    public List<PronounVar> collectPronounVar(Dictionary dictionary) {
        return getMeaningStream(dictionary)
            .flatMap(meaning -> meaning.getPronounVars().stream())
            .collect(toList());
    }

    public List<NounVar> collectNounVar(Dictionary dictionary) {
        return getMeaningStream(dictionary)
            .flatMap(meaning -> meaning.getNounVars().stream())
            .collect(toList());
    }

    public List<CasesVar> collectCasesVars(Dictionary dictionary) {
        List<CasesVar> casesFromNouns = getMeaningStream(dictionary)
            .flatMap(meaning -> meaning.getNounVars().stream())
            .flatMap(nounVar -> nounVar.getCasesVars().stream())
            .collect(toList());

        List<CasesVar> casesFromAdjectiveDegreeVar = getMeaningStream(dictionary)
            .flatMap(meaning -> meaning.getAdjectiveVars().stream())
            .flatMap(adjectiveVar -> adjectiveVar.getAdjectiveDegreeVars().stream())
            .flatMap(adjectiveDegreeVar -> adjectiveDegreeVar.getCasesVars().stream())
            .collect(toList());

        casesFromAdjectiveDegreeVar.addAll(casesFromNouns);

        return casesFromAdjectiveDegreeVar;
    }

    public List<Cognate> collectCognates(Dictionary dictionary) {
        return getMeaningStream(dictionary)
            .flatMap(meaning -> meaning.getCognates().stream())
            .collect(toList());
    }

    public List<Collocation> collectCollocation(Dictionary dictionary) {
        return getMeaningStream(dictionary)
            .flatMap(meaning -> meaning.getCollocations().stream())
            .collect(toList());
    }

    public List<Phraseology> collectPhraseology(Dictionary dictionary) {
        return getMeaningStream(dictionary)
            .flatMap(meaning -> meaning.getPhraseology().stream())
            .collect(toList());
    }

    public List<Antonym> collectAntonyms(Dictionary dictionary) {
        return getMeaningStream(dictionary)
            .flatMap(meaning -> meaning.getAntonyms().stream())
            .collect(toList());
    }

    public List<Example> collectExamples(Dictionary dictionary) {
        return getMeaningStream(dictionary)
            .flatMap(meaning -> meaning.getExamples().stream())
            .collect(toList());
    }

    public List<Synonym> collectSynonyms(Dictionary dictionary) {
        return getMeaningStream(dictionary)
            .flatMap(meaning -> meaning.getSynonyms().stream())
            .collect(toList());
    }

    private Stream<Meaning> getMeaningStream(Dictionary dictionary) {
        return dictionary.getWikiUnits().stream()
            .flatMap(wikiUnit -> wikiUnit.getMeanings().stream());
    }

}

