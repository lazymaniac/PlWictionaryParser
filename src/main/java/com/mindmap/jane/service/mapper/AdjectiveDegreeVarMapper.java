package com.mindmap.jane.service.mapper;

import com.mindmap.jane.domain.*;
import com.mindmap.jane.service.dto.AdjectiveDegreeVarDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AdjectiveDegreeVar and its DTO AdjectiveDegreeVarDTO.
 */
@Mapper(componentModel = "spring", uses = {CasesVarMapper.class})
public interface AdjectiveDegreeVarMapper extends EntityMapper <AdjectiveDegreeVarDTO, AdjectiveDegreeVar> {

}
