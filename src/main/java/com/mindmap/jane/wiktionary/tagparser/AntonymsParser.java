package com.mindmap.jane.wiktionary.tagparser;

import com.mindmap.jane.domain.Antonym;
import com.mindmap.jane.domain.Link;
import com.mindmap.jane.domain.WikiUnit;
import com.mindmap.jane.utils.LinkParser;
import com.mindmap.jane.wiktionary.numeration.Numeration;
import com.mindmap.jane.wiktionary.numeration.NumerationInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mindmap.jane.utils.NumerationUtils.getNumeration;

/**
 * Szablon:
 * {{antonimy}}
 * : (1.1) [[antonim]], [[antonim]]
 * : (1.2) [[antonim]], [[antonim]]
 * : (1.3) [[antonim]]
 */
@Service
public class AntonymsParser {

    private static final Logger log = LoggerFactory.getLogger(AntonymsParser.class);

    private static final String template = "{{antonimy}}";

    private final NumerationInterpreter numerationInterpreter;

    @Autowired
    public AntonymsParser(NumerationInterpreter numerationInterpreter) {
        this.numerationInterpreter = numerationInterpreter;
    }

    public void parseAntonym(WikiUnit wikiUnit, List<String> dataBlock) {
        if (dataBlock == null) {
            log.error("Invalid input. Unit name: {}", wikiUnit.getName());
            return;
        }

        for (String antonymLine : dataBlock) {
            if (antonymLine.trim().equals(template)) {
                continue;
            }

            List<Numeration> numerations = numerationInterpreter.parseReferenceNumeration(wikiUnit, getNumeration(antonymLine));
            List<Link> links = LinkParser.parseLinks(antonymLine, false);

            for (Link link : links) {
                wikiUnit.addAntonym(numerations, new Antonym(link));
            }
        }
    }
}
