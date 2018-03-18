package com.mindmap.jane.service.mapper;

import com.mindmap.jane.domain.Link;
import com.mindmap.jane.service.dto.LinkDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Sentence and its DTO SentenceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LinkMapper extends EntityMapper<LinkDTO, Link> {

}
