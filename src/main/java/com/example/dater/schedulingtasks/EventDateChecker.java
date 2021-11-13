package com.example.dater.schedulingtasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;
import java.util.List;
import javax.mail.MessagingException;

import com.example.dater.mailer.Mail;
import com.example.dater.model.Event;
import com.example.dater.service.EventService;

@Configuration
public class EventDateChecker {

    List<Event> eventList;
    Mail mailer = new Mail();

    private final EventService eventService;

    @Autowired
    public EventDateChecker(EventService eventService) {
        this.eventService = eventService;
    }

    public void getEventData() throws MessagingException {
        eventList = eventService.getStorage();
        LocalDate currentDate = LocalDate.now();

        for (Event event : eventList) {
            if (Boolean.FALSE.equals(event.getReminder()))
                continue;
            String date = event.getDate().substring(0, 10);
            LocalDate myObj = LocalDate.parse(date);
            Long reminderInDays = Long.parseLong(Integer.toString(event.getReminderDays()));
            LocalDate eventReminderDate = myObj.minusDays(reminderInDays);

            if (currentDate.equals(eventReminderDate)) {
                mailer.send(event);

            }
        }
    }
}
