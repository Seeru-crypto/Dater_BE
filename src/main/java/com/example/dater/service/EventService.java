package com.example.dater.service;

import com.example.dater.model.Events;
import com.example.dater.repository.EventRepository;
import com.example.dater.schedulingtasks.EventDateChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import java.time.Instant;
import java.util.List;

import static com.example.dater.model.Events.MAX_DATE;
import static com.example.dater.model.Events.MIN_DATE;

@RequiredArgsConstructor
@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventDateChecker eventDateChecker;
    private final HelperFunctions helperFunctions;

    public List<Events> findAll() {
        return eventRepository.findAll();
    }

    public Events save(Events events) {
        if (events.getDate().isBefore(MIN_DATE) || events.getDate().isAfter(MAX_DATE)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date is invalid");
        }

        events.setDateCreated(Instant.now());
        events.setDateNextReminder(helperFunctions.returnNextReminderDate(events));
        return eventRepository.save(events);
    }

    public Events update(Events eventsDto, String eventId) {
        Events events = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event with Id " +eventId+ " does not exist"));
        events.setName(eventsDto.getName())
                .setDate(eventsDto.getDate())
                .setDescription(eventsDto.getDescription())
                .setReminder(eventsDto.getReminder())
                .setReminderDays(eventsDto.getReminderDays())
                .setAccountForYear(eventsDto.getAccountForYear())
                .setDateUpdated(Instant.now())
                .setDateCreated(events.getDateCreated())
                .setDateNextReminder((helperFunctions.returnNextReminderDate(eventsDto))
                );
        return eventRepository.save(events);
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
