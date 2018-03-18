package com.mindmap.jane.wiktionary.dictionary;

import com.mindmap.jane.config.ApplicationProperties;
import com.mindmap.jane.wiktionary.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;


/**
 * Builder working on Dictionary object. Configured in main method.
 *
 * @author Sebastian Fabisz
 */
@Service
public class DictionaryBuilder {

    private final static Logger log = LoggerFactory.getLogger(DictionaryBuilder.class);

    private final WikiCleaner wikiCleaner;

    private final WikiRawDataParser wikiRawDataParser;

    private final ApplicationProperties applicationProperties;

    public DictionaryBuilder(WikiCleaner wikiCleaner, WikiRawDataParser wikiRawDataParser, ApplicationProperties applicationProperties) {
        this.wikiCleaner = wikiCleaner;
        this.wikiRawDataParser = wikiRawDataParser;
        this.applicationProperties = applicationProperties;
    }

    /**
     * Builds dictionary. Checks all flags and invoke necessary
     * methods corresponding to each flag.
     */
    public Dictionary buildDictionary() {
        Dictionary dictionary = new Dictionary();
        String wiktionaryFilePath = getWiktionaryFilePath();

        WikiParser wikiParser = new WikiParser(wiktionaryFilePath, dictionary);
        wikiParser.parseDocument();

        wikiCleaner.clearUnits(dictionary);

        wikiRawDataParser.parseRawUnits(dictionary);

        return dictionary;
    }

    private String getWiktionaryFilePath() {
        String wiktionaryfilePath = new File(applicationProperties.getWiktionaryFileName()).getAbsolutePath();
        log.info("Wiktionary file path: " + wiktionaryfilePath);
        return wiktionaryfilePath;
    }

}
