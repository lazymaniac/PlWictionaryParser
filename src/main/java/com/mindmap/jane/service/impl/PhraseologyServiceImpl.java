package com.mindmap.jane.service.impl;

import com.mindmap.jane.service.PhraseologyService;
import com.mindmap.jane.domain.Phraseology;
import com.mindmap.jane.repository.PhraseologyRepository;
import com.mindmap.jane.service.dto.PhraseologyDTO;
import com.mindmap.jane.service.mapper.PhraseologyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing Phraseology.
 */
@Service
public class PhraseologyServiceImpl implements PhraseologyService{

    private final Logger log = LoggerFactory.getLogger(PhraseologyServiceImpl.class);

    private final PhraseologyRepository phraseologyRepository;

    private final PhraseologyMapper phraseologyMapper;

    public PhraseologyServiceImpl(PhraseologyRepository phraseologyRepository, PhraseologyMapper phraseologyMapper) {
        this.phraseologyRepository = phraseologyRepository;
        this.phraseologyMapper = phraseologyMapper;
    }

    /**
     * Save a phraseology.
     *
     * @param phraseologyDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PhraseologyDTO save(PhraseologyDTO phraseologyDTO) {
        log.debug("Request to save Phraseology : {}", phraseologyDTO);
        Phraseology phraseology = phraseologyMapper.toEntity(phraseologyDTO);
        phraseology = phraseologyRepository.save(phraseology);
        return phraseologyMapper.toDto(phraseology);
    }

    /**
     *  Get all the phraseologies.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<PhraseologyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Phraseologies");
        return phraseologyRepository.findAll(pageable)
            .map(phraseologyMapper::toDto);
    }

    /**
     *  Get one phraseology by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public PhraseologyDTO findOne(String id) {
        log.debug("Request to get Phraseology : {}", id);
        Phraseology phraseology = phraseologyRepository.findOne(id);
        return phraseologyMapper.toDto(phraseology);
    }

    /**
     *  Delete the  phraseology by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Phraseology : {}", id);
        phraseologyRepository.delete(id);
    }
}
