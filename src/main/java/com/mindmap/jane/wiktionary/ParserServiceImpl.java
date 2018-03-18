package com.mindmap.jane.wiktionary;

import com.mindmap.jane.repository.WikiUnitRepository;
import com.mindmap.jane.service.ParserService;
import com.mindmap.jane.wiktionary.dictionary.Dictionary;
import com.mindmap.jane.wiktionary.dictionary.DictionaryBuilder;
import com.mindmap.jane.web.rest.vm.UpdateDatabaseResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ParserServiceImpl implements ParserService {

    private Logger log = LoggerFactory.getLogger(ParserServiceImpl.class);

    private final DictionaryBuilder dictionaryBuilder;

    private final WikiUnitRepository wikiUnitRepository;

    private final WiktionaryXMLExporter wiktionaryXMLExporter;

    public ParserServiceImpl(DictionaryBuilder dictionaryBuilder, WikiUnitRepository wikiUnitRepository, WiktionaryXMLExporter wiktionaryXMLExporter) {
        this.dictionaryBuilder = dictionaryBuilder;
        this.wikiUnitRepository = wikiUnitRepository;
        this.wiktionaryXMLExporter = wiktionaryXMLExporter;
    }

    @Override
    @Async
    public void processWiktionary(Boolean exportToFiles, Boolean saveToDatabase) {
        log.debug("Setup DictionaryBuilder and start processing");

        Dictionary dictionary = dictionaryBuilder.buildDictionary();

        if (saveToDatabase) {
            log.info("Delete all old units");
            wikiUnitRepository.deleteAll();
            log.info("Persisting WikiUnits in database");
            wikiUnitRepository.save(dictionary.getWikiUnits());
        }

        if (exportToFiles) {
           wiktionaryXMLExporter.exportDatabase(dictionary);
        }
    }

}
