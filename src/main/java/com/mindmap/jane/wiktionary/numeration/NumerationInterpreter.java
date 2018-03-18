package com.mindmap.jane.wiktionary.numeration;


import com.mindmap.jane.domain.Meaning;
import com.mindmap.jane.domain.WikiUnit;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mindmap.jane.wiktionary.numeration.NumerationInterpreter.RangeEnum.*;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang.StringUtils.*;

/**
 * Numeration examples:
 * - (1.1)
 * - (1.1-3)
 * - (1.1,3)
 * - (1-3)
 * - (1,3)
 * - (1.1, 2.3)
 * - (1-2, 3.3)
 */
@Service
public class NumerationInterpreter {

    private final static Logger log = LoggerFactory.getLogger(NumerationInterpreter.class);

    // pattern
    private final static String numerationRegex = "([0-9]+)([.\\-])?([0-9]+)?([.\\-])?([0-9]+)?(,[0-9]+)*";
    private final static Pattern numerationPattern = Pattern.compile(numerationRegex);

    // groups
    private final int PART_OF_SPEECH_GRP = 1;
    private final int PART_OF_SPEECH_RANGE_GRP = 2;
    private final int MEANING_1_GRP = 3;
    private final int MEANING_RANGE_GRP = 4;
    private final int MEANING_2_GRP = 5;
    private final int NUM_ARRAY_GRP = 6;

    /**
     * Parse numeration in format (1.2). If input is empty it means current block is assigned to all meanings.
     *
     * @param wikiUnit        should be not null
     * @param inputNumeration
     * @return array of numerations
     */
    public List<Numeration> parseReferenceNumeration(WikiUnit wikiUnit, String inputNumeration) {
        if (inputIncorrect(wikiUnit)) return null;

        // if numeration is empty it means element is assigned to all meanings.
        if (isBlank(inputNumeration)) {
            return generateNumerationForAllMeanings(wikiUnit);
        }

        List<Numeration> result = new ArrayList<>();

        String cleanNumeration = removeBrackets(inputNumeration);
        String[] numerations = cleanNumeration.split("\\s");

        try {
            for (String numeration : numerations) {
                result.addAll(parseSingleNumeration(wikiUnit, numeration.trim()));
            }
        } catch (Exception ex) {
            log.error("Problem paring reference numeration for unitName: {}, input: {}", wikiUnit.getName(), inputNumeration);
            log.error("Error: ", ex);
        }

        return result;
    }

    /**
     * Parse reference to meaning. Input format (1.1). Should return only one Numeration.
     *
     * @param wikiUnit
     * @param inputNumeration e.g. (1.1)
     * @return
     */
    public Numeration parseMeaningNumeration(WikiUnit wikiUnit, String inputNumeration) {
        if (inputIncorrect(wikiUnit)) return null;

        if (isEmpty(inputNumeration)) {
            return null;
        }

        String cleanNumeration = removeBrackets(inputNumeration);
        List<Numeration> numerations = null;
        try {
            numerations = parseSingleNumeration(wikiUnit, cleanNumeration.trim());
        } catch (Exception ex) {
            log.error("Problem paring meaning numeration for unitName: {}, input: {}", wikiUnit.getName(), inputNumeration);
            log.error("Error: ", ex);
        }

        if (numerations == null || numerations.isEmpty() || numerations.size() > 1) {
            log.error("Incorrect Meaning numeration. Unit name: {}, input data: {}", wikiUnit.getName(), inputNumeration);
            return null;
        }

        return numerations.get(0);
    }

    /**
     * Create matcher for numeration string and assign groups to fields for further processing. If input match pattern
     * log error.
     *
     * @param wikiUnit
     * @param trimmed
     */
    private List<Numeration> parseSingleNumeration(WikiUnit wikiUnit, String trimmed) {
        List<Numeration> result = new ArrayList<>();

        Matcher numerationMatcher = numerationPattern.matcher(trimmed);

        boolean numerationFound = false;
        while (numerationMatcher.find()) {
            numerationFound = true;

            Byte partOfSpeech = parseByte(numerationMatcher.group(PART_OF_SPEECH_GRP));
            RangeEnum partOfSpeechRange = getRangeEnum(numerationMatcher.group(PART_OF_SPEECH_RANGE_GRP));
            Byte meaning1 = parseByte(numerationMatcher.group(MEANING_1_GRP));
            RangeEnum meaningRange = getRangeEnum(numerationMatcher.group(MEANING_RANGE_GRP));
            Byte meaning2 = parseByte(numerationMatcher.group(MEANING_2_GRP));
            List<Byte> numArray = parseByteArray(numerationMatcher.group(NUM_ARRAY_GRP));

            result.addAll(generateNumerations(wikiUnit, partOfSpeech, partOfSpeechRange, meaning1, meaningRange, meaning2, numArray));
        }

        if (!numerationFound) {
            log.error("Numeration not found. Input: {}. Unit name: {}", trimmed, wikiUnit.getName());
        }

        return result;
    }

    /**
     * Iterate over all Meanings and generate Numeration.
     *
     * @param wikiUnit
     * @return
     */
    private List<Numeration> generateNumerationForAllMeanings(WikiUnit wikiUnit) {
        List<Numeration> result = new ArrayList<>();
        for (Meaning meaning : wikiUnit.getMeanings()) {
            result.add(meaning.getNumeration());
        }

        return result;
    }

