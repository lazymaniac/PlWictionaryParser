package com.mindmap.jane.service.mapper;

import com.mindmap.jane.domain.*;
import com.mindmap.jane.service.dto.VerbVarDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VerbVar and its DTO VerbVarDTO.
 */
@Mapper(componentModel = "spring", uses = {PersonVarMapper.class})
public interface VerbVarMapper extends EntityMapper <VerbVarDTO, VerbVar> {



}
