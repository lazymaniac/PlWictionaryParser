package com.mindmap.jane.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mindmap.jane.service.PersonVarService;
import com.mindmap.jane.web.rest.util.HeaderUtil;
import com.mindmap.jane.web.rest.util.PaginationUtil;
import com.mindmap.jane.service.dto.PersonVarDTO;
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
 * REST controller for managing PersonVar.
 */
@RestController
@RequestMapping("/api")
public class PersonVarResource {

    private final Logger log = LoggerFactory.getLogger(PersonVarResource.class);

    private static final String ENTITY_NAME = "personVar";

    private final PersonVarService personVarService;

    public PersonVarResource(PersonVarService personVarService) {
        this.personVarService = personVarService;
    }

    /**
     * POST  /person-vars : Create a new personVar.
     *
     * @param personVarDTO the personVarDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personVarDTO, or with status 400 (Bad Request) if the personVar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-vars")
    @Timed
    public ResponseEntity<PersonVarDTO> createPersonVar(@RequestBody PersonVarDTO personVarDTO) throws URISyntaxException {
        log.debug("REST request to save PersonVar : {}", personVarDTO);
        if (personVarDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new personVar cannot already have an ID")).body(null);
        }
        PersonVarDTO result = personVarService.save(personVarDTO);
        return ResponseEntity.created(new URI("/api/person-vars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-vars : Updates an existing personVar.
     *
     * @param personVarDTO the personVarDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personVarDTO,
     * or with status 400 (Bad Request) if the personVarDTO is not valid,
     * or with status 500 (Internal Server Error) if the personVarDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-vars")
    @Timed
    public ResponseEntity<PersonVarDTO> updatePersonVar(@RequestBody PersonVarDTO personVarDTO) throws URISyntaxException {
        log.debug("REST request to update PersonVar : {}", personVarDTO);
        if (personVarDTO.getId() == null) {
            return createPersonVar(personVarDTO);
        }
        PersonVarDTO result = personVarService.save(personVarDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personVarDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-vars : get all the personVars.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personVars in body
     */
    @GetMapping("/person-vars")
    @Timed
    public ResponseEntity<List<PersonVarDTO>> getAllPersonVars(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of PersonVars");
        Page<PersonVarDTO> page = personVarService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-vars");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-vars/:id : get the "id" personVar.
     *
     * @param id the id of the personVarDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personVarDTO, or with status 404 (Not Found)
     */
    @GetMapping("/person-vars/{id}")
    @Timed
    public ResponseEntity<PersonVarDTO> getPersonVar(@PathVariable String id) {
        log.debug("REST request to get PersonVar : {}", id);
        PersonVarDTO personVarDTO = personVarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(personVarDTO));
    }

    /**
     * DELETE  /person-vars/:id : delete the "id" personVar.
     *
     * @param id the id of the personVarDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-vars/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonVar(@PathVariable String id) {
        log.debug("REST request to delete PersonVar : {}", id);
        personVarService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
