package com.example.dater.schedulingtasks;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import javax.mail.MessagingException;

import com.example.dater.service.SendMailService;

import com.example.dater.model.Event;
import com.example.dater.service.EventService;

import java.util.*;

@Configuration
@Log4j2
public class EventDateChecker {
    private final EventService eventService;
    private SendMailService sendMailService;
    private Boolean sentStatus = false;

    List<Event> eventList;

    @Autowired
    public EventDateChecker(EventService eventService, SendMailService sendMailService) {
        this.eventService = eventService;
        this.sendMailService = sendMailService;
    }

    public void getEventData() throws MessagingException {
        eventList = eventService.getStorage();
        LocalDate currentDate = LocalDate.now();
        List<Event> eventsToSendOut = new ArrayList<>();
        for (Event event : eventList) {
            if (Boolean.FALSE.equals(event.getReminder())) {
                continue;
            }
            String date = event.getDate().substring(0, 10);
            LocalDate myObj = LocalDate.parse(date);
            Long reminderInDays = Long.parseLong(Integer.toString(event.getReminderDays()));
            LocalDate eventReminderDate = myObj.minusDays(reminderInDays);

            Boolean dayAndMonthMatch = (currentDate.getDayOfMonth() == eventReminderDate.getDayOfMonth()
                    && currentDate.getMonthValue() == eventReminderDate.getMonthValue());
            Boolean yearsMatch = (currentDate.getYear() == eventReminderDate.getYear());

            if (Boolean.TRUE.equals(event.getAccountForYear() && yearsMatch && dayAndMonthMatch)) {
                eventsToSendOut.add(event);
            }
            if (Boolean.TRUE.equals(!(event.getAccountForYear())) && Boolean.TRUE.equals(dayAndMonthMatch)) {
                eventsToSendOut.add(event);
            }
        }

        if (!sentStatus && eventsToSendOut.size() != 0) {

            for (int a = 0; a < eventsToSendOut.size(); a++) {
                Event event = eventsToSendOut.get(a);
                event.setDate(event.getDate().substring(0, 10));
                eventsToSendOut.set(a, event);
            }

            sendMailService.sendMimeMailList(eventsToSendOut);
            System.out.println("Events that were sent out " + eventsToSendOut.size());
            sentStatus = true;
        }
    }

}
