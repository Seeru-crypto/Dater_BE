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

import static com.example.dater.model.Event.MAX_DATE;
import static com.example.dater.model.Event.MIN_DATE;

@RequiredArgsConstructor
@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventDateChecker eventDateChecker;
    private final HelperFunctions helperFunctions;

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Event save(Event event) {
        if (event.getDate().isBefore(MIN_DATE) || event.getDate().isAfter(MAX_DATE)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date is invalid");
        }

        event.setDateCreated(Instant.now());
        event.setDateNextReminder(helperFunctions.returnNextReminderDate(event));
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
                .setDateCreated(event.getDateCreated())
                .setDateNextReminder((helperFunctions.returnNextReminderDate(eventDto))
                );
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

    public void checkEventDates(String iniatedBy) {
        try {
            eventDateChecker.checkEventDates(iniatedBy);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
