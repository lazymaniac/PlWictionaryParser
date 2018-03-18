package com.mindmap.jane.service.impl;

import com.mindmap.jane.service.CognateService;
import com.mindmap.jane.domain.Cognate;
import com.mindmap.jane.repository.CognateRepository;
import com.mindmap.jane.service.dto.CognateDTO;
import com.mindmap.jane.service.mapper.CognateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing Cognate.
 */
@Service
public class CognateServiceImpl implements CognateService{

    private final Logger log = LoggerFactory.getLogger(CognateServiceImpl.class);

    private final CognateRepository cognateRepository;

    private final CognateMapper cognateMapper;

    public CognateServiceImpl(CognateRepository cognateRepository, CognateMapper cognateMapper) {
        this.cognateRepository = cognateRepository;
        this.cognateMapper = cognateMapper;
    }

    /**
     * Save a cognate.
     *
     * @param cognateDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CognateDTO save(CognateDTO cognateDTO) {
        log.debug("Request to save Cognate : {}", cognateDTO);
        Cognate cognate = cognateMapper.toEntity(cognateDTO);
        cognate = cognateRepository.save(cognate);
        return cognateMapper.toDto(cognate);
    }

    /**
     *  Get all the cognates.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<CognateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cognates");
        return cognateRepository.findAll(pageable)
            .map(cognateMapper::toDto);
    }

    /**
     *  Get one cognate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public CognateDTO findOne(String id) {
        log.debug("Request to get Cognate : {}", id);
        Cognate cognate = cognateRepository.findOne(id);
        return cognateMapper.toDto(cognate);
    }

    /**
     *  Delete the  cognate by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Cognate : {}", id);
        cognateRepository.delete(id);
    }
}
