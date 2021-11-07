package com.example.Dater_BE.repository;

import java.util.List;

import com.example.Dater_BE.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "event", path = "event")
public interface EventRepository extends MongoRepository<Event, String> {

    Event findByEventName(String eventName);

    Event findByDate(String date);

    Event findByEventReminder(Boolean reminder);

    Event findByEventDescription(String description);

}
