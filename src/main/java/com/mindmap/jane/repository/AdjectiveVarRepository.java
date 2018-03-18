package com.mindmap.jane.repository;

import com.mindmap.jane.domain.AdjectiveVar;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the AdjectiveVar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdjectiveVarRepository extends MongoRepository<AdjectiveVar, String> {

}
