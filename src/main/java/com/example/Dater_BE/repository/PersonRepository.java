package com.example.Dater_BE.repository;

import java.util.List;

import com.example.Dater_BE.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "person", path = "person")
public interface PersonRepository extends MongoRepository<Person, String> {

  public List<Person> findByLastName(@Param("name") String name);

  // works
  public List<Person> findByFirstName(@Param("name") String firstName);
}
