package com.example.dater.schedulingtasks;

import com.example.dater.model.Event;
import com.example.dater.repository.EventRepository;
import com.example.dater.service.SendMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@Log4j2
public class EventDateChecker {
    private final EventRepository eventRepository;
    private SendMailService sendMailService;

    List<Event> eventList;

    @Autowired
    public EventDateChecker(EventRepository eventRepository, SendMailService sendMailService) {
        this.eventRepository = eventRepository;
        this.sendMailService = sendMailService;
    }

    public void checkEventDates(String iniatedBy) throws MessagingException {
        eventList = eventRepository.findAll();
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
        if (eventsToSendOut.isEmpty())
            return;

        for (int a = 0; a < eventsToSendOut.size(); a++) {
            Event event = eventsToSendOut.get(a);
            event.setDate(event.getDate().substring(0, 10));
            eventsToSendOut.set(a, event);
        }
        sendMailService.sendMimeMailList(eventsToSendOut, iniatedBy);
    }

}
