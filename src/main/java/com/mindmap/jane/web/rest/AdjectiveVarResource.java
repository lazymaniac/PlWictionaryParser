package com.mindmap.jane.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mindmap.jane.service.AdjectiveVarService;
import com.mindmap.jane.web.rest.util.HeaderUtil;
import com.mindmap.jane.web.rest.util.PaginationUtil;
import com.mindmap.jane.service.dto.AdjectiveVarDTO;
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
 * REST controller for managing AdjectiveVar.
 */
@RestController
@RequestMapping("/api")
public class AdjectiveVarResource {

    private final Logger log = LoggerFactory.getLogger(AdjectiveVarResource.class);

    private static final String ENTITY_NAME = "adjectiveVar";

    private final AdjectiveVarService adjectiveVarService;

    public AdjectiveVarResource(AdjectiveVarService adjectiveVarService) {
        this.adjectiveVarService = adjectiveVarService;
    }

    /**
     * POST  /adjective-vars : Create a new adjectiveVar.
     *
     * @param adjectiveVarDTO the adjectiveVarDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new adjectiveVarDTO, or with status 400 (Bad Request) if the adjectiveVar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/adjective-vars")
    @Timed
    public ResponseEntity<AdjectiveVarDTO> createAdjectiveVar(@RequestBody AdjectiveVarDTO adjectiveVarDTO) throws URISyntaxException {
        log.debug("REST request to save AdjectiveVar : {}", adjectiveVarDTO);
        if (adjectiveVarDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new adjectiveVar cannot already have an ID")).body(null);
        }
        AdjectiveVarDTO result = adjectiveVarService.save(adjectiveVarDTO);
        return ResponseEntity.created(new URI("/api/adjective-vars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /adjective-vars : Updates an existing adjectiveVar.
     *
     * @param adjectiveVarDTO the adjectiveVarDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated adjectiveVarDTO,
     * or with status 400 (Bad Request) if the adjectiveVarDTO is not valid,
     * or with status 500 (Internal Server Error) if the adjectiveVarDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/adjective-vars")
    @Timed
    public ResponseEntity<AdjectiveVarDTO> updateAdjectiveVar(@RequestBody AdjectiveVarDTO adjectiveVarDTO) throws URISyntaxException {
        log.debug("REST request to update AdjectiveVar : {}", adjectiveVarDTO);
        if (adjectiveVarDTO.getId() == null) {
            return createAdjectiveVar(adjectiveVarDTO);
        }
        AdjectiveVarDTO result = adjectiveVarService.save(adjectiveVarDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, adjectiveVarDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /adjective-vars : get all the adjectiveVars.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of adjectiveVars in body
     */
    @GetMapping("/adjective-vars")
    @Timed
    public ResponseEntity<List<AdjectiveVarDTO>> getAllAdjectiveVars(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of AdjectiveVars");
        Page<AdjectiveVarDTO> page = adjectiveVarService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/adjective-vars");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /adjective-vars/:id : get the "id" adjectiveVar.
     *
     * @param id the id of the adjectiveVarDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the adjectiveVarDTO, or with status 404 (Not Found)
     */
    @GetMapping("/adjective-vars/{id}")
    @Timed
    public ResponseEntity<AdjectiveVarDTO> getAdjectiveVar(@PathVariable String id) {
        log.debug("REST request to get AdjectiveVar : {}", id);
        AdjectiveVarDTO adjectiveVarDTO = adjectiveVarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(adjectiveVarDTO));
    }

    /**
     * DELETE  /adjective-vars/:id : delete the "id" adjectiveVar.
     *
     * @param id the id of the adjectiveVarDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/adjective-vars/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdjectiveVar(@PathVariable String id) {
        log.debug("REST request to delete AdjectiveVar : {}", id);
        adjectiveVarService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
