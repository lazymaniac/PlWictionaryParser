package com.mindmap.jane.wiktionary;

import com.codahale.metrics.annotation.Timed;
import com.mindmap.jane.domain.SourceWikiUnit;
import com.mindmap.jane.domain.WikiUnit;
import com.mindmap.jane.wiktionary.dictionary.Dictionary;
import com.mindmap.jane.wiktionary.tagparser.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.mindmap.jane.wiktionary.WikiRawDataParser.RawUnitTagTypeEnum.*;

@Service
public class WikiRawDataParser {

    private static final Logger log = LoggerFactory.getLogger(WikiRawDataParser.class);

    private final MeaningParser meaningParser;
    private final VarietyParser varietyParser;
    private final ExamplesParser examplesParser;
    private final CollocationsParser collocationsParser;
    private final SynonymParser synonymParser;
    private final AntonymsParser antonymsParser;
    private final CognatesParser cognatesParser;
    private final PhraseologyParser phraseologyParser;

    private final String BEGIN_BLOCK = "{{";

    public WikiRawDataParser(MeaningParser meaningParser, VarietyParser varietyParser, ExamplesParser examplesParser,
                             CollocationsParser collocationsParser, SynonymParser synonymParser, AntonymsParser antonymsParser,
                             CognatesParser cognatesParser, PhraseologyParser phraseologyParser) {
        this.meaningParser = meaningParser;
        this.varietyParser = varietyParser;
        this.examplesParser = examplesParser;
        this.collocationsParser = collocationsParser;
        this.synonymParser = synonymParser;
        this.antonymsParser = antonymsParser;
        this.cognatesParser = cognatesParser;
        this.phraseologyParser = phraseologyParser;
    }

    @Timed
    public void parseRawUnits(Dictionary dictionary) {
        log.info("RawUnits size: " + dictionary.getSourceWikiUnits().size());
        Set<WikiUnit> wikiUnits = new HashSet<>();

        for (SourceWikiUnit sourceWikiUnit : dictionary.getSourceWikiUnits()) {
            if (StringUtils.isBlank(sourceWikiUnit.getText())) {
                continue;
            }

            WikiUnit wikiUnit = new WikiUnit();
            wikiUnit.setName(sourceWikiUnit.getTitle());

            Map<RawUnitTagTypeEnum, List<String>> indexesOfBlocks = findIndexesOfBlocks(sourceWikiUnit.getText());
            parseBlocks(wikiUnit, indexesOfBlocks);

            wikiUnits.add(wikiUnit);
        }

        log.info("Formatted units size:" + wikiUnits.size());
        dictionary.setWikiUnits(wikiUnits);
    }

    private void parseBlocks(WikiUnit wikiUnit, Map<RawUnitTagTypeEnum, List<String>> indexesOfBlocks) {
        meaningParser.parseMeaning(wikiUnit, indexesOfBlocks.get(ZNACZENIA));
        varietyParser.parse(wikiUnit, indexesOfBlocks.get(ODMIANA));
        examplesParser.parserExamples(wikiUnit, indexesOfBlocks.get(PRZYKLADY));
        collocationsParser.parseCollocation(wikiUnit, indexesOfBlocks.get(KOLOKACJE));
        synonymParser.parseSynonyms(wikiUnit, indexesOfBlocks.get(SYNONIMY));
        antonymsParser.parseAntonym(wikiUnit, indexesOfBlocks.get(ANTONIMY));
        cognatesParser.parseCognate(wikiUnit, indexesOfBlocks.get(POKREWNE));
        phraseologyParser.parsePhraseology(wikiUnit, indexesOfBlocks.get(FRAZEOLOGIA));
    }

    private Map<RawUnitTagTypeEnum, List<String>> findIndexesOfBlocks(String rawUnitText) {

        Map<RawUnitTagTypeEnum, List<String>> blockTypeMap = new HashMap<>();

        String[] unitTextLines = rawUnitText.split("\n");

        RawUnitTagTypeEnum blockType = null;
        List<String> currentBlockData = new ArrayList<>();
        boolean anyBlockTypeMatched = false;
        for (int i = 0; i < unitTextLines.length; i++) {
            String currentLine = unitTextLines[i];
            // skip unit title (== dom {{język polski}})
            if (currentLine.startsWith("==")) {
                continue;
            }

            if (currentLine.startsWith(BEGIN_BLOCK)) {
                anyBlockTypeMatched = false;
                for (RawUnitTagTypeEnum rawUnitTagTypeEnum : RawUnitTagTypeEnum.values()) {
                    if (currentLine.startsWith(rawUnitTagTypeEnum.value)) {
                        anyBlockTypeMatched = true;
                        if (blockType == null) {
                            // set type of first tag
                            blockType = rawUnitTagTypeEnum;
                        } else {
                            // if found another tag put data to map
                            blockTypeMap.put(blockType, new ArrayList<>(currentBlockData));
                            blockType = rawUnitTagTypeEnum;
                            currentBlockData.clear();
                        }
                        break;
                    }
                }
            }
            if (anyBlockTypeMatched) {
                currentBlockData.add(currentLine);
            }
            if (i == unitTextLines.length - 1) {
                blockTypeMap.put(blockType, new ArrayList<>(currentBlockData));
            }
        }

        return blockTypeMap;
    }

    enum RawUnitTagTypeEnum {
        PODOBNE("{{podobne"),
        ZNACZENIA("{{znaczenia"),
        ODMIANA("{{odmiana"),
        PRZYKLADY("{{przykłady"),
        SKLADNIA("{{składnia"),
        KOLOKACJE("{{kolokacje"),
        SYNONIMY("{{synonimy"),
        ANTONIMY("{{antonimy"),
        HIPERONIMY("{{hiperonimy"),
        HIPONIMY("{{hiponimy"),
        HOLONIMY("{{holonimy"),
        MERONIMY("{{meronimy"),
        POKREWNE("{{pokrewne"),
        FRAZEOLOGIA("{{frazeologia");

        private final String value;

        RawUnitTagTypeEnum(String value) {
            this.value = value;
        }
    }


}
