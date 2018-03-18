package com.mindmap.jane.service;

import com.mindmap.jane.service.dto.MeaningDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Meaning.
 */
public interface MeaningService {

    /**
     * Save a meaning.
     *
     * @param meaningDTO the entity to save
     * @return the persisted entity
     */
    MeaningDTO save(MeaningDTO meaningDTO);

    /**
     *  Get all the meanings.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MeaningDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" meaning.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MeaningDTO findOne(String id);

    /**
     *  Delete the "id" meaning.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
