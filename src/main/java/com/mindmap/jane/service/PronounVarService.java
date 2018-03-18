package com.mindmap.jane.service;

import com.mindmap.jane.service.dto.PronounVarDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PronounVar.
 */
public interface PronounVarService {

    /**
     * Save a pronounVar.
     *
     * @param pronounVarDTO the entity to save
     * @return the persisted entity
     */
    PronounVarDTO save(PronounVarDTO pronounVarDTO);

    /**
     *  Get all the pronounVars.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PronounVarDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" pronounVar.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PronounVarDTO findOne(String id);

    /**
     *  Delete the "id" pronounVar.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
