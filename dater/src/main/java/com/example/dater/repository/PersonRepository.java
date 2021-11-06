package com.example.dater.repository;

import java.util.List;

import com.example.dater.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonRepository extends MongoRepository<Person, String> {

  List<Person> findByLastName(@Param("name") String name);

  List<Person> findByFirstName(@Param("name") String firstName);

  Person findByIdPerson(@Param("id") String id);

}
