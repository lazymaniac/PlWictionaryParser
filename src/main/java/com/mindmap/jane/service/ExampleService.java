package com.mindmap.jane.service;

import com.mindmap.jane.service.dto.ExampleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Example.
 */
public interface ExampleService {

    /**
     * Save a example.
     *
     * @param exampleDTO the entity to save
     * @return the persisted entity
     */
    ExampleDTO save(ExampleDTO exampleDTO);

    /**
     *  Get all the examples.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ExampleDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" example.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ExampleDTO findOne(String id);

    /**
     *  Delete the "id" example.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
