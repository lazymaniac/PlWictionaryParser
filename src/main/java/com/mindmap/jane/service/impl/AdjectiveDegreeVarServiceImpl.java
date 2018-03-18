package com.mindmap.jane.service.impl;

import com.mindmap.jane.service.AdjectiveDegreeVarService;
import com.mindmap.jane.domain.AdjectiveDegreeVar;
import com.mindmap.jane.repository.AdjectiveDegreeVarRepository;
import com.mindmap.jane.service.dto.AdjectiveDegreeVarDTO;
import com.mindmap.jane.service.mapper.AdjectiveDegreeVarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing AdjectiveDegreeVar.
 */
@Service
public class AdjectiveDegreeVarServiceImpl implements AdjectiveDegreeVarService{

    private final Logger log = LoggerFactory.getLogger(AdjectiveDegreeVarServiceImpl.class);

    private final AdjectiveDegreeVarRepository adjectiveDegreeVarRepository;

    private final AdjectiveDegreeVarMapper adjectiveDegreeVarMapper;

    public AdjectiveDegreeVarServiceImpl(AdjectiveDegreeVarRepository adjectiveDegreeVarRepository, AdjectiveDegreeVarMapper adjectiveDegreeVarMapper) {
        this.adjectiveDegreeVarRepository = adjectiveDegreeVarRepository;
        this.adjectiveDegreeVarMapper = adjectiveDegreeVarMapper;
    }

    /**
     * Save a adjectiveDegreeVar.
     *
     * @param adjectiveDegreeVarDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AdjectiveDegreeVarDTO save(AdjectiveDegreeVarDTO adjectiveDegreeVarDTO) {
        log.debug("Request to save AdjectiveDegreeVar : {}", adjectiveDegreeVarDTO);
        AdjectiveDegreeVar adjectiveDegreeVar = adjectiveDegreeVarMapper.toEntity(adjectiveDegreeVarDTO);
        adjectiveDegreeVar = adjectiveDegreeVarRepository.save(adjectiveDegreeVar);
        return adjectiveDegreeVarMapper.toDto(adjectiveDegreeVar);
    }

    /**
     *  Get all the adjectiveDegreeVars.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<AdjectiveDegreeVarDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdjectiveDegreeVars");
        return adjectiveDegreeVarRepository.findAll(pageable)
            .map(adjectiveDegreeVarMapper::toDto);
    }

    /**
     *  Get one adjectiveDegreeVar by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public AdjectiveDegreeVarDTO findOne(String id) {
        log.debug("Request to get AdjectiveDegreeVar : {}", id);
        AdjectiveDegreeVar adjectiveDegreeVar = adjectiveDegreeVarRepository.findOne(id);
        return adjectiveDegreeVarMapper.toDto(adjectiveDegreeVar);
    }

    /**
     *  Delete the  adjectiveDegreeVar by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete AdjectiveDegreeVar : {}", id);
        adjectiveDegreeVarRepository.delete(id);
    }
}
