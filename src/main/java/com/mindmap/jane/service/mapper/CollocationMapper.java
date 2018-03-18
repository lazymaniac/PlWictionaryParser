package com.mindmap.jane.service.mapper;

import com.mindmap.jane.domain.*;
import com.mindmap.jane.service.dto.CollocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Collocation and its DTO CollocationDTO.
 */
@Mapper(componentModel = "spring", uses = {LinkMapper.class})
public interface CollocationMapper extends EntityMapper <CollocationDTO, Collocation> {



}
