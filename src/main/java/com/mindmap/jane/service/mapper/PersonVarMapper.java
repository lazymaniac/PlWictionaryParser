package com.mindmap.jane.service.mapper;

import com.mindmap.jane.domain.*;
import com.mindmap.jane.service.dto.PersonVarDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PersonVar and its DTO PersonVarDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PersonVarMapper extends EntityMapper <PersonVarDTO, PersonVar> {
    
    

}
