package com.mindmap.jane.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mindmap.jane.service.AdverbVarService;
import com.mindmap.jane.web.rest.util.HeaderUtil;
import com.mindmap.jane.web.rest.util.PaginationUtil;
import com.mindmap.jane.service.dto.AdverbVarDTO;
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
 * REST controller for managing AdverbVar.
 */
@RestController
@RequestMapping("/api")
public class AdverbVarResource {

    private final Logger log = LoggerFactory.getLogger(AdverbVarResource.class);

    private static final String ENTITY_NAME = "adverbVar";

    private final AdverbVarService adverbVarService;

    public AdverbVarResource(AdverbVarService adverbVarService) {
        this.adverbVarService = adverbVarService;
    }

    /**
     * POST  /adverb-vars : Create a new adverbVar.
     *
     * @param adverbVarDTO the adverbVarDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new adverbVarDTO, or with status 400 (Bad Request) if the adverbVar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/adverb-vars")
    @Timed
    public ResponseEntity<AdverbVarDTO> createAdverbVar(@RequestBody AdverbVarDTO adverbVarDTO) throws URISyntaxException {
        log.debug("REST request to save AdverbVar : {}", adverbVarDTO);
        if (adverbVarDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new adverbVar cannot already have an ID")).body(null);
        }
        AdverbVarDTO result = adverbVarService.save(adverbVarDTO);
        return ResponseEntity.created(new URI("/api/adverb-vars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /adverb-vars : Updates an existing adverbVar.
     *
     * @param adverbVarDTO the adverbVarDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated adverbVarDTO,
     * or with status 400 (Bad Request) if the adverbVarDTO is not valid,
     * or with status 500 (Internal Server Error) if the adverbVarDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/adverb-vars")
    @Timed
    public ResponseEntity<AdverbVarDTO> updateAdverbVar(@RequestBody AdverbVarDTO adverbVarDTO) throws URISyntaxException {
        log.debug("REST request to update AdverbVar : {}", adverbVarDTO);
        if (adverbVarDTO.getId() == null) {
            return createAdverbVar(adverbVarDTO);
        }
        AdverbVarDTO result = adverbVarService.save(adverbVarDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, adverbVarDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /adverb-vars : get all the adverbVars.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of adverbVars in body
     */
    @GetMapping("/adverb-vars")
    @Timed
    public ResponseEntity<List<AdverbVarDTO>> getAllAdverbVars(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of AdverbVars");
        Page<AdverbVarDTO> page = adverbVarService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/adverb-vars");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /adverb-vars/:id : get the "id" adverbVar.
     *
     * @param id the id of the adverbVarDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the adverbVarDTO, or with status 404 (Not Found)
     */
    @GetMapping("/adverb-vars/{id}")
    @Timed
    public ResponseEntity<AdverbVarDTO> getAdverbVar(@PathVariable String id) {
        log.debug("REST request to get AdverbVar : {}", id);
        AdverbVarDTO adverbVarDTO = adverbVarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(adverbVarDTO));
    }

    /**
     * DELETE  /adverb-vars/:id : delete the "id" adverbVar.
     *
     * @param id the id of the adverbVarDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/adverb-vars/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdverbVar(@PathVariable String id) {
        log.debug("REST request to delete AdverbVar : {}", id);
        adverbVarService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
