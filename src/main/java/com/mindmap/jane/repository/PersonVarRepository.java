package com.mindmap.jane.repository;

import com.mindmap.jane.domain.PersonVar;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the PersonVar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonVarRepository extends MongoRepository<PersonVar, String> {

}
