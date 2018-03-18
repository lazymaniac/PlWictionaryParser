package com.mindmap.jane.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mindmap.jane.service.VerbVarService;
import com.mindmap.jane.web.rest.util.HeaderUtil;
import com.mindmap.jane.web.rest.util.PaginationUtil;
import com.mindmap.jane.service.dto.VerbVarDTO;
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
 * REST controller for managing VerbVar.
 */
@RestController
@RequestMapping("/api")
public class VerbVarResource {

    private final Logger log = LoggerFactory.getLogger(VerbVarResource.class);

    private static final String ENTITY_NAME = "verbVar";

    private final VerbVarService verbVarService;

    public VerbVarResource(VerbVarService verbVarService) {
        this.verbVarService = verbVarService;
    }

    /**
     * POST  /verb-vars : Create a new verbVar.
     *
     * @param verbVarDTO the verbVarDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new verbVarDTO, or with status 400 (Bad Request) if the verbVar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/verb-vars")
    @Timed
    public ResponseEntity<VerbVarDTO> createVerbVar(@RequestBody VerbVarDTO verbVarDTO) throws URISyntaxException {
        log.debug("REST request to save VerbVar : {}", verbVarDTO);
        if (verbVarDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new verbVar cannot already have an ID")).body(null);
        }
        VerbVarDTO result = verbVarService.save(verbVarDTO);
        return ResponseEntity.created(new URI("/api/verb-vars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /verb-vars : Updates an existing verbVar.
     *
     * @param verbVarDTO the verbVarDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated verbVarDTO,
     * or with status 400 (Bad Request) if the verbVarDTO is not valid,
     * or with status 500 (Internal Server Error) if the verbVarDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/verb-vars")
    @Timed
    public ResponseEntity<VerbVarDTO> updateVerbVar(@RequestBody VerbVarDTO verbVarDTO) throws URISyntaxException {
        log.debug("REST request to update VerbVar : {}", verbVarDTO);
        if (verbVarDTO.getId() == null) {
            return createVerbVar(verbVarDTO);
        }
        VerbVarDTO result = verbVarService.save(verbVarDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, verbVarDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /verb-vars : get all the verbVars.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of verbVars in body
     */
    @GetMapping("/verb-vars")
    @Timed
    public ResponseEntity<List<VerbVarDTO>> getAllVerbVars(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of VerbVars");
        Page<VerbVarDTO> page = verbVarService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/verb-vars");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /verb-vars/:id : get the "id" verbVar.
     *
     * @param id the id of the verbVarDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the verbVarDTO, or with status 404 (Not Found)
     */
    @GetMapping("/verb-vars/{id}")
    @Timed
    public ResponseEntity<VerbVarDTO> getVerbVar(@PathVariable String id) {
        log.debug("REST request to get VerbVar : {}", id);
        VerbVarDTO verbVarDTO = verbVarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(verbVarDTO));
    }

    /**
     * DELETE  /verb-vars/:id : delete the "id" verbVar.
     *
     * @param id the id of the verbVarDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/verb-vars/{id}")
    @Timed
    public ResponseEntity<Void> deleteVerbVar(@PathVariable String id) {
        log.debug("REST request to delete VerbVar : {}", id);
        verbVarService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
