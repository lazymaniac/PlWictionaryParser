package com.mindmap.jane.service.impl;

import com.mindmap.jane.service.VerbVarService;
import com.mindmap.jane.domain.VerbVar;
import com.mindmap.jane.repository.VerbVarRepository;
import com.mindmap.jane.service.dto.VerbVarDTO;
import com.mindmap.jane.service.mapper.VerbVarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing VerbVar.
 */
@Service
public class VerbVarServiceImpl implements VerbVarService{

    private final Logger log = LoggerFactory.getLogger(VerbVarServiceImpl.class);

    private final VerbVarRepository verbVarRepository;

    private final VerbVarMapper verbVarMapper;

    public VerbVarServiceImpl(VerbVarRepository verbVarRepository, VerbVarMapper verbVarMapper) {
        this.verbVarRepository = verbVarRepository;
        this.verbVarMapper = verbVarMapper;
    }

    /**
     * Save a verbVar.
     *
     * @param verbVarDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VerbVarDTO save(VerbVarDTO verbVarDTO) {
        log.debug("Request to save VerbVar : {}", verbVarDTO);
        VerbVar verbVar = verbVarMapper.toEntity(verbVarDTO);
        verbVar = verbVarRepository.save(verbVar);
        return verbVarMapper.toDto(verbVar);
    }

    /**
     *  Get all the verbVars.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<VerbVarDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VerbVars");
        return verbVarRepository.findAll(pageable)
            .map(verbVarMapper::toDto);
    }

    /**
     *  Get one verbVar by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public VerbVarDTO findOne(String id) {
        log.debug("Request to get VerbVar : {}", id);
        VerbVar verbVar = verbVarRepository.findOne(id);
        return verbVarMapper.toDto(verbVar);
    }

    /**
     *  Delete the  verbVar by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete VerbVar : {}", id);
        verbVarRepository.delete(id);
    }
}
