package com.mindmap.jane.service.impl;

import com.mindmap.jane.service.PronounVarService;
import com.mindmap.jane.domain.PronounVar;
import com.mindmap.jane.repository.PronounVarRepository;
import com.mindmap.jane.service.dto.PronounVarDTO;
import com.mindmap.jane.service.mapper.PronounVarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing PronounVar.
 */
@Service
public class PronounVarServiceImpl implements PronounVarService{

    private final Logger log = LoggerFactory.getLogger(PronounVarServiceImpl.class);

    private final PronounVarRepository pronounVarRepository;

    private final PronounVarMapper pronounVarMapper;

    public PronounVarServiceImpl(PronounVarRepository pronounVarRepository, PronounVarMapper pronounVarMapper) {
        this.pronounVarRepository = pronounVarRepository;
        this.pronounVarMapper = pronounVarMapper;
    }

    /**
     * Save a pronounVar.
     *
     * @param pronounVarDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PronounVarDTO save(PronounVarDTO pronounVarDTO) {
        log.debug("Request to save PronounVar : {}", pronounVarDTO);
        PronounVar pronounVar = pronounVarMapper.toEntity(pronounVarDTO);
        pronounVar = pronounVarRepository.save(pronounVar);
        return pronounVarMapper.toDto(pronounVar);
    }

    /**
     *  Get all the pronounVars.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<PronounVarDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PronounVars");
        return pronounVarRepository.findAll(pageable)
            .map(pronounVarMapper::toDto);
    }

    /**
     *  Get one pronounVar by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public PronounVarDTO findOne(String id) {
        log.debug("Request to get PronounVar : {}", id);
        PronounVar pronounVar = pronounVarRepository.findOne(id);
        return pronounVarMapper.toDto(pronounVar);
    }

    /**
     *  Delete the  pronounVar by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete PronounVar : {}", id);
        pronounVarRepository.delete(id);
    }
}
