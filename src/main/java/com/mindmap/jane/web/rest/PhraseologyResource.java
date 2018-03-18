package com.mindmap.jane.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mindmap.jane.service.PhraseologyService;
import com.mindmap.jane.web.rest.util.HeaderUtil;
import com.mindmap.jane.web.rest.util.PaginationUtil;
import com.mindmap.jane.service.dto.PhraseologyDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Phraseology.
 */
@RestController
@RequestMapping("/api")
public class PhraseologyResource {

    private final Logger log = LoggerFactory.getLogger(PhraseologyResource.class);

    private static final String ENTITY_NAME = "phraseology";

    private final PhraseologyService phraseologyService;

    public PhraseologyResource(PhraseologyService phraseologyService) {
        this.phraseologyService = phraseologyService;
    }

    /**
     * POST  /phraseologies : Create a new phraseology.
     *
     * @param phraseologyDTO the phraseologyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new phraseologyDTO, or with status 400 (Bad Request) if the phraseology has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/phraseologies")
    @Timed
    public ResponseEntity<PhraseologyDTO> createPhraseology(@RequestBody PhraseologyDTO phraseologyDTO) throws URISyntaxException {
        log.debug("REST request to save Phraseology : {}", phraseologyDTO);
        if (phraseologyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new phraseology cannot already have an ID")).body(null);
        }
        PhraseologyDTO result = phraseologyService.save(phraseologyDTO);
        return ResponseEntity.created(new URI("/api/phraseologies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /phraseologies : Updates an existing phraseology.
     *
     * @param phraseologyDTO the phraseologyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated phraseologyDTO,
     * or with status 400 (Bad Request) if the phraseologyDTO is not valid,
     * or with status 500 (Internal Server Error) if the phraseologyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/phraseologies")
    @Timed
    public ResponseEntity<PhraseologyDTO> updatePhraseology(@RequestBody PhraseologyDTO phraseologyDTO) throws URISyntaxException {
        log.debug("REST request to update Phraseology : {}", phraseologyDTO);
        if (phraseologyDTO.getId() == null) {
            return createPhraseology(phraseologyDTO);
        }
        PhraseologyDTO result = phraseologyService.save(phraseologyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, phraseologyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /phraseologies : get all the phraseologies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of phraseologies in body
     */
    @GetMapping("/phraseologies")
    @Timed
    public ResponseEntity<List<PhraseologyDTO>> getAllPhraseologies(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Phraseologies");
        Page<PhraseologyDTO> page = phraseologyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/phraseologies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /phraseologies/:id : get the "id" phraseology.
     *
     * @param id the id of the phraseologyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the phraseologyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/phraseologies/{id}")
    @Timed
    public ResponseEntity<PhraseologyDTO> getPhraseology(@PathVariable String id) {
        log.debug("REST request to get Phraseology : {}", id);
        PhraseologyDTO phraseologyDTO = phraseologyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(phraseologyDTO));
    }

    /**
     * DELETE  /phraseologies/:id : delete the "id" phraseology.
     *
     * @param id the id of the phraseologyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/phraseologies/{id}")
    @Timed
    public ResponseEntity<Void> deletePhraseology(@PathVariable String id) {
        log.debug("REST request to delete Phraseology : {}", id);
        phraseologyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
