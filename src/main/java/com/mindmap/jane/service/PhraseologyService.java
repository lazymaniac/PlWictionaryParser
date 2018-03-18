package com.mindmap.jane.service;

import com.mindmap.jane.service.dto.PhraseologyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Phraseology.
 */
public interface PhraseologyService {

    /**
     * Save a phraseology.
     *
     * @param phraseologyDTO the entity to save
     * @return the persisted entity
     */
    PhraseologyDTO save(PhraseologyDTO phraseologyDTO);

    /**
     *  Get all the phraseologies.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PhraseologyDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" phraseology.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PhraseologyDTO findOne(String id);

    /**
     *  Delete the "id" phraseology.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
