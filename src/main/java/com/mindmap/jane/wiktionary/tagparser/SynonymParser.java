package com.mindmap.jane.wiktionary.tagparser;


import com.mindmap.jane.domain.Link;
import com.mindmap.jane.domain.Synonym;
import com.mindmap.jane.domain.WikiUnit;
import com.mindmap.jane.utils.LinkParser;
import com.mindmap.jane.domain.Numeration;
import com.mindmap.jane.wiktionary.numeration.NumerationInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mindmap.jane.utils.NumerationUtils.getNumeration;

@Service
public class SynonymParser {

    private final static Logger log = LoggerFactory.getLogger(SynonymParser.class);

    private final NumerationInterpreter numerationInterpreter;

    private static final String template = "{{synonimy}}";

    public SynonymParser(NumerationInterpreter numerationInterpreter) {
        this.numerationInterpreter = numerationInterpreter;
    }

    public void parseSynonyms(WikiUnit wikiUnit, List<String> dataBlock) {
        if (dataBlock == null) {
            log.error("Invalid input. Unit name: {}", wikiUnit.getName());
            return;
        }

        for (String currentLine : dataBlock) {
            if (currentLine.trim().equals(template)) {
                continue; // if contains only template then continue to next line
            }

            List<Numeration> numerations = numerationInterpreter.parseReferenceNumeration(wikiUnit, getNumeration(currentLine));
            if (numerations == null || numerations.isEmpty())
                log.error("No numerations found. Incorrect unit: {}, dataBlock: {}", wikiUnit.getName(), dataBlock);

            List<Link> links = LinkParser.parseLinks(currentLine, false);

            for (Link link : links) {
                wikiUnit.addSynonym(numerations, new Synonym(link));
            }
        }
    }

}
