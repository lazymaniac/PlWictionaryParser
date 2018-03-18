package com.mindmap.jane.repository;

import com.mindmap.jane.domain.Cognate;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Cognate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CognateRepository extends MongoRepository<Cognate, String> {

}
