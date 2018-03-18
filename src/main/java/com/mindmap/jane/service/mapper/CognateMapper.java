package com.mindmap.jane.service.mapper;

import com.mindmap.jane.domain.*;
import com.mindmap.jane.service.dto.CognateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cognate and its DTO CognateDTO.
 */
@Mapper(componentModel = "spring", uses = {LinkMapper.class})
public interface CognateMapper extends EntityMapper <CognateDTO, Cognate> {



}
