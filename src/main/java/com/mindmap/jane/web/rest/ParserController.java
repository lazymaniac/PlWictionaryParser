package com.mindmap.jane.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mindmap.jane.service.ParserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ParserController {

    private final Logger log = LoggerFactory.getLogger(ParserController.class);

    private final ParserService parserService;

    public ParserController(ParserService parserService) {
        this.parserService = parserService;
    }

    @GetMapping("update-database")
    @Timed

    public void updateDatabase(@RequestParam Boolean exportToFiles) {
        log.debug("Database update process started with param: exportToFiles: {}", exportToFiles);

        parserService.processWiktionary(exportToFiles, true);
    }


}
