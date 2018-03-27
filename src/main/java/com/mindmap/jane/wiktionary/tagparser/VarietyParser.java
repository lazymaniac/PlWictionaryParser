package com.mindmap.jane.wiktionary.tagparser;

import com.mindmap.jane.domain.*;
import com.mindmap.jane.wiktionary.generators.AdverbVarGenerator;
import com.mindmap.jane.wiktionary.numeration.NumerationInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mindmap.jane.utils.NumerationUtils.getNumeration;

@Service
public class VarietyParser {

    private final Logger log = LoggerFactory.getLogger(VarietyParser.class);

    private final NumerationInterpreter numerationInterpreter;
    private final VerbVarParser verbVarParser;
    private final NounVarParser nounVarParser;
    private final AdjectiveVarParser adjectiveVarParser;
    private final AdverbVarGenerator adverbVarParser;
    private final NumeralVarParser numeralVarParser;

    private final String colon = ":";
    private final String ODMIANA_TAG = "{{odmiana}}";
    private final String END_OD_BLOCK = "}}";


    public VarietyParser(NumerationInterpreter numerationInterpreter, VerbVarParser verbVarParser, NounVarParser nounVarParser, AdjectiveVarParser adjectiveVarParser, AdverbVarGenerator adverbVarParser, NumeralVarParser numeralVarParser) {
        this.numerationInterpreter = numerationInterpreter;
        this.verbVarParser = verbVarParser;
        this.nounVarParser = nounVarParser;
        this.adjectiveVarParser = adjectiveVarParser;
        this.adverbVarParser = adverbVarParser;
        this.numeralVarParser = numeralVarParser;
    }

    public void parse(WikiUnit wikiUnit, List<String> variety) {
        if (variety == null) {
            log.error("Invalid input. Unit name: {}", wikiUnit.getName());
        }

        try {
            Map<PartOfSpeechTagEnum, List<String>> blockOfVariety = findBlockOfVariety(variety);
            parseVariety(wikiUnit, blockOfVariety);
        } catch (Exception ex) {
            log.error("Problem with variety: {}, {}", wikiUnit, variety, ex);
        }

    }


    private Map<PartOfSpeechTagEnum, List<String>> findBlockOfVariety(List<String> varietyBlock) {
        Map<PartOfSpeechTagEnum, List<String>> blocksOfVariety = new HashMap<>();

        PartOfSpeechTagEnum blockType = null;
        List<String> currentBlock = new ArrayList<>();
        for (int i = 0; i < varietyBlock.size(); i++) {
            String currentLine = varietyBlock.get(i);

            if (currentLine.equals(ODMIANA_TAG) || currentLine.equals(END_OD_BLOCK)) {
                continue; // if it's beginning or ending of "odmiana" tag - skip
            }

            if (currentLine.startsWith(colon)) { // only lines starting with colon contains variety type tag.
                for (PartOfSpeechTagEnum partOfSpeechTagEnum : PartOfSpeechTagEnum.values()) {
                    if (currentLine.contains(partOfSpeechTagEnum.value)) {
                        if (blockType == null) { // set type of first part of speech
                            blockType = partOfSpeechTagEnum;
                        } else { // if found next tag put data to map
                            blocksOfVariety.put(blockType, new ArrayList<>(currentBlock));
                            blockType = partOfSpeechTagEnum;
                            currentBlock.clear();
                        }
                        break; // break loop. Tag found
                    }
                }
            }
            currentBlock.add(currentLine);
            if (i == varietyBlock.size() - 2 && blockType != null) {
                blocksOfVariety.put(blockType, currentBlock);
            }
        }

        return blocksOfVariety;
    }

    private void parseVariety(WikiUnit wikiUnit, Map<PartOfSpeechTagEnum, List<String>> blocksOfVariety) {
        blocksOfVariety.forEach((partOfSpeechTagEnum, varietyBlock) -> {
            List<Numeration> numerations = numerationInterpreter.parseReferenceNumeration(wikiUnit, getNumeration(varietyBlock.get(0))); //first line block contains numerations

            switch (partOfSpeechTagEnum) {
                case CZASOWNIK:
                    parseVerb(wikiUnit, numerations, varietyBlock);
                    break;
                case RZECZOWNIK:
                    parseNoun(wikiUnit, numerations, varietyBlock);
                    break;
                case LICZEBNIK:
                    parseNumeral(wikiUnit, numerations, varietyBlock);
                    break;
                case PRZYMIOTNIK:
                    parseAdjective(wikiUnit, numerations, varietyBlock);
                    break;
                case PRZYSLOWEK:
                    parseAdverb(wikiUnit, numerations);
                    break;
            }
        });

    }

    private void parseNoun(WikiUnit wikiUnit, List<Numeration> numerations, List<String> varietyBlock) {
        NounVar nounVar = nounVarParser.parse(wikiUnit, varietyBlock);
        wikiUnit.addNounVar(numerations, nounVar);
    }

    private void parseVerb(WikiUnit wikiUnit, List<Numeration> numerations, List<String> varietyBlock) {
        VerbVar verbVar = verbVarParser.parse(wikiUnit, varietyBlock);
        wikiUnit.addVerbVar(numerations, verbVar);

    }

    private void parseAdjective(WikiUnit wikiUnit, List<Numeration> numerations, List<String> varietyBlock) {
        AdjectiveVar adjectiveVar = adjectiveVarParser.parseAdjective(wikiUnit, varietyBlock);
        wikiUnit.addAdjectiveVar(numerations, adjectiveVar);
    }


    private void parseAdverb(WikiUnit wikiUnit, List<Numeration> numerations) {
        AdverbVar adverbVar = adverbVarParser.generate(wikiUnit);
        wikiUnit.addAdverbVar(numerations, adverbVar);
    }

    private void parseNumeral(WikiUnit wikiUnit, List<Numeration> numerations, List<String> varietyBlock) {
        NumeralVar numeralVar = numeralVarParser.parse(wikiUnit, varietyBlock);
        wikiUnit.addNumeralVar(numerations, numeralVar);
    }

    enum PartOfSpeechTagEnum {
        RZECZOWNIK("odmiana-rzeczownik"),
        CZASOWNIK("odmiana-czasownik"),
        PRZYMIOTNIK("odmiana-przymiotnik"),
        PRZYSLOWEK("stopn|"),
        LICZEBNIK("liczebnik");

        private final String value;

        PartOfSpeechTagEnum(String value) {
            this.value = value;
        }
    }
}
