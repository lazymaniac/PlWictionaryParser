package com.mindmap.jane.service;

import com.mindmap.jane.service.dto.CollocationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Collocation.
 */
public interface CollocationService {

    /**
     * Save a collocation.
     *
     * @param collocationDTO the entity to save
     * @return the persisted entity
     */
    CollocationDTO save(CollocationDTO collocationDTO);

    /**
     *  Get all the collocations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CollocationDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" collocation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CollocationDTO findOne(String id);

    /**
     *  Delete the "id" collocation.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
