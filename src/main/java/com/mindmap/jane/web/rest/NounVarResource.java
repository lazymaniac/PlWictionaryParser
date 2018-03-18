package com.mindmap.jane.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mindmap.jane.service.NounVarService;
import com.mindmap.jane.web.rest.util.HeaderUtil;
import com.mindmap.jane.web.rest.util.PaginationUtil;
import com.mindmap.jane.service.dto.NounVarDTO;
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
 * REST controller for managing NounVar.
 */
@RestController
@RequestMapping("/api")
public class NounVarResource {

    private final Logger log = LoggerFactory.getLogger(NounVarResource.class);

    private static final String ENTITY_NAME = "nounVar";

    private final NounVarService nounVarService;

    public NounVarResource(NounVarService nounVarService) {
        this.nounVarService = nounVarService;
    }

    /**
     * POST  /noun-vars : Create a new nounVar.
     *
     * @param nounVarDTO the nounVarDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nounVarDTO, or with status 400 (Bad Request) if the nounVar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/noun-vars")
    @Timed
    public ResponseEntity<NounVarDTO> createNounVar(@RequestBody NounVarDTO nounVarDTO) throws URISyntaxException {
        log.debug("REST request to save NounVar : {}", nounVarDTO);
        if (nounVarDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new nounVar cannot already have an ID")).body(null);
        }
        NounVarDTO result = nounVarService.save(nounVarDTO);
        return ResponseEntity.created(new URI("/api/noun-vars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /noun-vars : Updates an existing nounVar.
     *
     * @param nounVarDTO the nounVarDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nounVarDTO,
     * or with status 400 (Bad Request) if the nounVarDTO is not valid,
     * or with status 500 (Internal Server Error) if the nounVarDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/noun-vars")
    @Timed
    public ResponseEntity<NounVarDTO> updateNounVar(@RequestBody NounVarDTO nounVarDTO) throws URISyntaxException {
        log.debug("REST request to update NounVar : {}", nounVarDTO);
        if (nounVarDTO.getId() == null) {
            return createNounVar(nounVarDTO);
        }
        NounVarDTO result = nounVarService.save(nounVarDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nounVarDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /noun-vars : get all the nounVars.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of nounVars in body
     */
    @GetMapping("/noun-vars")
    @Timed
    public ResponseEntity<List<NounVarDTO>> getAllNounVars(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of NounVars");
        Page<NounVarDTO> page = nounVarService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/noun-vars");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /noun-vars/:id : get the "id" nounVar.
     *
     * @param id the id of the nounVarDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nounVarDTO, or with status 404 (Not Found)
     */
    @GetMapping("/noun-vars/{id}")
    @Timed
    public ResponseEntity<NounVarDTO> getNounVar(@PathVariable String id) {
        log.debug("REST request to get NounVar : {}", id);
        NounVarDTO nounVarDTO = nounVarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(nounVarDTO));
    }

    /**
     * DELETE  /noun-vars/:id : delete the "id" nounVar.
     *
     * @param id the id of the nounVarDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/noun-vars/{id}")
    @Timed
    public ResponseEntity<Void> deleteNounVar(@PathVariable String id) {
        log.debug("REST request to delete NounVar : {}", id);
        nounVarService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
