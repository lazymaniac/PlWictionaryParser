package com.mindmap.jane.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mindmap.jane.service.CasesVarService;
import com.mindmap.jane.web.rest.util.HeaderUtil;
import com.mindmap.jane.web.rest.util.PaginationUtil;
import com.mindmap.jane.service.dto.CasesVarDTO;
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
 * REST controller for managing CasesVar.
 */
@RestController
@RequestMapping("/api")
public class CasesVarResource {

    private final Logger log = LoggerFactory.getLogger(CasesVarResource.class);

    private static final String ENTITY_NAME = "casesVar";

    private final CasesVarService casesVarService;

    public CasesVarResource(CasesVarService casesVarService) {
        this.casesVarService = casesVarService;
    }

    /**
     * POST  /cases-vars : Create a new casesVar.
     *
     * @param casesVarDTO the casesVarDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new casesVarDTO, or with status 400 (Bad Request) if the casesVar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cases-vars")
    @Timed
    public ResponseEntity<CasesVarDTO> createCasesVar(@RequestBody CasesVarDTO casesVarDTO) throws URISyntaxException {
        log.debug("REST request to save CasesVar : {}", casesVarDTO);
        if (casesVarDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new casesVar cannot already have an ID")).body(null);
        }
        CasesVarDTO result = casesVarService.save(casesVarDTO);
        return ResponseEntity.created(new URI("/api/cases-vars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cases-vars : Updates an existing casesVar.
     *
     * @param casesVarDTO the casesVarDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated casesVarDTO,
     * or with status 400 (Bad Request) if the casesVarDTO is not valid,
     * or with status 500 (Internal Server Error) if the casesVarDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cases-vars")
    @Timed
    public ResponseEntity<CasesVarDTO> updateCasesVar(@RequestBody CasesVarDTO casesVarDTO) throws URISyntaxException {
        log.debug("REST request to update CasesVar : {}", casesVarDTO);
        if (casesVarDTO.getId() == null) {
            return createCasesVar(casesVarDTO);
        }
        CasesVarDTO result = casesVarService.save(casesVarDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, casesVarDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cases-vars : get all the casesVars.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of casesVars in body
     */
    @GetMapping("/cases-vars")
    @Timed
    public ResponseEntity<List<CasesVarDTO>> getAllCasesVars(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of CasesVars");
        Page<CasesVarDTO> page = casesVarService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cases-vars");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cases-vars/:id : get the "id" casesVar.
     *
     * @param id the id of the casesVarDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the casesVarDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cases-vars/{id}")
    @Timed
    public ResponseEntity<CasesVarDTO> getCasesVar(@PathVariable String id) {
        log.debug("REST request to get CasesVar : {}", id);
        CasesVarDTO casesVarDTO = casesVarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(casesVarDTO));
    }

    /**
     * DELETE  /cases-vars/:id : delete the "id" casesVar.
     *
     * @param id the id of the casesVarDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cases-vars/{id}")
    @Timed
    public ResponseEntity<Void> deleteCasesVar(@PathVariable String id) {
        log.debug("REST request to delete CasesVar : {}", id);
        casesVarService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
