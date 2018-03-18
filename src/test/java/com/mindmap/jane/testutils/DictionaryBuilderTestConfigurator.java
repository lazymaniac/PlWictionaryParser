package com.mindmap.jane.testutils;

import com.mindmap.jane.config.ApplicationProperties;
import com.mindmap.jane.wiktionary.WikiCleaner;
import com.mindmap.jane.wiktionary.WikiRawDataParser;
import com.mindmap.jane.wiktionary.dictionary.DictionaryBuilder;
import com.mindmap.jane.wiktionary.generators.AdjectiveVarGenerator;
import com.mindmap.jane.wiktionary.generators.AdverbVarGenerator;
import com.mindmap.jane.wiktionary.numeration.NumerationInterpreter;
import com.mindmap.jane.wiktionary.tagparser.*;

import static com.mindmap.jane.testutils.ApplicationPropertiesUtils.getApplicationPropertiesForTestFile;

public class DictionaryBuilderTestConfigurator {

    public static DictionaryBuilder getDictionaryBuilderForTestFile(String pathToFile) {
        ApplicationProperties applicationProperties = getApplicationPropertiesForTestFile(pathToFile);

        WikiCleaner wikiCleaner = new WikiCleaner();
        NumerationInterpreter numerationInterpreter = new NumerationInterpreter();
        MeaningParser meaningParser = new MeaningParser(numerationInterpreter);
        VerbVarParser verbVarParser = new VerbVarParser();
        NounVarParser nounVarParser = new NounVarParser();
        AdjectiveVarParser adjectiveVarParser = new AdjectiveVarParser(new AdjectiveVarGenerator());
        AdverbVarGenerator adverbVarGenerator = new AdverbVarGenerator();
        NumeralVarParser numeralVarParser = new NumeralVarParser();
        VarietyParser varietyParser = new VarietyParser(numerationInterpreter, verbVarParser, nounVarParser, adjectiveVarParser, adverbVarGenerator, numeralVarParser);
        ExamplesParser examplesParser = new ExamplesParser(numerationInterpreter);
        CollocationsParser collocationsParser = new CollocationsParser(numerationInterpreter);
        SynonymParser synonymParser = new SynonymParser(numerationInterpreter);
        AntonymsParser antonymsParser = new AntonymsParser(numerationInterpreter);
        CognatesParser cognatesParser = new CognatesParser();
        PhraseologyParser phraseologyParser = new PhraseologyParser(numerationInterpreter);
        WikiRawDataParser wikiRawDataParser = new WikiRawDataParser(meaningParser, varietyParser, examplesParser, collocationsParser, synonymParser, antonymsParser, cognatesParser, phraseologyParser);
        DictionaryBuilder dictionaryBuilder = new DictionaryBuilder(wikiCleaner, wikiRawDataParser, applicationProperties);

        return dictionaryBuilder;
    }

}
