package com.example.dater.repository;

import java.util.List;

import com.example.dater.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "event", path = "event")
public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findByEvents(@Param("eventName") String eventName);

}
