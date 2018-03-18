package com.mindmap.jane.repository;

import com.mindmap.jane.domain.NounVar;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the NounVar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NounVarRepository extends MongoRepository<NounVar, String> {

}
