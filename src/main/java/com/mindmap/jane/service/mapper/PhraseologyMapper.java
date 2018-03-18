package com.mindmap.jane.service.mapper;

import com.mindmap.jane.domain.*;
import com.mindmap.jane.service.dto.PhraseologyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Phraseology and its DTO PhraseologyDTO.
 */
@Mapper(componentModel = "spring", uses = {SentenceMapper.class})
public interface PhraseologyMapper extends EntityMapper <PhraseologyDTO, Phraseology> {



}
