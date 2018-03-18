package com.mindmap.jane.repository;

import com.mindmap.jane.domain.Antonym;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Antonym entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AntonymRepository extends MongoRepository<Antonym, String> {

}
