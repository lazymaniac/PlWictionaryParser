package com.mindmap.jane.service;

import com.mindmap.jane.service.dto.SynonymDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Synonym.
 */
public interface SynonymService {

    /**
     * Save a synonym.
     *
     * @param synonymDTO the entity to save
     * @return the persisted entity
     */
    SynonymDTO save(SynonymDTO synonymDTO);

    /**
     *  Get all the synonyms.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SynonymDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" synonym.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SynonymDTO findOne(String id);

    /**
     *  Delete the "id" synonym.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
