package com.mindmap.jane.repository;

import com.mindmap.jane.domain.Sentence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Sentence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SentenceRepository extends MongoRepository<Sentence, String> {
}
