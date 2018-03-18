package com.mindmap.jane.service;

import com.mindmap.jane.service.dto.AdverbVarDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AdverbVar.
 */
public interface AdverbVarService {

    /**
     * Save a adverbVar.
     *
     * @param adverbVarDTO the entity to save
     * @return the persisted entity
     */
    AdverbVarDTO save(AdverbVarDTO adverbVarDTO);

    /**
     *  Get all the adverbVars.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AdverbVarDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" adverbVar.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AdverbVarDTO findOne(String id);

    /**
     *  Delete the "id" adverbVar.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
