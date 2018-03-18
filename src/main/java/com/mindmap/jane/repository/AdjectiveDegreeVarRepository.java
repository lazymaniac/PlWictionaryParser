package com.mindmap.jane.repository;

import com.mindmap.jane.domain.AdjectiveDegreeVar;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the AdjectiveDegreeVar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdjectiveDegreeVarRepository extends MongoRepository<AdjectiveDegreeVar, String> {

}
