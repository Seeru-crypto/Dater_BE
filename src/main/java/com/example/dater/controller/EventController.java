package com.example.dater.controller;

import com.example.dater.model.Event;
import com.example.dater.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// ToDo when deploying remove localhost from CORS list
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://date-manager-front.herokuapp.com/"})
@RestController
@RequestMapping(path = "api/events")
public class EventController {

    private final EventService eventService;

    @GetMapping
    public List<Event> findAll() {
        return eventService.findAll();
    }

    @PostMapping
    public Event save(@RequestBody Event newEvent) {
        return eventService.save(newEvent);
    }

    @DeleteMapping(path = "{eventId}")
    public void delete(@PathVariable("eventId") String eventId){
        eventService.delete(eventId);
    }

//    @DeleteMapping
//    public void deleteEvents(@RequestBody List<String> eventIds){
//        eventService.deleteEvents(eventIds);
//    }

    @PostMapping(path = "/delete")
    public void deleteEvents(@RequestBody List<String> eventIds) {
        eventService.deleteEvents(eventIds);
    }


    @PutMapping(path = "{eventId}")
    public void put(@PathVariable("eventId") String eventId, @RequestBody Event event ){
        eventService.update(event, eventId);
    }

    @GetMapping("/checkEvents")
    public void checkItems() {
        eventService.checkEventDates();
    }
}
