package com.mindmap.jane.service.mapper;

import com.mindmap.jane.domain.NumeralVar;
import com.mindmap.jane.service.dto.NumeralVarDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity NounVar and its DTO NounVarDTO.
 */
@Mapper(componentModel = "spring", uses = {CasesVarMapper.class})
public interface NumeralVarMapper extends EntityMapper<NumeralVarDTO, NumeralVar> {
}
