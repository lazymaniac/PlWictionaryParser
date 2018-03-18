package com.mindmap.jane.wiktionary.tagparser;

import com.mindmap.jane.domain.Cognate;
import com.mindmap.jane.domain.Link;
import com.mindmap.jane.domain.WikiUnit;
import com.mindmap.jane.domain.enumeration.CognateNounTypesQualifier;
import com.mindmap.jane.domain.enumeration.CognatePartOfSpeechQualifier;
import com.mindmap.jane.utils.LinkParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mindmap.jane.utils.RegexConsts.ALPHABET;

/**
 * Example:
 * Unit: tęskny
 * {{pokrewne}}
 * : {{rzecz}} [[tęsknota]] {{f}}, [[tęsknotka]] {{f}}, [[tęsknica]] {{f}}, [[tęskność]] {{f}}, [[tęskliwość]] {{f}}, [[tęsknienie]] {{n}}, [[utęsknienie]] {{n}}
 * : {{czas}} [[tęsknić]] {{ndk}}, [[zatęsknić]] {{dk}}
 * : {{przysł}} [[tęsknie]], [[tęskno]], [[tęskliwie]]
 * <p>
 * Unit: współpracownik
 * {{pokrewne}}
 * : {{rzecz}} [[współpraca]] {{f}}, [[współpracowanie]] {{n}}
 * :: {{fż}} [[współpracowniczka]] {{f}}
 * : {{czas}} [[współpracować]] {{ndk}}
 */
@Service
public class CognatesParser {

    private final static Logger log = LoggerFactory.getLogger(CognatesParser.class);

    private final static String template = "{{pokrewne}}";

    private final static String qualifierReqex = "(\\{\\{)([" + ALPHABET + "\\s0-9-]*)(}})";

    private final static Pattern qualifierPattern = Pattern.compile(qualifierReqex);

    public CognatesParser() {
    }

    public void parseCognate(WikiUnit wikiUnit, List<String> dataBlock) {
        if (dataBlock == null) {
            log.error("Invalid input. Unit name: {}", wikiUnit.getName());
            return;
        }

        CognatePartOfSpeechQualifier currentPartOfSpeechQualifier = null;
        for (String currentLine : dataBlock) {
            if (currentLine.trim().equals(template)) {
                continue; // if current line contains only template skip line
            }
            CognatePartOfSpeechQualifier cognatePartOfSpeechQualifier = parsePartOfSpeechQualifier(currentLine);
            if (cognatePartOfSpeechQualifier != null) { // if null - it was set in previous line.
                currentPartOfSpeechQualifier = cognatePartOfSpeechQualifier;
            }
            parserCognate(wikiUnit, currentLine, currentPartOfSpeechQualifier);
        }
    }

    private void parserCognate(WikiUnit wikiUnit, String currentLine, CognatePartOfSpeechQualifier partOfSpeechQualifier) {
        String[] split = currentLine.split(",");

        for (String cognate : split) {
            Link link = parseLink(cognate, wikiUnit);
            CognateNounTypesQualifier nounTypesQualifier = null;
            if (CognatePartOfSpeechQualifier.RZECZOWNIK.equals(partOfSpeechQualifier)) {
                nounTypesQualifier = parseNounType(cognate);
            }
            parserStyleQualifier(cognate, nounTypesQualifier, partOfSpeechQualifier);
            List<String> cognateQualifiers = parserStyleQualifier(cognate, nounTypesQualifier, partOfSpeechQualifier);
            wikiUnit.addCognate(new Cognate(link, nounTypesQualifier, partOfSpeechQualifier, cognateQualifiers));
        }
    }

    private CognatePartOfSpeechQualifier parsePartOfSpeechQualifier(String currentLine) {
        Matcher qualifierMatcher = qualifierPattern.matcher(currentLine);

        while (qualifierMatcher.find()) {
            String qualifier = qualifierMatcher.group(2);
            CognatePartOfSpeechQualifier partOfSpeechQualifier = CognatePartOfSpeechQualifier.enumOf(qualifier);
            if (partOfSpeechQualifier != null) {
                return partOfSpeechQualifier;
            }
        }
        return null;
    }

    private Link parseLink(String input, WikiUnit wikiUnit) {
        Link link = LinkParser.parseSimpleLink(input);

        if (link == null) {
            log.error("Could not find link. Unit: {}, input data: {}", wikiUnit.getName(), input);
        }

        return link;
    }

    private CognateNounTypesQualifier parseNounType(String input) {
        Matcher qualifierMatcher = qualifierPattern.matcher(input);

        while (qualifierMatcher.find()) {
            String qualifier = qualifierMatcher.group(2);
            CognateNounTypesQualifier cognateNounTypesQualifier = CognateNounTypesQualifier.enumOf(qualifier);
            if (cognateNounTypesQualifier != null) {
                return cognateNounTypesQualifier;
            }
        }

        return null;
    }

    private List<String> parserStyleQualifier(String input, CognateNounTypesQualifier nounTypesQualifier, CognatePartOfSpeechQualifier partOfSpeechQualifier) {
        Matcher qualifierMatcher = qualifierPattern.matcher(input);

        List<String> qualifiers = new ArrayList<>();

        while (qualifierMatcher.find()) {
            String qualifier = qualifierMatcher.group(2);
            if ((nounTypesQualifier == null || !nounTypesQualifier.equals(CognateNounTypesQualifier.enumOf(qualifier))) &&
                (partOfSpeechQualifier != null && !partOfSpeechQualifier.equals(CognatePartOfSpeechQualifier.enumOf(qualifier)))) {
                qualifiers.add(qualifier);
            }
        }

        return qualifiers;
    }
}
