package com.example.Dater_BE.schedulingtasks;

import com.example.Dater_BE.model.Event;
import com.example.Dater_BE.service.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;
import java.util.List;

@Configuration
public class EventDateChecker {
    List<Event> eventList;

    private final EventService eventService;

    @Autowired
    public EventDateChecker(EventService eventService) {
        this.eventService = eventService;
    }

    public void getEventData() {

        eventList = eventService.getStorage();

        LocalDate currentDate = LocalDate.now();

        for (Event event : eventList) {

            if (!event.getReminder())
                continue;
            System.out.println("Checkking event " + event.getEventName());
            String date = event.getDate().substring(0, 10);
            LocalDate myObj = LocalDate.parse((CharSequence) date);
            Long reminderInDays = Long.parseLong(Integer.toString(event.getReminderDays()));
            LocalDate eventReminderDate = myObj.minusDays(reminderInDays);

            if (currentDate.equals(eventReminderDate)) {
                System.out.println("Send out an email! for event " + event.getEventName());
                System.out.println("***");

            }
        }
    }

    public void checkEventDates() {

    }
}
