package com.mindmap.jane.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mindmap.jane.service.ExampleService;
import com.mindmap.jane.web.rest.util.HeaderUtil;
import com.mindmap.jane.web.rest.util.PaginationUtil;
import com.mindmap.jane.service.dto.ExampleDTO;
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
 * REST controller for managing Example.
 */
@RestController
@RequestMapping("/api")
public class ExampleResource {

    private final Logger log = LoggerFactory.getLogger(ExampleResource.class);

    private static final String ENTITY_NAME = "example";

    private final ExampleService exampleService;

    public ExampleResource(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    /**
     * POST  /examples : Create a new example.
     *
     * @param exampleDTO the exampleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new exampleDTO, or with status 400 (Bad Request) if the example has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/examples")
    @Timed
    public ResponseEntity<ExampleDTO> createExample(@RequestBody ExampleDTO exampleDTO) throws URISyntaxException {
        log.debug("REST request to save Example : {}", exampleDTO);
        if (exampleDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new example cannot already have an ID")).body(null);
        }
        ExampleDTO result = exampleService.save(exampleDTO);
        return ResponseEntity.created(new URI("/api/examples/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /examples : Updates an existing example.
     *
     * @param exampleDTO the exampleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated exampleDTO,
     * or with status 400 (Bad Request) if the exampleDTO is not valid,
     * or with status 500 (Internal Server Error) if the exampleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/examples")
    @Timed
    public ResponseEntity<ExampleDTO> updateExample(@RequestBody ExampleDTO exampleDTO) throws URISyntaxException {
        log.debug("REST request to update Example : {}", exampleDTO);
        if (exampleDTO.getId() == null) {
            return createExample(exampleDTO);
        }
        ExampleDTO result = exampleService.save(exampleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, exampleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /examples : get all the examples.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of examples in body
     */
    @GetMapping("/examples")
    @Timed
    public ResponseEntity<List<ExampleDTO>> getAllExamples(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Examples");
        Page<ExampleDTO> page = exampleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/examples");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /examples/:id : get the "id" example.
     *
     * @param id the id of the exampleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the exampleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/examples/{id}")
    @Timed
    public ResponseEntity<ExampleDTO> getExample(@PathVariable String id) {
        log.debug("REST request to get Example : {}", id);
        ExampleDTO exampleDTO = exampleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(exampleDTO));
    }

    /**
     * DELETE  /examples/:id : delete the "id" example.
     *
     * @param id the id of the exampleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/examples/{id}")
    @Timed
    public ResponseEntity<Void> deleteExample(@PathVariable String id) {
        log.debug("REST request to delete Example : {}", id);
        exampleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