    /**
     * Convert input string numeration fields and start processing.
     *
     * @return
     */
    private List<Numeration> generateNumerations(WikiUnit wikiUnit, Byte partOfSpeechIndex, RangeEnum range1, Byte num2,
                                                 RangeEnum range2, Byte num3, List<Byte> rangeArray) {
        List<Numeration> numerations = new ArrayList<>();

        if (range1 != null && range1.equals(DIRECT)) {
            numerations.addAll(generateDirectNumeration(partOfSpeechIndex, num2, range2, num3, rangeArray));
        }

        if (range1 != null && range1.equals(RANGE)) {
            numerations.addAll(generateRangeNumeration(wikiUnit, partOfSpeechIndex, num2, rangeArray));
        }

        if (partOfSpeechIndex != null && num2 == null && num3 == null) {
            numerations.addAll(generateSimpleNumeration(wikiUnit, partOfSpeechIndex, rangeArray));
        }

        return numerations;
    }


    private List<Numeration> generateSimpleNumeration(WikiUnit wikiUnit, Byte partOfSpeechIndex, List<Byte> numArray) {
        List<Numeration> result = new ArrayList<>();

        if (wikiUnit.getMeanings().stream()
            .map(Meaning::getNumeration)
            .noneMatch(numeration -> numeration.getPartOfSpeech() == partOfSpeechIndex)) {
            log.error("Error during parsing numeration. Range to big. Unit name: {}", wikiUnit.getName());
            return result;
        }

        if (numArray == null || numArray.isEmpty()) {
            generateNumerationForPartOfSpeech(wikiUnit, partOfSpeechIndex, result);
        } else {
            generateNumerationForPartOfSpeech(wikiUnit, partOfSpeechIndex, result);
            for (byte partOfSpeech : numArray) {
                generateNumerationForPartOfSpeech(wikiUnit, partOfSpeech, result);
            }
        }

        return result;
    }

    private void generateNumerationForPartOfSpeech(WikiUnit wikiUnit, Byte partOfSpeechIndex, List<Numeration> result) {
        int meaningsSize = wikiUnit.getMeaningsByPartOfSpeechNumber(partOfSpeechIndex).size();

        for (byte j = 1; j < meaningsSize + 1; j++) {
            result.add(new Numeration(partOfSpeechIndex, j));
        }
    }

    private List<Numeration> generateRangeNumeration(WikiUnit wikiUnit, Byte num1, Byte num2, List<Byte> array) {
        List<Numeration> result = new ArrayList<>();

        if (wikiUnit.getMeanings().stream()
            .map(Meaning::getNumeration)
            .filter(Objects::nonNull)
            .noneMatch(numeration -> numeration.getPartOfSpeech() == num1) ||
            wikiUnit.getMeanings().stream()
                .map(Meaning::getNumeration)
                .filter(Objects::nonNull)
                .noneMatch(numeration -> numeration.getPartOfSpeech() == num2)) {
            log.error("Error during parsing numeration. Range to big. Unit name: {}", wikiUnit.getName());
            return result;
        }

        for (byte i = num1; i < num2 + 1; i++) {
            int meaningSize = wikiUnit.getMeaningsByPartOfSpeechNumber(i).size();

            for (byte j = 1; j < meaningSize + 1; j++) {
                result.add(new Numeration(i, j));
            }
        }

        return result;
    }

    private List<Numeration> generateDirectNumeration(Byte num1, Byte num2, RangeEnum range2, Byte num3, List<Byte> rangeArray) {
        List<Numeration> result = new ArrayList<>();

        // 1.2
        if (range2 == null && rangeArray == null) {
            result.add(new Numeration(num1, num2));
        }

        // 1.2-4
        if (range2 != null && range2.equals(RANGE) && num3 != null) {
            for (byte meaningIndex = num2; meaningIndex < num3 + 1; meaningIndex++) {
                result.add(new Numeration(num1, meaningIndex));
            }
        }

        // 1.2,4,6
        if (range2 == null && num2 != null && rangeArray != null) {
            result.add(new Numeration(num1, num2));
            for (byte meaningIndex : rangeArray) {
                result.add(new Numeration(num1, meaningIndex));
            }
        }

        return result;
    }

    private boolean inputIncorrect(WikiUnit wikiUnit) {
        return wikiUnit == null;
    }

    private Byte parseByte(String numStr) {
        if (isNotBlank(numStr) && numStr.length() > 3) {
            return null;
        }
        return isNotBlank(numStr) ? Byte.parseByte(numStr) : null;
    }

    private RangeEnum getRangeEnum(String range) {
        return enumOf(range);
    }

    private List<Byte> parseByteArray(String array) {
        if (array == null) {
            return null;
        }
        String[] split = array.split(",");
        return Arrays.stream(split)
            .filter(StringUtils::isNotBlank)
            .map(Byte::parseByte)
            .collect(toList());
    }

    private String removeBrackets(String input) {
        if (isEmpty(input)) {
            log.error("Empty numeration: {}", input);
            return input;
        }
        return input.replaceAll("([()]+)", EMPTY);
    }

    enum RangeEnum {

        DIRECT("."),
        RANGE("-"),
        ARRAY(",");

        private final String value;

        RangeEnum(String value) {
            this.value = value;
        }

        public static RangeEnum enumOf(String value) {
            for (RangeEnum rangeEnum : RangeEnum.values()) {
                if (rangeEnum.value.equals(value)) {
                    return rangeEnum;
                }
            }
            return null;
        }
    }
}
