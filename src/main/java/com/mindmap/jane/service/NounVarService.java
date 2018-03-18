package com.mindmap.jane.service;

import com.mindmap.jane.service.dto.NounVarDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing NounVar.
 */
public interface NounVarService {

    /**
     * Save a nounVar.
     *
     * @param nounVarDTO the entity to save
     * @return the persisted entity
     */
    NounVarDTO save(NounVarDTO nounVarDTO);

    /**
     *  Get all the nounVars.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<NounVarDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" nounVar.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    NounVarDTO findOne(String id);

    /**
     *  Delete the "id" nounVar.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
