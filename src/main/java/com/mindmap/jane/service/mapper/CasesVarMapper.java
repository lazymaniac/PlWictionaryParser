package com.mindmap.jane.service.mapper;

import com.mindmap.jane.domain.*;
import com.mindmap.jane.service.dto.CasesVarDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CasesVar and its DTO CasesVarDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CasesVarMapper extends EntityMapper <CasesVarDTO, CasesVar> {
    
    

}
