package com.example.dater.controller;

import java.util.List;

import com.example.dater.service.EventService;
import com.example.dater.model.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.dater.schedulingtasks.EventDateChecker;
import javax.mail.MessagingException;

@CrossOrigin
@RestController
@RequestMapping(path = "api/")
public class EventController {

    private final EventService eventService;
    private final EventDateChecker eventDateChecker;

    @Autowired
    public EventController(EventService eventService, EventDateChecker eventDateChecker) {
        this.eventService = eventService;
        this.eventDateChecker = eventDateChecker;
    }

    @GetMapping("/event")
    public List<Event> getItems() {
        return eventService.getStorage();
    }

    @GetMapping("/checkEvents")
    public void checkItems() {
        try {
            eventDateChecker.checkEventDates();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
