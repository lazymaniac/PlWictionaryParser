package com.mindmap.jane.repository;

import com.mindmap.jane.domain.AdverbVar;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the AdverbVar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdverbVarRepository extends MongoRepository<AdverbVar, String> {

}
