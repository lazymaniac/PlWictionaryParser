package com.mindmap.jane.wiktionary.tagparser;

import com.mindmap.jane.domain.Collocation;
import com.mindmap.jane.domain.Numeration;
import com.mindmap.jane.domain.Sentence;
import com.mindmap.jane.domain.WikiUnit;
import com.mindmap.jane.utils.RegexConsts;
import com.mindmap.jane.utils.SentenceParser;
import com.mindmap.jane.wiktionary.numeration.NumerationInterpreter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mindmap.jane.utils.NumerationUtils.getNumeration;
import static com.mindmap.jane.utils.NumerationUtils.removeNumerationReference;

/**
 * Szablon: {{kolokacje}}
 * hasło Perseusz
 * {{kolokacje}}
 * : (1.1) [[heros]] Perseusz • [[dzielny]] / [[mężny]] / [[nieustraszony]] / [[odważny]] Perseusz
 * : (2.1) Perseusz [[górować|góruje]] / [[widnieć|widnieje]] [[na]] [[niebo|niebie]] • Perseusz [[być|jest]] [[widoczny]] / [[widzialny]] • [[podziwiać]] / [[obserwować]] / [[zobaczyć]] Perseusza • [[leżeć]] / [[znajdować się]] [[w]] Perseuszu
 */
@Service
public class CollocationsParser {

    private final static Logger log = LoggerFactory.getLogger(CollocationsParser.class);

    private final static String template = "{{kolokacje}}";

    private final static String firstVariantRegex = "(\\[\\[[" + RegexConsts.ALPHABET + "|\\s0-9]*]])( /)";
    private final static String nextVariantsRegex = "(/ )(\\[\\[[" + RegexConsts.ALPHABET + "|\\s0-9]*]])";

    private final static Pattern firstVariantPattern = Pattern.compile(firstVariantRegex);
    private final static Pattern nextVariantsPattern = Pattern.compile(nextVariantsRegex);

    private final NumerationInterpreter numerationInterpreter;

    public CollocationsParser(NumerationInterpreter numerationInterpreter) {
        this.numerationInterpreter = numerationInterpreter;
    }

    public void parseCollocation(WikiUnit wikiUnit, List<String> dataBlock) {
        if (dataBlock == null) {
            log.error("Invalid input. Unit name: {}", wikiUnit.getName());
            return;
        }

        for (String currentLine : dataBlock) {
            if (currentLine.trim().equals(template)) {
                continue; // continue if line contains only template
            }

            List<Numeration> numerations = numerationInterpreter.parseReferenceNumeration(wikiUnit, getNumeration(currentLine));

            try {
                for (String collocation : currentLine.split("•")) {
                    for (String collocationSentence : prepareForSentenceParser(wikiUnit.getName(), removeNumerationReference(collocation.trim()))) {
                        Sentence sentence = SentenceParser.parseSentence(collocationSentence);
                        wikiUnit.addCollocation(numerations, new Collocation(sentence));
                    }
                }
            } catch (Exception ex) {
                log.error("Error in collocation parser. Unit name: {}, input: {}", wikiUnit.getName(), dataBlock);
                log.error("Exception: ", ex);
            }
        }
    }

    private List<String> prepareForSentenceParser(String unitName, String collocation) {
        List<String> result = new ArrayList<>();

        Matcher firstVariantMatcher = firstVariantPattern.matcher(collocation);
        Matcher nextVariantsMatcher = nextVariantsPattern.matcher(collocation);

        String firstVariant = "";
        List<String> variants = new ArrayList<>();

        while (firstVariantMatcher.find()) {
            firstVariant = firstVariantMatcher.group(1);
            if (StringUtils.isNotBlank(firstVariant)) {
                variants.add(firstVariant);
                break;
            }
        }
        if (StringUtils.isNotBlank(firstVariant)) {
            while (nextVariantsMatcher.find()) {
                String nextVariant = nextVariantsMatcher.group(2);
                if (StringUtils.isNotBlank(nextVariant)) {
                    variants.add(nextVariant);
                }
            }
            int indexOfFirstVariant = 0;
            if (StringUtils.isNotBlank(firstVariant)) {
                indexOfFirstVariant = collocation.indexOf(firstVariant);
            }

            int indexOfEndOfLastVariant = 0;
            if (!variants.isEmpty()) {
                String lastVariant = variants.get(variants.size() - 1);
                indexOfEndOfLastVariant = collocation.lastIndexOf(lastVariant) + lastVariant.length();
            }

            if (indexOfFirstVariant > -1 && indexOfEndOfLastVariant > 0) {
                for (String variant : variants) {
                    result.add(collocation.replace(collocation.substring(indexOfFirstVariant, indexOfEndOfLastVariant), variant));
                }
            }
        } else {
            result.add(collocation);
        }
        return result;
    }

}
