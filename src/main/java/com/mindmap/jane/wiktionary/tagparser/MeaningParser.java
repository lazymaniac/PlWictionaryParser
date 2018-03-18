package com.mindmap.jane.wiktionary.tagparser;

import com.mindmap.jane.domain.Meaning;
import com.mindmap.jane.domain.Sentence;
import com.mindmap.jane.domain.WikiUnit;
import com.mindmap.jane.utils.NumerationUtils;
import com.mindmap.jane.utils.SentenceParser;
import com.mindmap.jane.utils.WikiStringUtils;
import com.mindmap.jane.wiktionary.numeration.Numeration;
import com.mindmap.jane.wiktionary.numeration.NumerationInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mindmap.jane.utils.NumerationUtils.isNumerationLine;
import static com.mindmap.jane.utils.RegexConsts.ALPHABET;
import static java.util.stream.Collectors.*;

/**
 * {{znaczenia}}
 * ''rzeczownik, rodzaj męskoosobowy''
 * : (1.1) {{starop}} [[Maciej]]<ref>Jan Miodek, ''Rozmyślajcie nad mową!'', Prószyński i S-ka, Warszawa 1999, s. 151.</ref>
 * <p>
 * {{znaczenia}}
 * ''rzeczownik, rodzaj żeński''
 * : (1.1) {{hist}} ''[[w]] [[starożytny]]m [[Rzym]]ie'': [[pora]] [[rok]]u, [[w]] [[który|której]] [[Słońce]] [[znajdować się|znajduje się]] [[w]] [[gwiazdozbiór|gwiazdozbiorze]] Psa, [[czyli]] [[od]] [[22]] [[czerwiec|czerwca]] [[do]] [[23]] [[sierpień|sierpnia]]
 * : (1.2) {{książk}} [[gorący|najgorętszy]] [[okres]] [[lato|lata]]
 * <p>
 * {{znaczenia}}
 * ''przymiotnik''
 * : (1.1) [[związany]] [[z]] [[powietrze]]m, [[dotyczyć|dotyczący]] [[powietrze|powietrza]]
 * : (1.2) [[taki]], [[który]] [[odbywać|odbywa]] [[się]] [[w]] [[powietrze|powietrzu]], [[na]] [[pewien|pewnej]] [[wysokość|wysokości]]
 */
@Service
public class MeaningParser {

    private final static Logger log = LoggerFactory.getLogger(MeaningParser.class);

    private final static String template = "{{znaczenia}}";

    private final static String qualifierRegex = "(\\{\\{)([" + ALPHABET + ".\\-\\s0-9]*)(}})";
    private final Pattern qualifierPattern = Pattern.compile(qualifierRegex);

    private final static String comma = ",";

    private NumerationInterpreter numerationInterpreter;

    @Autowired
    public MeaningParser(NumerationInterpreter numerationInterpreter) {
        this.numerationInterpreter = numerationInterpreter;
    }

    public void parseMeaning(WikiUnit wikiUnit, List<String> dataBlock) {
        if (dataBlock == null) {
            log.error("Invalid input. Unit name: {}", wikiUnit.getName());
            return;
        }

        List<String> qualifiers = new ArrayList<>();
        for (String currentLine : dataBlock) {
            if (template.equals(currentLine.trim()))
                continue;

            String cleaned = WikiStringUtils.removeReferences(currentLine);
            if (isQualifiersLine(currentLine)) {
                qualifiers = getQualifiers(cleaned);
            }
            if (isNumerationLine(currentLine)) {
                Numeration numeration = parseNumeration(wikiUnit, cleaned);

                if (numeration != null) { // numeration is invalid so no other element can be connected with this meaning.
                    Sentence sentence = parseSentence(cleaned);
                    List<String> meaningQualifiers = parseMeaningQualifiers(currentLine);
                    Meaning meaning = buildMeaning(sentence, qualifiers, meaningQualifiers, numeration);
                    wikiUnit.addMeaning(meaning);
                }
            }
        }
    }

    private List<String> getQualifiers(String currentLine) {
        String qualifiersLine = WikiStringUtils.removeApostrophes(currentLine);
        String[] qualifiersArray = qualifiersLine.split(comma);
        return Arrays.stream(qualifiersArray)
            .map(String::trim)
            .collect(toList());
    }

    private Numeration parseNumeration(WikiUnit wikiUnit, String currentLine) {
        String numerationStr = NumerationUtils.getNumeration(currentLine);
        Numeration numeration = numerationInterpreter.parseMeaningNumeration(wikiUnit, numerationStr);

        if (numeration == null) {
            log.error("Incorrect meaning. No numeration. Unit name: {}, input data: {}", wikiUnit.getName(), currentLine);
            return null;
        }

        return numeration;
    }

    private Sentence parseSentence(String currentLine) {
        String sentence = removeNumerationAndQualifiers(currentLine);
        return SentenceParser.parseSentence(sentence.trim());
    }

    private List<String> parseMeaningQualifiers(String currentLine) {
        Matcher qualifierMatcher = qualifierPattern.matcher(currentLine);

        List<String> result = new ArrayList<>();
        while (qualifierMatcher.find()) {
            String group = qualifierMatcher.group(2);
            result.add(group);
        }

        return result;
    }

    private Meaning buildMeaning(Sentence sentence, List<String> partOfSpeechQualifiers, List<String> meaningQualifiers, Numeration numeration) {
        Meaning meaning = new Meaning();
        meaning.setSentence(sentence);
        meaning.setNumeration(numeration);
        meaning.setPartOfSpeechQualifiers(partOfSpeechQualifiers);
        meaning.setMeaningQualifiers(meaningQualifiers);
        return meaning;
    }

    private String removeNumerationAndQualifiers(String currentLine) {
        String removedNumeration = NumerationUtils.removeNumeration(currentLine);
        Matcher qualifierMatcher = qualifierPattern.matcher(removedNumeration);
        return qualifierMatcher.replaceAll("");
    }

    private boolean isQualifiersLine(String line) {
        return line.startsWith("''");
    }

}
