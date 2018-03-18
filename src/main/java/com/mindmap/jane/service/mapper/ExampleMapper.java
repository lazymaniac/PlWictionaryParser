package com.mindmap.jane.service.mapper;

import com.mindmap.jane.domain.*;
import com.mindmap.jane.service.dto.ExampleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Example and its DTO ExampleDTO.
 */
@Mapper(componentModel = "spring", uses = {SentenceMapper.class, LinkMapper.class})
public interface ExampleMapper extends EntityMapper <ExampleDTO, Example> {



}
