package com.example.dater.repository;

import com.example.dater.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface EventRepository extends MongoRepository<Event, String> {

    Optional<Event> findById(String id);
}
