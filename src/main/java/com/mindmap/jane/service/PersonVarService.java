package com.mindmap.jane.service;

import com.mindmap.jane.service.dto.PersonVarDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PersonVar.
 */
public interface PersonVarService {

    /**
     * Save a personVar.
     *
     * @param personVarDTO the entity to save
     * @return the persisted entity
     */
    PersonVarDTO save(PersonVarDTO personVarDTO);

    /**
     *  Get all the personVars.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonVarDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" personVar.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonVarDTO findOne(String id);

    /**
     *  Delete the "id" personVar.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
