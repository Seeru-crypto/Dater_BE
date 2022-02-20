package com.example.dater.service;

import com.example.dater.model.Event;
import com.example.dater.repository.EventRepository;
import com.example.dater.schedulingtasks.EventDateChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
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
        return eventRepository.save(event);
    }

    @Transactional
    public Event update(Event event, String eventId) {
        Event exisingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalStateException("event with ID " + eventId + "does not exist"));
        exisingEvent.setEvent(event);
        return eventRepository.save(exisingEvent);
    }

    public void delete(String eventId) {
        eventRepository.deleteById(eventId);
    }

    public void deleteEvents(List<String> eventIds) {
        eventRepository.deleteAllById(eventIds);
    }

    // ToDo replace temp pin with proper o-auth
    public void checkEventDates(String iniatedBy) {
        try {
            eventDateChecker.checkEventDates(iniatedBy);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
