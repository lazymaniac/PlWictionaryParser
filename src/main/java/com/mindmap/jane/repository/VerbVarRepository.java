package com.mindmap.jane.repository;

import com.mindmap.jane.domain.VerbVar;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the VerbVar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerbVarRepository extends MongoRepository<VerbVar, String> {

}
