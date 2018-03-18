package com.mindmap.jane.wiktionary.tagparser;

import com.mindmap.jane.domain.Link;
import com.mindmap.jane.domain.Phraseology;
import com.mindmap.jane.domain.WikiUnit;
import com.mindmap.jane.utils.LinkParser;
import com.mindmap.jane.wiktionary.numeration.Numeration;
import com.mindmap.jane.wiktionary.numeration.NumerationInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mindmap.jane.utils.NumerationUtils.getNumeration;

/**
 * {{frazeologia}}
 * : [[jabłko Adama]] • [[szczyt Adama]] • [[most Adama]] • [[na świętego Adama]] • ([[wywodzić się]]) [[od Adama i Ewy]] • [[wyjaśniać od Adama i Ewy]] • [[w stroju Adama]] • [[żebro Adama]] • [[Y-chromosomalny Adam]] • [[Adam cóż by poradził, gdyby Bóg w raju Ewy nie posadził]] • [[na Adama pięknie, jutrzenka jasna, będzie stodoła ciasna]] • [[na Adama pięknie, zima prędko pęknie]] • [[począwszy od Adama, każdy człowiek kłamie]] • [[Adam i Ewa pokazują, jaki styczeń i luty po nich następują]] • [[na Adama i Ewy, dobre bydłu i plewy]] • [[słota w dzień Adama i Ewy, zabezpiecz od zimna cholewy]] • [[w dzień Adama i Ewy daruj bliźnim gniewy]] • [[każdy Adam znajdzie sobie Ewę]]
 */
@Service
public class PhraseologyParser {

    private final static Logger log = LoggerFactory.getLogger(PhraseologyParser.class);

    private final static String template = "{{frazeologia}}";

    private final NumerationInterpreter numerationInterpreter;

    public PhraseologyParser(NumerationInterpreter numerationInterpreter) {
        this.numerationInterpreter = numerationInterpreter;
    }

    public void parsePhraseology(WikiUnit wikiUnit, List<String> dataBlock) {
        if (dataBlock == null) {
            log.error("Invalid input. Unit name: {}", wikiUnit.getName());
            return;
        }

        for (String phraseologyLine : dataBlock) {
            if (phraseologyLine.trim().equals(template)) {
                continue;
            }

            List<Numeration> numerations = numerationInterpreter.parseReferenceNumeration(wikiUnit, getNumeration(phraseologyLine));
            String[] split = phraseologyLine.split("•");

            for (String phraseology : split) {
                Link link = LinkParser.parseSimpleLink(phraseology);
                wikiUnit.addPhraseology(numerations, new Phraseology(link));
            }
        }
    }
}
