package com.mindmap.jane.service.impl;

import com.mindmap.jane.domain.WikiUnit;
import com.mindmap.jane.repository.WikiUnitRepository;
import com.mindmap.jane.service.WikiUnitService;
import com.mindmap.jane.service.dto.WikiUnitDTO;
import com.mindmap.jane.service.mapper.WikiUnitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing WikiUnit.
 */
@Service
public class WikiUnitServiceImpl implements WikiUnitService {

    private final Logger log = LoggerFactory.getLogger(WikiUnitServiceImpl.class);

    private final WikiUnitRepository wikiUnitRepository;

    private final WikiUnitMapper wikiUnitMapper;

    public WikiUnitServiceImpl(WikiUnitRepository wikiUnitRepository, WikiUnitMapper wikiUnitMapper) {
        this.wikiUnitRepository = wikiUnitRepository;
        this.wikiUnitMapper = wikiUnitMapper;
    }

    /**
     * Save a wikiUnit.
     *
     * @param wikiUnitDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WikiUnitDTO save(WikiUnitDTO wikiUnitDTO) {
        log.debug("Request to save WikiUnit : {}", wikiUnitDTO);
        WikiUnit wikiUnit = wikiUnitMapper.toEntity(wikiUnitDTO);
        wikiUnit = wikiUnitRepository.save(wikiUnit);
        return wikiUnitMapper.toDto(wikiUnit);
    }

    /**
     * Get all the wikiUnits.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<WikiUnitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WikiUnits");
        return wikiUnitRepository.findAll(pageable)
            .map(wikiUnitMapper::toDto);
    }

    /**
     * Get one wikiUnit by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public WikiUnitDTO findOne(String id) {
        log.debug("Request to get WikiUnit : {}", id);
        WikiUnit wikiUnit = wikiUnitRepository.findOne(id);
        return wikiUnitMapper.toDto(wikiUnit);
    }

    /**
     * Delete the  wikiUnit by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete WikiUnit : {}", id);
        wikiUnitRepository.delete(id);
    }
}
