package com.mindmap.jane.service.mapper;

import com.mindmap.jane.domain.*;
import com.mindmap.jane.service.dto.MeaningDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Meaning and its DTO MeaningDTO.
 */
@Mapper(componentModel = "spring", uses = {SentenceMapper.class, SynonymMapper.class, ExampleMapper.class, AntonymMapper.class, PhraseologyMapper.class, CollocationMapper.class, CognateMapper.class, NounVarMapper.class, AdjectiveVarMapper.class, AdverbVarMapper.class, VerbVarMapper.class, PronounVarMapper.class, NumeralVarMapper.class})
public interface MeaningMapper extends EntityMapper <MeaningDTO, Meaning> {



}
