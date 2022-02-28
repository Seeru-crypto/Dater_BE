package com.example.dater.controller;

import com.example.dater.model.Event;
import com.example.dater.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RequiredArgsConstructor
//@CrossOrigin(origins = {"http://localhost:3000", "https://date-manager-front.herokuapp.com/"})
@RestController
@RequestMapping(path = "api/events")
public class EventController {
    public String checkIniatedByAdmin = "Admin";

    private final EventService eventService;

    @GetMapping
    public List<Event> findAll() {
        return eventService.findAll();
    }

    // ToDo Fix bug where a user can insert their own ID
    @PostMapping
    public Event save(@Valid @RequestBody Event newEvent) {
        return eventService.save(newEvent);
    }

    @DeleteMapping(path = "{eventId}")
    public void delete(@Valid @PathVariable("eventId") String eventId){
        eventService.delete(eventId);
    }

    @PostMapping(path = "/delete")
    public void deleteEvents(@Valid @RequestBody List<String> eventIds) {
        eventService.deleteEvents(eventIds);
    }

    @PutMapping(path = "{eventId}")
    public void put(@Valid @PathVariable("eventId") String eventId, @RequestBody Event event ){
        eventService.update(event, eventId);
    }

    @GetMapping("/checkEvents")
    public void checkItems() {
        eventService.checkEventDates(checkIniatedByAdmin);
    }
}
