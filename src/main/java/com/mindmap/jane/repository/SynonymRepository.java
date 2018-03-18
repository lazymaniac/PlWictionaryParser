package com.mindmap.jane.repository;

import com.mindmap.jane.domain.Synonym;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Synonym entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SynonymRepository extends MongoRepository<Synonym, String> {

}
