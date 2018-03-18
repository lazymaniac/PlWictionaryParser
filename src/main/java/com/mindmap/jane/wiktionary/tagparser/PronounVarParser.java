package com.mindmap.jane.wiktionary.tagparser;

import com.mindmap.jane.domain.PronounVar;
import com.mindmap.jane.domain.WikiUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//TODO rewrite to regex verify with current wiktionary template
public class PronounVarParser {

    private static final Logger log = LoggerFactory.getLogger(PronounVarParser.class);

    public PronounVar parse(WikiUnit wikiUnit, List<String> dataBlock) {
        if (dataBlock == null) {
            log.error("Invalid input. Unit name: {}", wikiUnit.getName());
            return null;
        }

        //TODO implement

        return null;
    }
}
