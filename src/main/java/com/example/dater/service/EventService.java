package com.example.dater.service;

import com.example.dater.model.Event;
import com.example.dater.repository.EventRepository;
import com.example.dater.schedulingtasks.EventDateChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import java.time.Instant;
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
        event.setDateCreated(Instant.now());
        return eventRepository.save(event);
    }

    public Event update(Event eventDto, String eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event with Id " +eventId+ " does not exist"));
        event.setName(eventDto.getName())
                .setDate(eventDto.getDate())
                .setDescription(eventDto.getDescription())
                .setReminder(eventDto.getReminder())
                .setReminderDays(eventDto.getReminderDays())
                .setAccountForYear(eventDto.getAccountForYear())
                .setDateUpdated(Instant.now())
                .setDateCreated(event.getDateCreated());
        return eventRepository.save(event);
    }

    public void delete(String eventId) {
        eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event with Id " +eventId+ " does not exist"));
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
