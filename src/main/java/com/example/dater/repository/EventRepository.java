package com.example.dater.repository;

import com.example.dater.model.Events;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface EventRepository extends MongoRepository<Events, String> {

    Optional<Events> findById(String id);
}
