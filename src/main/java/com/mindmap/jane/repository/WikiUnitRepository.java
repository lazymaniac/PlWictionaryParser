package com.mindmap.jane.repository;

import com.mindmap.jane.domain.WikiUnit;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the WikiUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WikiUnitRepository extends MongoRepository<WikiUnit, String> {

}
