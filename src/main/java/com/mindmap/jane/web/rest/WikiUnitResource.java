package com.mindmap.jane.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mindmap.jane.service.WikiUnitService;
import com.mindmap.jane.web.rest.util.HeaderUtil;
import com.mindmap.jane.web.rest.util.PaginationUtil;
import com.mindmap.jane.service.dto.WikiUnitDTO;
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
 * REST controller for managing WikiUnit.
 */
@RestController
@RequestMapping("/api")
public class WikiUnitResource {

    private final Logger log = LoggerFactory.getLogger(WikiUnitResource.class);

    private static final String ENTITY_NAME = "wikiUnit";

    private final WikiUnitService wikiUnitService;

    public WikiUnitResource(WikiUnitService wikiUnitService) {
        this.wikiUnitService = wikiUnitService;
    }

    /**
     * POST  /wiki-units : Create a new wikiUnit.
     *
     * @param wikiUnitDTO the wikiUnitDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wikiUnitDTO, or with status 400 (Bad Request) if the wikiUnit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wiki-units")
    @Timed
    public ResponseEntity<WikiUnitDTO> createWikiUnit(@RequestBody WikiUnitDTO wikiUnitDTO) throws URISyntaxException {
        log.debug("REST request to save WikiUnit : {}", wikiUnitDTO);
        if (wikiUnitDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new wikiUnit cannot already have an ID")).body(null);
        }
        WikiUnitDTO result = wikiUnitService.save(wikiUnitDTO);
        return ResponseEntity.created(new URI("/api/wiki-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wiki-units : Updates an existing wikiUnit.
     *
     * @param wikiUnitDTO the wikiUnitDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wikiUnitDTO,
     * or with status 400 (Bad Request) if the wikiUnitDTO is not valid,
     * or with status 500 (Internal Server Error) if the wikiUnitDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wiki-units")
    @Timed
    public ResponseEntity<WikiUnitDTO> updateWikiUnit(@RequestBody WikiUnitDTO wikiUnitDTO) throws URISyntaxException {
        log.debug("REST request to update WikiUnit : {}", wikiUnitDTO);
        if (wikiUnitDTO.getId() == null) {
            return createWikiUnit(wikiUnitDTO);
        }
        WikiUnitDTO result = wikiUnitService.save(wikiUnitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, wikiUnitDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wiki-units : get all the wikiUnits.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of wikiUnits in body
     */
    @GetMapping("/wiki-units")
    @Timed
    public ResponseEntity<List<WikiUnitDTO>> getAllWikiUnits(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of WikiUnits");
        Page<WikiUnitDTO> page = wikiUnitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/wiki-units");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /wiki-units/:id : get the "id" wikiUnit.
     *
     * @param id the id of the wikiUnitDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wikiUnitDTO, or with status 404 (Not Found)
     */
    @GetMapping("/wiki-units/{id}")
    @Timed
    public ResponseEntity<WikiUnitDTO> getWikiUnit(@PathVariable String id) {
        log.debug("REST request to get WikiUnit : {}", id);
        WikiUnitDTO wikiUnitDTO = wikiUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(wikiUnitDTO));
    }

    /**
     * DELETE  /wiki-units/:id : delete the "id" wikiUnit.
     *
     * @param id the id of the wikiUnitDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wiki-units/{id}")
    @Timed
    public ResponseEntity<Void> deleteWikiUnit(@PathVariable String id) {
        log.debug("REST request to delete WikiUnit : {}", id);
        wikiUnitService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
