package com.mindmap.jane.service.impl;

import com.mindmap.jane.service.CasesVarService;
import com.mindmap.jane.domain.CasesVar;
import com.mindmap.jane.repository.CasesVarRepository;
import com.mindmap.jane.service.dto.CasesVarDTO;
import com.mindmap.jane.service.mapper.CasesVarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing CasesVar.
 */
@Service
public class CasesVarServiceImpl implements CasesVarService{

    private final Logger log = LoggerFactory.getLogger(CasesVarServiceImpl.class);

    private final CasesVarRepository casesVarRepository;

    private final CasesVarMapper casesVarMapper;

    public CasesVarServiceImpl(CasesVarRepository casesVarRepository, CasesVarMapper casesVarMapper) {
        this.casesVarRepository = casesVarRepository;
        this.casesVarMapper = casesVarMapper;
    }

    /**
     * Save a casesVar.
     *
     * @param casesVarDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CasesVarDTO save(CasesVarDTO casesVarDTO) {
        log.debug("Request to save CasesVar : {}", casesVarDTO);
        CasesVar casesVar = casesVarMapper.toEntity(casesVarDTO);
        casesVar = casesVarRepository.save(casesVar);
        return casesVarMapper.toDto(casesVar);
    }

    /**
     *  Get all the casesVars.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<CasesVarDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CasesVars");
        return casesVarRepository.findAll(pageable)
            .map(casesVarMapper::toDto);
    }

    /**
     *  Get one casesVar by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public CasesVarDTO findOne(String id) {
        log.debug("Request to get CasesVar : {}", id);
        CasesVar casesVar = casesVarRepository.findOne(id);
        return casesVarMapper.toDto(casesVar);
    }

    /**
     *  Delete the  casesVar by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete CasesVar : {}", id);
        casesVarRepository.delete(id);
    }
}
