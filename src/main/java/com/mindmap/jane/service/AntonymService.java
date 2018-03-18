package com.mindmap.jane.service;

import com.mindmap.jane.service.dto.AntonymDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Antonym.
 */
public interface AntonymService {

    /**
     * Save a antonym.
     *
     * @param antonymDTO the entity to save
     * @return the persisted entity
     */
    AntonymDTO save(AntonymDTO antonymDTO);

    /**
     *  Get all the antonyms.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AntonymDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" antonym.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AntonymDTO findOne(String id);

    /**
     *  Delete the "id" antonym.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
