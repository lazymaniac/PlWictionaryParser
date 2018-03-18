package com.mindmap.jane.wiktionary.tagparser;

import com.mindmap.jane.domain.Example;
import com.mindmap.jane.domain.Sentence;
import com.mindmap.jane.domain.WikiUnit;
import com.mindmap.jane.utils.SentenceParser;
import com.mindmap.jane.wiktionary.numeration.Numeration;
import com.mindmap.jane.wiktionary.numeration.NumerationInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mindmap.jane.utils.NumerationUtils.getNumeration;


/**
 * 1. == jabłko
 * {{przykłady}}
 * : (1.1) ''[[nieprawda|Nieprawdą]] [[być|jest]], [[że]] [[jeden|jedno]] [[jabłko]] [[wieczorem]] [[zastąpić|zastąpi]] [[umyć|umycie]] [[ząb|zębów]].''
 * : (1.2) ''[[król|Króla]] [[pochować|pochowano]] [[w]] [[korona|koronie]] [[i]] [[z]] [[jabłko|jabłkiem]] [[w]] [[dłoń|dłoni]].''
 */
@Service
public class ExamplesParser {

    private static final Logger log = LoggerFactory.getLogger(ExamplesParser.class);

    private final static String template = "{{przykłady}}";

    private final NumerationInterpreter numerationInterpreter;

    @Autowired
    public ExamplesParser(NumerationInterpreter numerationInterpreter) {
        this.numerationInterpreter = numerationInterpreter;
    }

    public void parserExamples(WikiUnit wikiUnit, List<String> dataBlock) {
        if (dataBlock == null) {
            log.error("Invalid input. Unit name: {}", wikiUnit.getName());
            return;
        }

        for (String currentLine : dataBlock) {
            if (currentLine.trim().equals(template)) {
                continue;
            }

            List<Numeration> numerations = numerationInterpreter.parseReferenceNumeration(wikiUnit, getNumeration(currentLine));
            Sentence sentence = SentenceParser.parseSentence(currentLine);

            wikiUnit.addExample(numerations, new Example(sentence));
        }
    }

}
