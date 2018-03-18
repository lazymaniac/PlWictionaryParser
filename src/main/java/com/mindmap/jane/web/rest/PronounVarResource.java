package com.mindmap.jane.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mindmap.jane.service.PronounVarService;
import com.mindmap.jane.web.rest.util.HeaderUtil;
import com.mindmap.jane.web.rest.util.PaginationUtil;
import com.mindmap.jane.service.dto.PronounVarDTO;
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
 * REST controller for managing PronounVar.
 */
@RestController
@RequestMapping("/api")
public class PronounVarResource {

    private final Logger log = LoggerFactory.getLogger(PronounVarResource.class);

    private static final String ENTITY_NAME = "pronounVar";

    private final PronounVarService pronounVarService;

    public PronounVarResource(PronounVarService pronounVarService) {
        this.pronounVarService = pronounVarService;
    }

    /**
     * POST  /pronoun-vars : Create a new pronounVar.
     *
     * @param pronounVarDTO the pronounVarDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pronounVarDTO, or with status 400 (Bad Request) if the pronounVar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pronoun-vars")
    @Timed
    public ResponseEntity<PronounVarDTO> createPronounVar(@RequestBody PronounVarDTO pronounVarDTO) throws URISyntaxException {
        log.debug("REST request to save PronounVar : {}", pronounVarDTO);
        if (pronounVarDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pronounVar cannot already have an ID")).body(null);
        }
        PronounVarDTO result = pronounVarService.save(pronounVarDTO);
        return ResponseEntity.created(new URI("/api/pronoun-vars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pronoun-vars : Updates an existing pronounVar.
     *
     * @param pronounVarDTO the pronounVarDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pronounVarDTO,
     * or with status 400 (Bad Request) if the pronounVarDTO is not valid,
     * or with status 500 (Internal Server Error) if the pronounVarDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pronoun-vars")
    @Timed
    public ResponseEntity<PronounVarDTO> updatePronounVar(@RequestBody PronounVarDTO pronounVarDTO) throws URISyntaxException {
        log.debug("REST request to update PronounVar : {}", pronounVarDTO);
        if (pronounVarDTO.getId() == null) {
            return createPronounVar(pronounVarDTO);
        }
        PronounVarDTO result = pronounVarService.save(pronounVarDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pronounVarDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pronoun-vars : get all the pronounVars.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pronounVars in body
     */
    @GetMapping("/pronoun-vars")
    @Timed
    public ResponseEntity<List<PronounVarDTO>> getAllPronounVars(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of PronounVars");
        Page<PronounVarDTO> page = pronounVarService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pronoun-vars");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pronoun-vars/:id : get the "id" pronounVar.
     *
     * @param id the id of the pronounVarDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pronounVarDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pronoun-vars/{id}")
    @Timed
    public ResponseEntity<PronounVarDTO> getPronounVar(@PathVariable String id) {
        log.debug("REST request to get PronounVar : {}", id);
        PronounVarDTO pronounVarDTO = pronounVarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pronounVarDTO));
    }

    /**
     * DELETE  /pronoun-vars/:id : delete the "id" pronounVar.
     *
     * @param id the id of the pronounVarDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pronoun-vars/{id}")
    @Timed
    public ResponseEntity<Void> deletePronounVar(@PathVariable String id) {
        log.debug("REST request to delete PronounVar : {}", id);
        pronounVarService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
