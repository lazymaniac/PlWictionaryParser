package com.mindmap.jane.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mindmap.jane.service.SynonymService;
import com.mindmap.jane.web.rest.util.HeaderUtil;
import com.mindmap.jane.web.rest.util.PaginationUtil;
import com.mindmap.jane.service.dto.SynonymDTO;
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
 * REST controller for managing Synonym.
 */
@RestController
@RequestMapping("/api")
public class SynonymResource {

    private final Logger log = LoggerFactory.getLogger(SynonymResource.class);

    private static final String ENTITY_NAME = "synonym";

    private final SynonymService synonymService;

    public SynonymResource(SynonymService synonymService) {
        this.synonymService = synonymService;
    }

    /**
     * POST  /synonyms : Create a new synonym.
     *
     * @param synonymDTO the synonymDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new synonymDTO, or with status 400 (Bad Request) if the synonym has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/synonyms")
    @Timed
    public ResponseEntity<SynonymDTO> createSynonym(@RequestBody SynonymDTO synonymDTO) throws URISyntaxException {
        log.debug("REST request to save Synonym : {}", synonymDTO);
        if (synonymDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new synonym cannot already have an ID")).body(null);
        }
        SynonymDTO result = synonymService.save(synonymDTO);
        return ResponseEntity.created(new URI("/api/synonyms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /synonyms : Updates an existing synonym.
     *
     * @param synonymDTO the synonymDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated synonymDTO,
     * or with status 400 (Bad Request) if the synonymDTO is not valid,
     * or with status 500 (Internal Server Error) if the synonymDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/synonyms")
    @Timed
    public ResponseEntity<SynonymDTO> updateSynonym(@RequestBody SynonymDTO synonymDTO) throws URISyntaxException {
        log.debug("REST request to update Synonym : {}", synonymDTO);
        if (synonymDTO.getId() == null) {
            return createSynonym(synonymDTO);
        }
        SynonymDTO result = synonymService.save(synonymDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, synonymDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /synonyms : get all the synonyms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of synonyms in body
     */
    @GetMapping("/synonyms")
    @Timed
    public ResponseEntity<List<SynonymDTO>> getAllSynonyms(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Synonyms");
        Page<SynonymDTO> page = synonymService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/synonyms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /synonyms/:id : get the "id" synonym.
     *
     * @param id the id of the synonymDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the synonymDTO, or with status 404 (Not Found)
     */
    @GetMapping("/synonyms/{id}")
    @Timed
    public ResponseEntity<SynonymDTO> getSynonym(@PathVariable String id) {
        log.debug("REST request to get Synonym : {}", id);
        SynonymDTO synonymDTO = synonymService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(synonymDTO));
    }

    /**
     * DELETE  /synonyms/:id : delete the "id" synonym.
     *
     * @param id the id of the synonymDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/synonyms/{id}")
    @Timed
    public ResponseEntity<Void> deleteSynonym(@PathVariable String id) {
        log.debug("REST request to delete Synonym : {}", id);
        synonymService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
