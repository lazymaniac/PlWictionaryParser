package com.mindmap.jane.repository;

import com.mindmap.jane.domain.PronounVar;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the PronounVar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PronounVarRepository extends MongoRepository<PronounVar, String> {

}
