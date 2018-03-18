package com.mindmap.jane.wiktionary.tagparser;

import com.mindmap.jane.domain.NumeralVar;
import com.mindmap.jane.domain.WikiUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//TODO rewrite to regex verify with current wiktionary template
public class NumeralVarParser {

    private final static Logger log = LoggerFactory.getLogger(NumeralVarParser.class);

    public NumeralVar parse(WikiUnit wikiUnit, List<String> dataBlock) {
        if (dataBlock == null) {
            log.error("Invalid input. Unit name: {}", wikiUnit.getName());
        }

        // TODO implement
        return null;
    }
}
