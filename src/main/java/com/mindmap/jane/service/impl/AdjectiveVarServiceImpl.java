package com.mindmap.jane.service.impl;

import com.mindmap.jane.service.AdjectiveVarService;
import com.mindmap.jane.domain.AdjectiveVar;
import com.mindmap.jane.repository.AdjectiveVarRepository;
import com.mindmap.jane.service.dto.AdjectiveVarDTO;
import com.mindmap.jane.service.mapper.AdjectiveVarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing AdjectiveVar.
 */
@Service
public class AdjectiveVarServiceImpl implements AdjectiveVarService{

    private final Logger log = LoggerFactory.getLogger(AdjectiveVarServiceImpl.class);

    private final AdjectiveVarRepository adjectiveVarRepository;

    private final AdjectiveVarMapper adjectiveVarMapper;

    public AdjectiveVarServiceImpl(AdjectiveVarRepository adjectiveVarRepository, AdjectiveVarMapper adjectiveVarMapper) {
        this.adjectiveVarRepository = adjectiveVarRepository;
        this.adjectiveVarMapper = adjectiveVarMapper;
    }

    /**
     * Save a adjectiveVar.
     *
     * @param adjectiveVarDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AdjectiveVarDTO save(AdjectiveVarDTO adjectiveVarDTO) {
        log.debug("Request to save AdjectiveVar : {}", adjectiveVarDTO);
        AdjectiveVar adjectiveVar = adjectiveVarMapper.toEntity(adjectiveVarDTO);
        adjectiveVar = adjectiveVarRepository.save(adjectiveVar);
        return adjectiveVarMapper.toDto(adjectiveVar);
    }

    /**
     *  Get all the adjectiveVars.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<AdjectiveVarDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdjectiveVars");
        return adjectiveVarRepository.findAll(pageable)
            .map(adjectiveVarMapper::toDto);
    }

    /**
     *  Get one adjectiveVar by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public AdjectiveVarDTO findOne(String id) {
        log.debug("Request to get AdjectiveVar : {}", id);
        AdjectiveVar adjectiveVar = adjectiveVarRepository.findOne(id);
        return adjectiveVarMapper.toDto(adjectiveVar);
    }

    /**
     *  Delete the  adjectiveVar by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete AdjectiveVar : {}", id);
        adjectiveVarRepository.delete(id);
    }
}
