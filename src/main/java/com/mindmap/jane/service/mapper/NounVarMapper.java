package com.mindmap.jane.service.mapper;

import com.mindmap.jane.domain.*;
import com.mindmap.jane.service.dto.NounVarDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity NounVar and its DTO NounVarDTO.
 */
@Mapper(componentModel = "spring", uses = {CasesVarMapper.class})
public interface NounVarMapper extends EntityMapper <NounVarDTO, NounVar> {

}
