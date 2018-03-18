package com.mindmap.jane.service;

import com.mindmap.jane.service.dto.AdjectiveVarDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AdjectiveVar.
 */
public interface AdjectiveVarService {

    /**
     * Save a adjectiveVar.
     *
     * @param adjectiveVarDTO the entity to save
     * @return the persisted entity
     */
    AdjectiveVarDTO save(AdjectiveVarDTO adjectiveVarDTO);

    /**
     *  Get all the adjectiveVars.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AdjectiveVarDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" adjectiveVar.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AdjectiveVarDTO findOne(String id);

    /**
     *  Delete the "id" adjectiveVar.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
