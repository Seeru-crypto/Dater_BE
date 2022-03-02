package com.example.dater.controller;

import com.example.dater.model.Event;
import com.example.dater.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/events")
public class EventController {
    public String checkIniatedByAdmin = "Admin";

    private final EventService eventService;

    @GetMapping
    public List<Event> findAll() {
        return eventService.findAll();
    }

    @PostMapping
    public Event save(@Valid @RequestBody Event newEvent) {
        if (newEvent.getId() != null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id cannot exist");
        return eventService.save(newEvent);
    }

    @PutMapping(path = "{eventId}")
    public Event put(@PathVariable String eventId, @Valid @RequestBody Event event) {
        return eventService.update(event, eventId);
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
        eventService.checkEventDates(checkIniatedByAdmin);
    }
}
