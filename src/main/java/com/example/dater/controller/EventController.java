package com.example.dater.controller;

import com.example.dater.model.Events;
import com.example.dater.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/event")
public class EventController {
    private static final String CHECK_INIATED_BY_ADMIN = "Admin";

    private final EventService eventService;

    @GetMapping
    public List<Events> findAll() {
        return eventService.findAll();
    }

    @PostMapping
    public Events save(@Valid @RequestBody Events newEvents) {
        if (newEvents.getId() != null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id cannot exist");
        return eventService.save(newEvents);
    }

    @PutMapping(path = "{eventId}")
    public Events put(@PathVariable String eventId, @Valid @RequestBody Events events) {
        return eventService.update(events, eventId);
    }
    @DeleteMapping(path = "{eventId}")
    public void delete(@Valid @PathVariable("eventId") String eventId){
        eventService.delete(eventId);
    }

    @PostMapping(path = "/delete")
    public void deleteEvents(@Valid @RequestBody List<String> eventIds) {
        eventService.deleteEvents(eventIds);
    }

    @GetMapping("/checkEvents")
    public void checkItems() {
        eventService.checkEventDates(CHECK_INIATED_BY_ADMIN);
    }
}
