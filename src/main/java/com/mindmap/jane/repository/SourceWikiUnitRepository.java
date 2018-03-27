package com.mindmap.jane.repository;

import com.mindmap.jane.domain.SourceWikiUnit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the SourceWikiUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SourceWikiUnitRepository extends MongoRepository<SourceWikiUnit, String> {
}

