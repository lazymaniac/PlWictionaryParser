package com.mindmap.jane.service.impl;

import com.mindmap.jane.service.CollocationService;
import com.mindmap.jane.domain.Collocation;
import com.mindmap.jane.repository.CollocationRepository;
import com.mindmap.jane.service.dto.CollocationDTO;
import com.mindmap.jane.service.mapper.CollocationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing Collocation.
 */
@Service
public class CollocationServiceImpl implements CollocationService{

    private final Logger log = LoggerFactory.getLogger(CollocationServiceImpl.class);

    private final CollocationRepository collocationRepository;

    private final CollocationMapper collocationMapper;

    public CollocationServiceImpl(CollocationRepository collocationRepository, CollocationMapper collocationMapper) {
        this.collocationRepository = collocationRepository;
        this.collocationMapper = collocationMapper;
    }

    /**
     * Save a collocation.
     *
     * @param collocationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CollocationDTO save(CollocationDTO collocationDTO) {
        log.debug("Request to save Collocation : {}", collocationDTO);
        Collocation collocation = collocationMapper.toEntity(collocationDTO);
        collocation = collocationRepository.save(collocation);
        return collocationMapper.toDto(collocation);
    }

    /**
     *  Get all the collocations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<CollocationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Collocations");
        return collocationRepository.findAll(pageable)
            .map(collocationMapper::toDto);
    }

    /**
     *  Get one collocation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public CollocationDTO findOne(String id) {
        log.debug("Request to get Collocation : {}", id);
        Collocation collocation = collocationRepository.findOne(id);
        return collocationMapper.toDto(collocation);
    }

    /**
     *  Delete the  collocation by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Collocation : {}", id);
        collocationRepository.delete(id);
    }
}
