package com.mindmap.jane.service.mapper;

import com.mindmap.jane.domain.*;
import com.mindmap.jane.service.dto.AdjectiveVarDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AdjectiveVar and its DTO AdjectiveVarDTO.
 */
@Mapper(componentModel = "spring", uses = {AdjectiveDegreeVarMapper.class})
public interface AdjectiveVarMapper extends EntityMapper <AdjectiveVarDTO, AdjectiveVar> {

}
