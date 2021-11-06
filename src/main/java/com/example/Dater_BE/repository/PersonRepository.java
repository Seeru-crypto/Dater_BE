package com.example.Dater_BE.repository;

import java.util.List;

import com.example.Dater_BE.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonRepository extends MongoRepository<Person, String> {

  List<Person> findByLastName(@Param("name") String name);

  List<Person> findByFirstName(@Param("name") String firstName);

  Person save(Person person);

  void delete(Person person);

  // Person findOne(String id);

  // Offical example
  // https://github.com/spring-projects/spring-data-book/blob/master/mongodb/src/main/java/com/oreilly/springdata/mongodb/core/CustomerRepository.java

}
