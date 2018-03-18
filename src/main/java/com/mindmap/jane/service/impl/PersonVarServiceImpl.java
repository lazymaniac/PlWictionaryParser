package com.mindmap.jane.service.impl;

import com.mindmap.jane.service.PersonVarService;
import com.mindmap.jane.domain.PersonVar;
import com.mindmap.jane.repository.PersonVarRepository;
import com.mindmap.jane.service.dto.PersonVarDTO;
import com.mindmap.jane.service.mapper.PersonVarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing PersonVar.
 */
@Service
public class PersonVarServiceImpl implements PersonVarService{

    private final Logger log = LoggerFactory.getLogger(PersonVarServiceImpl.class);

    private final PersonVarRepository personVarRepository;

    private final PersonVarMapper personVarMapper;

    public PersonVarServiceImpl(PersonVarRepository personVarRepository, PersonVarMapper personVarMapper) {
        this.personVarRepository = personVarRepository;
        this.personVarMapper = personVarMapper;
    }

    /**
     * Save a personVar.
     *
     * @param personVarDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PersonVarDTO save(PersonVarDTO personVarDTO) {
        log.debug("Request to save PersonVar : {}", personVarDTO);
        PersonVar personVar = personVarMapper.toEntity(personVarDTO);
        personVar = personVarRepository.save(personVar);
        return personVarMapper.toDto(personVar);
    }

    /**
     *  Get all the personVars.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<PersonVarDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PersonVars");
        return personVarRepository.findAll(pageable)
            .map(personVarMapper::toDto);
    }

    /**
     *  Get one personVar by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public PersonVarDTO findOne(String id) {
        log.debug("Request to get PersonVar : {}", id);
        PersonVar personVar = personVarRepository.findOne(id);
        return personVarMapper.toDto(personVar);
    }

    /**
     *  Delete the  personVar by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete PersonVar : {}", id);
        personVarRepository.delete(id);
    }
}
