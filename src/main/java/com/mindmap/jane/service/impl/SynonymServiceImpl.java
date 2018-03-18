package com.mindmap.jane.service.impl;

import com.mindmap.jane.service.SynonymService;
import com.mindmap.jane.domain.Synonym;
import com.mindmap.jane.repository.SynonymRepository;
import com.mindmap.jane.service.dto.SynonymDTO;
import com.mindmap.jane.service.mapper.SynonymMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing Synonym.
 */
@Service
public class SynonymServiceImpl implements SynonymService{

    private final Logger log = LoggerFactory.getLogger(SynonymServiceImpl.class);

    private final SynonymRepository synonymRepository;

    private final SynonymMapper synonymMapper;

    public SynonymServiceImpl(SynonymRepository synonymRepository, SynonymMapper synonymMapper) {
        this.synonymRepository = synonymRepository;
        this.synonymMapper = synonymMapper;
    }

    /**
     * Save a synonym.
     *
     * @param synonymDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SynonymDTO save(SynonymDTO synonymDTO) {
        log.debug("Request to save Synonym : {}", synonymDTO);
        Synonym synonym = synonymMapper.toEntity(synonymDTO);
        synonym = synonymRepository.save(synonym);
        return synonymMapper.toDto(synonym);
    }

    /**
     *  Get all the synonyms.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<SynonymDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Synonyms");
        return synonymRepository.findAll(pageable)
            .map(synonymMapper::toDto);
    }

    /**
     *  Get one synonym by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public SynonymDTO findOne(String id) {
        log.debug("Request to get Synonym : {}", id);
        Synonym synonym = synonymRepository.findOne(id);
        return synonymMapper.toDto(synonym);
    }

    /**
     *  Delete the  synonym by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Synonym : {}", id);
        synonymRepository.delete(id);
    }
}
