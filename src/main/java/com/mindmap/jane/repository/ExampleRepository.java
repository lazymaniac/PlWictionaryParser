package com.mindmap.jane.repository;

import com.mindmap.jane.domain.Example;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Example entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExampleRepository extends MongoRepository<Example, String> {

}
