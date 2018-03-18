package com.mindmap.jane.service;

import com.mindmap.jane.service.dto.AdjectiveDegreeVarDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AdjectiveDegreeVar.
 */
public interface AdjectiveDegreeVarService {

    /**
     * Save a adjectiveDegreeVar.
     *
     * @param adjectiveDegreeVarDTO the entity to save
     * @return the persisted entity
     */
    AdjectiveDegreeVarDTO save(AdjectiveDegreeVarDTO adjectiveDegreeVarDTO);

    /**
     *  Get all the adjectiveDegreeVars.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AdjectiveDegreeVarDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" adjectiveDegreeVar.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AdjectiveDegreeVarDTO findOne(String id);

    /**
     *  Delete the "id" adjectiveDegreeVar.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
