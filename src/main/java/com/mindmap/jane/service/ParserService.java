package com.mindmap.jane.service;

import com.mindmap.jane.domain.SourceWikiUnit;
import com.mindmap.jane.domain.WikiUnit;
import com.mindmap.jane.repository.SourceWikiUnitRepository;
import com.mindmap.jane.repository.WikiUnitRepository;
import com.mindmap.jane.wiktionary.WiktionaryXMLExporter;
import com.mindmap.jane.wiktionary.dictionary.Dictionary;
import com.mindmap.jane.wiktionary.dictionary.DictionaryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ParserService {

    private Logger log = LoggerFactory.getLogger(ParserService.class);

    private final DictionaryBuilder dictionaryBuilder;

    private final WikiUnitRepository wikiUnitRepository;

    private final SourceWikiUnitRepository sourceWikiUnitRepository;

    private final WiktionaryXMLExporter wiktionaryXMLExporter;

    public ParserService(DictionaryBuilder dictionaryBuilder, WikiUnitRepository wikiUnitRepository, SourceWikiUnitRepository sourceWikiUnitRepository, WiktionaryXMLExporter wiktionaryXMLExporter) {
        this.dictionaryBuilder = dictionaryBuilder;
        this.wikiUnitRepository = wikiUnitRepository;
        this.sourceWikiUnitRepository = sourceWikiUnitRepository;
        this.wiktionaryXMLExporter = wiktionaryXMLExporter;
    }

    @Async
    public void processWiktionary(Boolean exportToFiles, Boolean saveToDatabase) {
        log.debug("Setup DictionaryBuilder and start processing");

        Dictionary dictionary = dictionaryBuilder.buildDictionary();

        if (saveToDatabase) {
            removeExistingUnits();
            saveWikiUnits(dictionary.getWikiUnits());
            saveSourceWikiUnits(dictionary.getSourceWikiUnits());
        }

        if (exportToFiles) {
            wiktionaryXMLExporter.exportDatabase(dictionary);
        }
    }

    private void removeExistingUnits() {
        log.info("Delete persisted units");
        wikiUnitRepository.deleteAll();
        sourceWikiUnitRepository.deleteAll();
    }

    private void saveWikiUnits(Set<WikiUnit> wikiUnits) {
        log.info("Persisting WikiUnits in database");
        wikiUnitRepository.save(wikiUnits);
        log.info("Saved {} WikiUnits", wikiUnits.size());
    }

    private void saveSourceWikiUnits(Set<SourceWikiUnit> sourceWikiUnits) {
        log.info("Persisting SourceWikiUnits in database");
        sourceWikiUnitRepository.save(sourceWikiUnits);
        log.info("Saved {} SourceWikiUnits", sourceWikiUnits.size());
    }

}
