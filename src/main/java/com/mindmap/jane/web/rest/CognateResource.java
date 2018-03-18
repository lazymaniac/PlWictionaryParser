package com.mindmap.jane.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mindmap.jane.service.CognateService;
import com.mindmap.jane.web.rest.util.HeaderUtil;
import com.mindmap.jane.web.rest.util.PaginationUtil;
import com.mindmap.jane.service.dto.CognateDTO;
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
 * REST controller for managing Cognate.
 */
@RestController
@RequestMapping("/api")
public class CognateResource {

    private final Logger log = LoggerFactory.getLogger(CognateResource.class);

    private static final String ENTITY_NAME = "cognate";

    private final CognateService cognateService;

    public CognateResource(CognateService cognateService) {
        this.cognateService = cognateService;
    }

    /**
     * POST  /cognates : Create a new cognate.
     *
     * @param cognateDTO the cognateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cognateDTO, or with status 400 (Bad Request) if the cognate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cognates")
    @Timed
    public ResponseEntity<CognateDTO> createCognate(@RequestBody CognateDTO cognateDTO) throws URISyntaxException {
        log.debug("REST request to save Cognate : {}", cognateDTO);
        if (cognateDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cognate cannot already have an ID")).body(null);
        }
        CognateDTO result = cognateService.save(cognateDTO);
        return ResponseEntity.created(new URI("/api/cognates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cognates : Updates an existing cognate.
     *
     * @param cognateDTO the cognateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cognateDTO,
     * or with status 400 (Bad Request) if the cognateDTO is not valid,
     * or with status 500 (Internal Server Error) if the cognateDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cognates")
    @Timed
    public ResponseEntity<CognateDTO> updateCognate(@RequestBody CognateDTO cognateDTO) throws URISyntaxException {
        log.debug("REST request to update Cognate : {}", cognateDTO);
        if (cognateDTO.getId() == null) {
            return createCognate(cognateDTO);
        }
        CognateDTO result = cognateService.save(cognateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cognateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cognates : get all the cognates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cognates in body
     */
    @GetMapping("/cognates")
    @Timed
    public ResponseEntity<List<CognateDTO>> getAllCognates(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Cognates");
        Page<CognateDTO> page = cognateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cognates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cognates/:id : get the "id" cognate.
     *
     * @param id the id of the cognateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cognateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cognates/{id}")
    @Timed
    public ResponseEntity<CognateDTO> getCognate(@PathVariable String id) {
        log.debug("REST request to get Cognate : {}", id);
        CognateDTO cognateDTO = cognateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cognateDTO));
    }

    /**
     * DELETE  /cognates/:id : delete the "id" cognate.
     *
     * @param id the id of the cognateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cognates/{id}")
    @Timed
    public ResponseEntity<Void> deleteCognate(@PathVariable String id) {
        log.debug("REST request to delete Cognate : {}", id);
        cognateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
