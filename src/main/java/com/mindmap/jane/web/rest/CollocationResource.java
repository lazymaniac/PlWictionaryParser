package com.mindmap.jane.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mindmap.jane.service.CollocationService;
import com.mindmap.jane.web.rest.util.HeaderUtil;
import com.mindmap.jane.web.rest.util.PaginationUtil;
import com.mindmap.jane.service.dto.CollocationDTO;
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
 * REST controller for managing Collocation.
 */
@RestController
@RequestMapping("/api")
public class CollocationResource {

    private final Logger log = LoggerFactory.getLogger(CollocationResource.class);

    private static final String ENTITY_NAME = "collocation";

    private final CollocationService collocationService;

    public CollocationResource(CollocationService collocationService) {
        this.collocationService = collocationService;
    }

    /**
     * POST  /collocations : Create a new collocation.
     *
     * @param collocationDTO the collocationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new collocationDTO, or with status 400 (Bad Request) if the collocation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/collocations")
    @Timed
    public ResponseEntity<CollocationDTO> createCollocation(@RequestBody CollocationDTO collocationDTO) throws URISyntaxException {
        log.debug("REST request to save Collocation : {}", collocationDTO);
        if (collocationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new collocation cannot already have an ID")).body(null);
        }
        CollocationDTO result = collocationService.save(collocationDTO);
        return ResponseEntity.created(new URI("/api/collocations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /collocations : Updates an existing collocation.
     *
     * @param collocationDTO the collocationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated collocationDTO,
     * or with status 400 (Bad Request) if the collocationDTO is not valid,
     * or with status 500 (Internal Server Error) if the collocationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/collocations")
    @Timed
    public ResponseEntity<CollocationDTO> updateCollocation(@RequestBody CollocationDTO collocationDTO) throws URISyntaxException {
        log.debug("REST request to update Collocation : {}", collocationDTO);
        if (collocationDTO.getId() == null) {
            return createCollocation(collocationDTO);
        }
        CollocationDTO result = collocationService.save(collocationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, collocationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /collocations : get all the collocations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of collocations in body
     */
    @GetMapping("/collocations")
    @Timed
    public ResponseEntity<List<CollocationDTO>> getAllCollocations(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Collocations");
        Page<CollocationDTO> page = collocationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/collocations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /collocations/:id : get the "id" collocation.
     *
     * @param id the id of the collocationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the collocationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/collocations/{id}")
    @Timed
    public ResponseEntity<CollocationDTO> getCollocation(@PathVariable String id) {
        log.debug("REST request to get Collocation : {}", id);
        CollocationDTO collocationDTO = collocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(collocationDTO));
    }

    /**
     * DELETE  /collocations/:id : delete the "id" collocation.
     *
     * @param id the id of the collocationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/collocations/{id}")
    @Timed
    public ResponseEntity<Void> deleteCollocation(@PathVariable String id) {
        log.debug("REST request to delete Collocation : {}", id);
        collocationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
