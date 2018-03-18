package com.mindmap.jane.service.impl;

import com.mindmap.jane.service.MeaningService;
import com.mindmap.jane.domain.Meaning;
import com.mindmap.jane.repository.MeaningRepository;
import com.mindmap.jane.service.dto.MeaningDTO;
import com.mindmap.jane.service.mapper.MeaningMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing Meaning.
 */
@Service
public class MeaningServiceImpl implements MeaningService {

    private final Logger log = LoggerFactory.getLogger(MeaningServiceImpl.class);

    private final MeaningRepository meaningRepository;

    private final MeaningMapper meaningMapper;

    public MeaningServiceImpl(MeaningRepository meaningRepository, MeaningMapper meaningMapper) {
        this.meaningRepository = meaningRepository;
        this.meaningMapper = meaningMapper;
    }

    /**
     * Save a meaning.
     *
     * @param meaningDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MeaningDTO save(MeaningDTO meaningDTO) {
        log.debug("Request to save Meaning : {}", meaningDTO);
        Meaning meaning = meaningMapper.toEntity(meaningDTO);
        meaning = meaningRepository.save(meaning);
        return meaningMapper.toDto(meaning);
    }

    /**
     *  Get all the meanings.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<MeaningDTO> findAll(Pageable pageable) {
        log.debug("Request to get all meanings");
        return meaningRepository.findAll(pageable)
            .map(meaningMapper::toDto);
    }

    /**
     *  Get one meaning by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public MeaningDTO findOne(String id) {
        log.debug("Request to get Meaning : {}", id);
        Meaning meaning = meaningRepository.findOne(id);
        return meaningMapper.toDto(meaning);
    }

    /**
     *  Delete the  meaning by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Meaning : {}", id);
        meaningRepository.delete(id);
    }
}
