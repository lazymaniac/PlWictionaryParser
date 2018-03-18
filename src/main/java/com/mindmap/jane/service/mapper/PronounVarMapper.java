package com.mindmap.jane.service.mapper;

import com.mindmap.jane.domain.*;
import com.mindmap.jane.service.dto.PronounVarDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PronounVar and its DTO PronounVarDTO.
 */
@Mapper(componentModel = "spring", uses = {CasesVarMapper.class})
public interface PronounVarMapper extends EntityMapper <PronounVarDTO, PronounVar> {

}
