package com.example.dater.service;

import com.example.dater.model.Event;
import com.example.dater.repository.EventRepository;
import com.example.dater.schedulingtasks.EventDateChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventDateChecker eventDateChecker;

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Event save(Event event) {
        if (event.getId() != null || event.getDateCreated() != null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id cannot exist");
        LocalDateTime localDateTime = LocalDateTime.now(ZoneOffset.of("+02:00"));
        event.setDateCreated(localDateTime);
        return eventRepository.save(event);
    }

    @Transactional
    public Event update(Event event, String eventId) {
        Event exisingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event with Id " +eventId+ " does not exist"));
        LocalDateTime localDateTime = LocalDateTime.now(ZoneOffset.of("+02:00"));
        event.setDateUpdated(localDateTime);
        exisingEvent.setEvent(event);
        return eventRepository.save(exisingEvent);
    }

    public void delete(String eventId) {
        eventRepository.deleteById(eventId);
    }

    public void deleteEvents(List<String> eventIds) {
        eventRepository.deleteAllById(eventIds);
    }

    // ToDo will be replaced with proper o-auth in module 3
    public void checkEventDates(String iniatedBy) {
        try {
            eventDateChecker.checkEventDates(iniatedBy);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
