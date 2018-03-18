package com.mindmap.jane.service;

import com.mindmap.jane.service.dto.VerbVarDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing VerbVar.
 */
public interface VerbVarService {

    /**
     * Save a verbVar.
     *
     * @param verbVarDTO the entity to save
     * @return the persisted entity
     */
    VerbVarDTO save(VerbVarDTO verbVarDTO);

    /**
     *  Get all the verbVars.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<VerbVarDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" verbVar.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    VerbVarDTO findOne(String id);

    /**
     *  Delete the "id" verbVar.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
