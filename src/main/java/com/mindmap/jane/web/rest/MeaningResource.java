package com.mindmap.jane.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mindmap.jane.service.MeaningService;
import com.mindmap.jane.service.dto.MeaningDTO;
import com.mindmap.jane.web.rest.util.HeaderUtil;
import com.mindmap.jane.web.rest.util.PaginationUtil;
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
 * REST controller for managing Meaning.
 */
@RestController
@RequestMapping("/api")
public class MeaningResource {

    private final Logger log = LoggerFactory.getLogger(MeaningResource.class);

    private static final String ENTITY_NAME = "meaning";

    private final MeaningService meaningService;

    public MeaningResource(MeaningService meaningService) {
        this.meaningService = meaningService;
    }

    /**
     * POST  /meanings : Create a new meaning.
     *
     * @param meaningDTO the meaningDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new meaningDTO, or with status 400 (Bad Request) if the meaning has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/meanings")
    @Timed
    public ResponseEntity<MeaningDTO> createMeaning(@RequestBody MeaningDTO meaningDTO) throws URISyntaxException {
        log.debug("REST request to save Meaning : {}", meaningDTO);
        if (meaningDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new meaning cannot already have an ID")).body(null);
        }
        MeaningDTO result = meaningService.save(meaningDTO);
        return ResponseEntity.created(new URI("/api/meanings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /meanings : Updates an existing meaning.
     *
     * @param meaningDTO the meaningDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated meaningDTO,
     * or with status 400 (Bad Request) if the meaningDTO is not valid,
     * or with status 500 (Internal Server Error) if the meaningDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/meanings")
    @Timed
    public ResponseEntity<MeaningDTO> updateMeaning(@RequestBody MeaningDTO meaningDTO) throws URISyntaxException {
        log.debug("REST request to update Meaning : {}", meaningDTO);
        if (meaningDTO.getId() == null) {
            return createMeaning(meaningDTO);
        }
        MeaningDTO result = meaningService.save(meaningDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, meaningDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /meanings : get all the meanings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of meanings in body
     */
    @GetMapping("/meanings")
    @Timed
    public ResponseEntity<List<MeaningDTO>> getAllMeanings(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of meanings");
        Page<MeaningDTO> page = meaningService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/meanings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /meanings/:id : get the "id" meaning.
     *
     * @param id the id of the meaningDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the meaningDTO, or with status 404 (Not Found)
     */
    @GetMapping("/meanings/{id}")
    @Timed
    public ResponseEntity<MeaningDTO> getMeaning(@PathVariable String id) {
        log.debug("REST request to get Meaning : {}", id);
        MeaningDTO meaningDTO = meaningService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(meaningDTO));
    }

    /**
     * DELETE  /meanings/:id : delete the "id" meaning.
     *
     * @param id the id of the meaningDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/meanings/{id}")
    @Timed
    public ResponseEntity<Void> deleteMeaning(@PathVariable String id) {
        log.debug("REST request to delete Meaning : {}", id);
        meaningService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
