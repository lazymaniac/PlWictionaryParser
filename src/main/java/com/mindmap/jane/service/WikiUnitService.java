package com.mindmap.jane.service;

import com.mindmap.jane.service.dto.WikiUnitDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing WikiUnit.
 */
public interface WikiUnitService {

    /**
     * Save a wikiUnit.
     *
     * @param wikiUnitDTO the entity to save
     * @return the persisted entity
     */
    WikiUnitDTO save(WikiUnitDTO wikiUnitDTO);

    /**
     *  Get all the wikiUnits.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<WikiUnitDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" wikiUnit.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    WikiUnitDTO findOne(String id);

    /**
     *  Delete the "id" wikiUnit.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
