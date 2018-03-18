package com.mindmap.jane.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mindmap.jane.service.AntonymService;
import com.mindmap.jane.web.rest.util.HeaderUtil;
import com.mindmap.jane.web.rest.util.PaginationUtil;
import com.mindmap.jane.service.dto.AntonymDTO;
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
 * REST controller for managing Antonym.
 */
@RestController
@RequestMapping("/api")
public class AntonymResource {

    private final Logger log = LoggerFactory.getLogger(AntonymResource.class);

    private static final String ENTITY_NAME = "antonym";

    private final AntonymService antonymService;

    public AntonymResource(AntonymService antonymService) {
        this.antonymService = antonymService;
    }

    /**
     * POST  /antonyms : Create a new antonym.
     *
     * @param antonymDTO the antonymDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new antonymDTO, or with status 400 (Bad Request) if the antonym has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/antonyms")
    @Timed
    public ResponseEntity<AntonymDTO> createAntonym(@RequestBody AntonymDTO antonymDTO) throws URISyntaxException {
        log.debug("REST request to save Antonym : {}", antonymDTO);
        if (antonymDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new antonym cannot already have an ID")).body(null);
        }
        AntonymDTO result = antonymService.save(antonymDTO);
        return ResponseEntity.created(new URI("/api/antonyms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /antonyms : Updates an existing antonym.
     *
     * @param antonymDTO the antonymDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated antonymDTO,
     * or with status 400 (Bad Request) if the antonymDTO is not valid,
     * or with status 500 (Internal Server Error) if the antonymDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/antonyms")
    @Timed
    public ResponseEntity<AntonymDTO> updateAntonym(@RequestBody AntonymDTO antonymDTO) throws URISyntaxException {
        log.debug("REST request to update Antonym : {}", antonymDTO);
        if (antonymDTO.getId() == null) {
            return createAntonym(antonymDTO);
        }
        AntonymDTO result = antonymService.save(antonymDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, antonymDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /antonyms : get all the antonyms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of antonyms in body
     */
    @GetMapping("/antonyms")
    @Timed
    public ResponseEntity<List<AntonymDTO>> getAllAntonyms(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Antonyms");
        Page<AntonymDTO> page = antonymService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/antonyms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /antonyms/:id : get the "id" antonym.
     *
     * @param id the id of the antonymDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the antonymDTO, or with status 404 (Not Found)
     */
    @GetMapping("/antonyms/{id}")
    @Timed
    public ResponseEntity<AntonymDTO> getAntonym(@PathVariable String id) {
        log.debug("REST request to get Antonym : {}", id);
        AntonymDTO antonymDTO = antonymService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(antonymDTO));
    }

    /**
     * DELETE  /antonyms/:id : delete the "id" antonym.
     *
     * @param id the id of the antonymDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/antonyms/{id}")
    @Timed
    public ResponseEntity<Void> deleteAntonym(@PathVariable String id) {
        log.debug("REST request to delete Antonym : {}", id);
        antonymService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
