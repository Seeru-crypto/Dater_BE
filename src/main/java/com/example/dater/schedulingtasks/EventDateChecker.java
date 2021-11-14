package com.example.dater.schedulingtasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;
import java.util.List;

import javax.mail.MessagingException;

import com.example.dater.service.SendMailService;

import com.example.dater.model.Event;
import com.example.dater.service.EventService;

@Configuration
public class EventDateChecker {
    private final EventService eventService;
    private SendMailService sendMailService;

    Boolean sentStatus = false;
    List<Event> eventList;

    @Autowired
    public EventDateChecker(EventService eventService, SendMailService sendMailService) {
        this.eventService = eventService;
        this.sendMailService = sendMailService;
    }

    public void getEventData() throws MessagingException {
        eventList = eventService.getStorage();
        LocalDate currentDate = LocalDate.now();
        for (Event event : eventList) {
            if (Boolean.FALSE.equals(event.getReminder())) {
                continue;
            }

            String date = event.getDate().substring(0, 10);
            LocalDate myObj = LocalDate.parse(date);
            Long reminderInDays = Long.parseLong(Integer.toString(event.getReminderDays()));
            LocalDate eventReminderDate = myObj.minusDays(reminderInDays);

            if (Boolean.TRUE
                    .equals(event.getAccountForYear() && currentDate.equals(eventReminderDate) && !sentStatus)) {
                System.out.println("*** Email has been sent: " + event.getEventName());
                sendMailService.sendMimeMail(event);
                sentStatus = true;

            } else {
                Boolean dayAndMonthMatch = (currentDate.getDayOfMonth() == eventReminderDate.getDayOfMonth()
                        && currentDate.getMonthValue() == eventReminderDate.getMonthValue());

                if (Boolean.TRUE.equals(dayAndMonthMatch && !sentStatus)) {
                    System.out.println("*** Email has been sent: " + event.getEventName());
                    sendMailService.sendMimeMail(event);
                    sentStatus = true;
                }
            }
        }
    }

}
