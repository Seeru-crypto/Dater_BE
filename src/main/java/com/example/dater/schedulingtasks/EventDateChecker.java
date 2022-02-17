package com.example.dater.schedulingtasks;

import com.example.dater.model.Event;
import com.example.dater.service.EventService;
import com.example.dater.service.SendMailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Log4j2
public class EventDateChecker {
    private final EventService eventService;
    private SendMailService sendMailService;

    List<Event> eventList;

    @Autowired
    public EventDateChecker(EventService eventService, SendMailService sendMailService) {
        this.eventService = eventService;
        this.sendMailService = sendMailService;
    }

    public void checkEventDates() throws MessagingException {
        eventList = eventService.findAll();
        LocalDate currentDate = LocalDate.now();
        List<Event> eventsToSendOut = new ArrayList<>();
        for (Event event : eventList) {
            if (Boolean.FALSE.equals(event.getReminder())) {
                continue;
            }
            String date = event.getDate().substring(0, 10);
            LocalDate myObj = LocalDate.parse(date);
            long reminderInDays = Long.parseLong(Integer.toString(event.getReminderDays()));
            LocalDate eventReminderDate = myObj.minusDays(reminderInDays);

            Boolean dayAndMonthMatch = (currentDate.getDayOfMonth() == eventReminderDate.getDayOfMonth()
                    && currentDate.getMonthValue() == eventReminderDate.getMonthValue());
            Boolean yearsMatch = (currentDate.getYear() == eventReminderDate.getYear());

            if (Boolean.TRUE.equals(event.getAccountForYear() && yearsMatch && dayAndMonthMatch))
                eventsToSendOut.add(event);
            if (Boolean.TRUE.equals(!(event.getAccountForYear())) && Boolean.TRUE.equals(dayAndMonthMatch))
                eventsToSendOut.add(event);
        }

        log.info("******************");
        log.info("Number of events to send out ", eventsToSendOut.size());
        if (eventsToSendOut.isEmpty())
            return;

        for (int a = 0; a < eventsToSendOut.size(); a++) {
            Event event = eventsToSendOut.get(a);
            event.setDate(event.getDate().substring(0, 10));
            eventsToSendOut.set(a, event);
        }
        sendMailService.sendMimeMailList(eventsToSendOut);
    }

}
