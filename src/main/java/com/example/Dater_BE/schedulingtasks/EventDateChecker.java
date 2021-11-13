package com.example.Dater_BE.schedulingtasks;

import com.example.Dater_BE.model.Event;
import com.example.Dater_BE.service.EventService;
import com.example.Dater_BE.mailer.Mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;
import java.util.List;

import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

@Configuration
public class EventDateChecker {

    List<Event> eventList;
    Mail mailer = new Mail();

    private final EventService eventService;

    @Autowired
    public EventDateChecker(EventService eventService) {
        this.eventService = eventService;
    }

    public void getEventData() throws AddressException, MessagingException, IOException {
        eventList = eventService.getStorage();
        LocalDate currentDate = LocalDate.now();

        for (Event event : eventList) {
            if (!event.getReminder())
                continue;
            System.out.println("Checking event: " + event.getEventName());
            String date = event.getDate().substring(0, 10);
            LocalDate myObj = LocalDate.parse((CharSequence) date);
            Long reminderInDays = Long.parseLong(Integer.toString(event.getReminderDays()));
            LocalDate eventReminderDate = myObj.minusDays(reminderInDays);

            if (currentDate.equals(eventReminderDate)) {
                System.out.println("Send out an email! for event " + event.getEventName());
                mailer.send(event);
                System.out.println("***");

            }
        }
    }
}
