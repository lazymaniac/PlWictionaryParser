package com.mindmap.jane.service.impl;

import com.mindmap.jane.service.AdverbVarService;
import com.mindmap.jane.domain.AdverbVar;
import com.mindmap.jane.repository.AdverbVarRepository;
import com.mindmap.jane.service.dto.AdverbVarDTO;
import com.mindmap.jane.service.mapper.AdverbVarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing AdverbVar.
 */
@Service
public class AdverbVarServiceImpl implements AdverbVarService{

    private final Logger log = LoggerFactory.getLogger(AdverbVarServiceImpl.class);

    private final AdverbVarRepository adverbVarRepository;

    private final AdverbVarMapper adverbVarMapper;

    public AdverbVarServiceImpl(AdverbVarRepository adverbVarRepository, AdverbVarMapper adverbVarMapper) {
        this.adverbVarRepository = adverbVarRepository;
        this.adverbVarMapper = adverbVarMapper;
    }

    /**
     * Save a adverbVar.
     *
     * @param adverbVarDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AdverbVarDTO save(AdverbVarDTO adverbVarDTO) {
        log.debug("Request to save AdverbVar : {}", adverbVarDTO);
        AdverbVar adverbVar = adverbVarMapper.toEntity(adverbVarDTO);
        adverbVar = adverbVarRepository.save(adverbVar);
        return adverbVarMapper.toDto(adverbVar);
    }

    /**
     *  Get all the adverbVars.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<AdverbVarDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdverbVars");
        return adverbVarRepository.findAll(pageable)
            .map(adverbVarMapper::toDto);
    }

    /**
     *  Get one adverbVar by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public AdverbVarDTO findOne(String id) {
        log.debug("Request to get AdverbVar : {}", id);
        AdverbVar adverbVar = adverbVarRepository.findOne(id);
        return adverbVarMapper.toDto(adverbVar);
    }

    /**
     *  Delete the  adverbVar by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete AdverbVar : {}", id);
        adverbVarRepository.delete(id);
    }
}
