package com.mindmap.jane.service.mapper;

import com.mindmap.jane.domain.*;
import com.mindmap.jane.service.dto.WikiUnitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity WikiUnit and its DTO WikiUnitDTO.
 */
@Mapper(componentModel = "spring", uses = {LinkMapper.class, SentenceMapper.class, MeaningMapper.class})
public interface WikiUnitMapper extends EntityMapper <WikiUnitDTO, WikiUnit> {



}
