package com.example.dater.schedulingtasks;

import com.example.dater.model.Event;
import com.example.dater.repository.EventRepository;
import com.example.dater.service.HelperFunctions;
import com.example.dater.service.SendMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.mail.MessagingException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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

    public void checkEventDates(String initiatedBy) throws MessagingException {
        eventList = eventRepository.findAll();
        List<Event> eventsToSendOut = new ArrayList<>();
        for (Event event : eventList) {
            if (Boolean.FALSE.equals(event.getReminder())) {
                continue;
            }

            Instant currentDate = Instant.now().truncatedTo(ChronoUnit.DAYS);
            Instant reminderDate = event.getDateNextReminder().truncatedTo(ChronoUnit.DAYS);

            if (currentDate.equals(reminderDate)){
                eventsToSendOut.add(event);
            };

            // ToDo fix account for year logic
        }

        log.info("******************");
        if (eventsToSendOut.isEmpty()) {
            log.info("No events to send out");
            return;
        }

        for (int a = 0; a < eventsToSendOut.size(); a++) {
            Event event = eventsToSendOut.get(a);
            LocalDate eventDate =  event.getDate().atZone(ZoneId.systemDefault()).toLocalDate();
            DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd.MMM.yy");
            event.setMailDisplayDate(myFormatter.format(eventDate));
            eventsToSendOut.set(a, event);
        }
        sendMailService.sendMimeMailList(eventsToSendOut, initiatedBy);
    }

}
