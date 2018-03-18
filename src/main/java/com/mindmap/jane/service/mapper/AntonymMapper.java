package com.mindmap.jane.service.mapper;

import com.mindmap.jane.domain.*;
import com.mindmap.jane.service.dto.AntonymDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Antonym and its DTO AntonymDTO.
 */
@Mapper(componentModel = "spring", uses = {LinkMapper.class})
public interface AntonymMapper extends EntityMapper <AntonymDTO, Antonym> {



}
