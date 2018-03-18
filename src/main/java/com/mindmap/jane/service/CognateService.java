package com.mindmap.jane.service;

import com.mindmap.jane.service.dto.CognateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Cognate.
 */
public interface CognateService {

    /**
     * Save a cognate.
     *
     * @param cognateDTO the entity to save
     * @return the persisted entity
     */
    CognateDTO save(CognateDTO cognateDTO);

    /**
     *  Get all the cognates.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CognateDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" cognate.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CognateDTO findOne(String id);

    /**
     *  Delete the "id" cognate.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
