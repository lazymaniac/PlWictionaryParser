package com.mindmap.jane.repository;

import com.mindmap.jane.domain.Meaning;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Meaning entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeaningRepository extends MongoRepository<Meaning, String> {

}
