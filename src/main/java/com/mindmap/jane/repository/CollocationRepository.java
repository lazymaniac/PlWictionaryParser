package com.mindmap.jane.repository;

import com.mindmap.jane.domain.Collocation;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Collocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollocationRepository extends MongoRepository<Collocation, String> {

}
