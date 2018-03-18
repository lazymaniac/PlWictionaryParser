package com.mindmap.jane.service;

import com.mindmap.jane.service.dto.CasesVarDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CasesVar.
 */
public interface CasesVarService {

    /**
     * Save a casesVar.
     *
     * @param casesVarDTO the entity to save
     * @return the persisted entity
     */
    CasesVarDTO save(CasesVarDTO casesVarDTO);

    /**
     *  Get all the casesVars.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CasesVarDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" casesVar.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CasesVarDTO findOne(String id);

    /**
     *  Delete the "id" casesVar.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
