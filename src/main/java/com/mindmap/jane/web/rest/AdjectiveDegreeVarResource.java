package com.mindmap.jane.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mindmap.jane.service.AdjectiveDegreeVarService;
import com.mindmap.jane.web.rest.util.HeaderUtil;
import com.mindmap.jane.web.rest.util.PaginationUtil;
import com.mindmap.jane.service.dto.AdjectiveDegreeVarDTO;
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
 * REST controller for managing AdjectiveDegreeVar.
 */
@RestController
@RequestMapping("/api")
public class AdjectiveDegreeVarResource {

    private final Logger log = LoggerFactory.getLogger(AdjectiveDegreeVarResource.class);

    private static final String ENTITY_NAME = "adjectiveDegreeVar";

    private final AdjectiveDegreeVarService adjectiveDegreeVarService;

    public AdjectiveDegreeVarResource(AdjectiveDegreeVarService adjectiveDegreeVarService) {
        this.adjectiveDegreeVarService = adjectiveDegreeVarService;
    }

    /**
     * POST  /adjective-degree-vars : Create a new adjectiveDegreeVar.
     *
     * @param adjectiveDegreeVarDTO the adjectiveDegreeVarDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new adjectiveDegreeVarDTO, or with status 400 (Bad Request) if the adjectiveDegreeVar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/adjective-degree-vars")
    @Timed
    public ResponseEntity<AdjectiveDegreeVarDTO> createAdjectiveDegreeVar(@RequestBody AdjectiveDegreeVarDTO adjectiveDegreeVarDTO) throws URISyntaxException {
        log.debug("REST request to save AdjectiveDegreeVar : {}", adjectiveDegreeVarDTO);
        if (adjectiveDegreeVarDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new adjectiveDegreeVar cannot already have an ID")).body(null);
        }
        AdjectiveDegreeVarDTO result = adjectiveDegreeVarService.save(adjectiveDegreeVarDTO);
        return ResponseEntity.created(new URI("/api/adjective-degree-vars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /adjective-degree-vars : Updates an existing adjectiveDegreeVar.
     *
     * @param adjectiveDegreeVarDTO the adjectiveDegreeVarDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated adjectiveDegreeVarDTO,
     * or with status 400 (Bad Request) if the adjectiveDegreeVarDTO is not valid,
     * or with status 500 (Internal Server Error) if the adjectiveDegreeVarDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/adjective-degree-vars")
    @Timed
    public ResponseEntity<AdjectiveDegreeVarDTO> updateAdjectiveDegreeVar(@RequestBody AdjectiveDegreeVarDTO adjectiveDegreeVarDTO) throws URISyntaxException {
        log.debug("REST request to update AdjectiveDegreeVar : {}", adjectiveDegreeVarDTO);
        if (adjectiveDegreeVarDTO.getId() == null) {
            return createAdjectiveDegreeVar(adjectiveDegreeVarDTO);
        }
        AdjectiveDegreeVarDTO result = adjectiveDegreeVarService.save(adjectiveDegreeVarDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, adjectiveDegreeVarDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /adjective-degree-vars : get all the adjectiveDegreeVars.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of adjectiveDegreeVars in body
     */
    @GetMapping("/adjective-degree-vars")
    @Timed
    public ResponseEntity<List<AdjectiveDegreeVarDTO>> getAllAdjectiveDegreeVars(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of AdjectiveDegreeVars");
        Page<AdjectiveDegreeVarDTO> page = adjectiveDegreeVarService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/adjective-degree-vars");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /adjective-degree-vars/:id : get the "id" adjectiveDegreeVar.
     *
     * @param id the id of the adjectiveDegreeVarDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the adjectiveDegreeVarDTO, or with status 404 (Not Found)
     */
    @GetMapping("/adjective-degree-vars/{id}")
    @Timed
    public ResponseEntity<AdjectiveDegreeVarDTO> getAdjectiveDegreeVar(@PathVariable String id) {
        log.debug("REST request to get AdjectiveDegreeVar : {}", id);
        AdjectiveDegreeVarDTO adjectiveDegreeVarDTO = adjectiveDegreeVarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(adjectiveDegreeVarDTO));
    }

    /**
     * DELETE  /adjective-degree-vars/:id : delete the "id" adjectiveDegreeVar.
     *
     * @param id the id of the adjectiveDegreeVarDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/adjective-degree-vars/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdjectiveDegreeVar(@PathVariable String id) {
        log.debug("REST request to delete AdjectiveDegreeVar : {}", id);
        adjectiveDegreeVarService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
