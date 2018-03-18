package com.mindmap.jane.repository;

import com.mindmap.jane.domain.Phraseology;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Phraseology entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhraseologyRepository extends MongoRepository<Phraseology, String> {

}
