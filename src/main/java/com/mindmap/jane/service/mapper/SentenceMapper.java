package com.mindmap.jane.service.mapper;

import com.mindmap.jane.domain.Sentence;
import com.mindmap.jane.service.dto.SentenceDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Sentence and its DTO SentenceDTO.
 */
@Mapper(componentModel = "spring", uses = {LinkMapper.class})
public interface SentenceMapper extends EntityMapper<SentenceDTO, Sentence> {

}
