package com.example.Dater_BE.repository;

import java.util.List;

import com.example.Dater_BE.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4001")
@RepositoryRestResource(collectionResourceRel = "event", path = "event")

public interface EventRepository extends MongoRepository<Event, String> {

    List<Event> findByEventName(String eventName);

    List<Event> findByDate(String date);

    List<Event> findByReminder(Boolean reminder);

    List<Event> findByDescription(String description);

}
