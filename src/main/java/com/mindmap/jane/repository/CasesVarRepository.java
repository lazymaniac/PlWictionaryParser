package com.mindmap.jane.repository;

import com.mindmap.jane.domain.CasesVar;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the CasesVar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CasesVarRepository extends MongoRepository<CasesVar, String> {

}
