package com.example.Dater_BE.schedulingtasks;

import com.example.Dater_BE.model.Event;
import com.example.Dater_BE.service.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;
import java.util.List;
import java.time.format.DateTimeFormatter;

@Configuration
public class EventDateChecker {
    private static final DateTimeFormatter ISO_LOCAL_DATE_TIME = null;
    List<Event> result;
    List<String> reminderDates;

    private final EventService eventService;

    @Autowired
    public EventDateChecker(EventService eventService) {
        this.eventService = eventService;
    }

    public void getEventData() {

        result = eventService.getStorage();

        LocalDateTime date2 = LocalDateTime.now();

        String parsedDate = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(date2).substring(0, 19);

        for (Event event : result) {
            String date = event.getDate().substring(0, 19);
            LocalDateTime myObj = LocalDateTime.parse((CharSequence) date);
            System.out.println("Current DateTime is " + parsedDate);

            System.out.println("reminder is " + event.getReminderDays());
            System.out.println("new Date obj is " + myObj);

        }
    }

    public void checkEventDates() {

    }
}
