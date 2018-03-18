package com.mindmap.jane.service.mapper;

import com.mindmap.jane.domain.*;
import com.mindmap.jane.service.dto.AdverbVarDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AdverbVar and its DTO AdverbVarDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdverbVarMapper extends EntityMapper <AdverbVarDTO, AdverbVar> {
    
    

}
