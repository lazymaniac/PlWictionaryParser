package com.mindmap.jane.service.impl;

import com.mindmap.jane.service.ExampleService;
import com.mindmap.jane.domain.Example;
import com.mindmap.jane.repository.ExampleRepository;
import com.mindmap.jane.service.dto.ExampleDTO;
import com.mindmap.jane.service.mapper.ExampleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing Example.
 */
@Service
public class ExampleServiceImpl implements ExampleService{

    private final Logger log = LoggerFactory.getLogger(ExampleServiceImpl.class);

    private final ExampleRepository exampleRepository;

    private final ExampleMapper exampleMapper;

    public ExampleServiceImpl(ExampleRepository exampleRepository, ExampleMapper exampleMapper) {
        this.exampleRepository = exampleRepository;
        this.exampleMapper = exampleMapper;
    }

    /**
     * Save a example.
     *
     * @param exampleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ExampleDTO save(ExampleDTO exampleDTO) {
        log.debug("Request to save Example : {}", exampleDTO);
        Example example = exampleMapper.toEntity(exampleDTO);
        example = exampleRepository.save(example);
        return exampleMapper.toDto(example);
    }

    /**
     *  Get all the examples.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<ExampleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Examples");
        return exampleRepository.findAll(pageable)
            .map(exampleMapper::toDto);
    }

    /**
     *  Get one example by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public ExampleDTO findOne(String id) {
        log.debug("Request to get Example : {}", id);
        Example example = exampleRepository.findOne(id);
        return exampleMapper.toDto(example);
    }

    /**
     *  Delete the  example by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Example : {}", id);
        exampleRepository.delete(id);
    }
}
