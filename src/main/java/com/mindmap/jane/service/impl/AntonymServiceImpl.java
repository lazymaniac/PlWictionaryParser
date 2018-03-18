package com.mindmap.jane.service.impl;

import com.mindmap.jane.service.AntonymService;
import com.mindmap.jane.domain.Antonym;
import com.mindmap.jane.repository.AntonymRepository;
import com.mindmap.jane.service.dto.AntonymDTO;
import com.mindmap.jane.service.mapper.AntonymMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing Antonym.
 */
@Service
public class AntonymServiceImpl implements AntonymService{

    private final Logger log = LoggerFactory.getLogger(AntonymServiceImpl.class);

    private final AntonymRepository antonymRepository;

    private final AntonymMapper antonymMapper;

    public AntonymServiceImpl(AntonymRepository antonymRepository, AntonymMapper antonymMapper) {
        this.antonymRepository = antonymRepository;
        this.antonymMapper = antonymMapper;
    }

    /**
     * Save a antonym.
     *
     * @param antonymDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AntonymDTO save(AntonymDTO antonymDTO) {
        log.debug("Request to save Antonym : {}", antonymDTO);
        Antonym antonym = antonymMapper.toEntity(antonymDTO);
        antonym = antonymRepository.save(antonym);
        return antonymMapper.toDto(antonym);
    }

    /**
     *  Get all the antonyms.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<AntonymDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Antonyms");
        return antonymRepository.findAll(pageable)
            .map(antonymMapper::toDto);
    }

    /**
     *  Get one antonym by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public AntonymDTO findOne(String id) {
        log.debug("Request to get Antonym : {}", id);
        Antonym antonym = antonymRepository.findOne(id);
        return antonymMapper.toDto(antonym);
    }

    /**
     *  Delete the  antonym by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Antonym : {}", id);
        antonymRepository.delete(id);
    }
}
