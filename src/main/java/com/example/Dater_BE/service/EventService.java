package com.example.Dater_BE.service;

import com.example.Dater_BE.model.Event;
import com.example.Dater_BE.repository.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getStorage() {
        return eventRepository.findAll();
    }

}
