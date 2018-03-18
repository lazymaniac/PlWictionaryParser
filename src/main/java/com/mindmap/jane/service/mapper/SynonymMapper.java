package com.mindmap.jane.service.mapper;

import com.mindmap.jane.domain.*;
import com.mindmap.jane.service.dto.SynonymDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Synonym and its DTO SynonymDTO.
 */
@Mapper(componentModel = "spring", uses = {LinkMapper.class})
public interface SynonymMapper extends EntityMapper <SynonymDTO, Synonym> {



}
