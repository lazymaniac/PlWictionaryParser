package com.mindmap.jane.service.impl;

import com.mindmap.jane.service.NounVarService;
import com.mindmap.jane.domain.NounVar;
import com.mindmap.jane.repository.NounVarRepository;
import com.mindmap.jane.service.dto.NounVarDTO;
import com.mindmap.jane.service.mapper.NounVarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing NounVar.
 */
@Service
public class NounVarServiceImpl implements NounVarService{

    private final Logger log = LoggerFactory.getLogger(NounVarServiceImpl.class);

    private final NounVarRepository nounVarRepository;

    private final NounVarMapper nounVarMapper;

    public NounVarServiceImpl(NounVarRepository nounVarRepository, NounVarMapper nounVarMapper) {
        this.nounVarRepository = nounVarRepository;
        this.nounVarMapper = nounVarMapper;
    }

    /**
     * Save a nounVar.
     *
     * @param nounVarDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public NounVarDTO save(NounVarDTO nounVarDTO) {
        log.debug("Request to save NounVar : {}", nounVarDTO);
        NounVar nounVar = nounVarMapper.toEntity(nounVarDTO);
        nounVar = nounVarRepository.save(nounVar);
        return nounVarMapper.toDto(nounVar);
    }

    /**
     *  Get all the nounVars.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<NounVarDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NounVars");
        return nounVarRepository.findAll(pageable)
            .map(nounVarMapper::toDto);
    }

    /**
     *  Get one nounVar by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public NounVarDTO findOne(String id) {
        log.debug("Request to get NounVar : {}", id);
        NounVar nounVar = nounVarRepository.findOne(id);
        return nounVarMapper.toDto(nounVar);
    }

    /**
     *  Delete the  nounVar by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete NounVar : {}", id);
        nounVarRepository.delete(id);
    }
}
