package com.example.dater.schedulingtasks;

import com.example.dater.model.Event;
import com.example.dater.repository.EventRepository;
import com.example.dater.service.SendMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.mail.MessagingException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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

    protected Boolean dateVerification(Event event, Instant currentDate){
        if (Boolean.FALSE.equals(event.getReminder())) {
            return false;
        }
        Instant reminderDate = event.getDateNextReminder().truncatedTo(ChronoUnit.DAYS);

        if (Boolean.TRUE.equals(event.getAccountForYear()) && currentDate.equals(reminderDate)){
            return true;
        }
        else if (Boolean.FALSE.equals(event.getAccountForYear())){
            ZoneId z = ZoneId.systemDefault();
            ZonedDateTime currentDateTime = ZonedDateTime.ofInstant(currentDate, z);
            ZonedDateTime reminderDateTime = ZonedDateTime.ofInstant(reminderDate, z);

            Boolean areDaysEqual = (currentDateTime.getDayOfMonth() == reminderDateTime.getDayOfMonth());
            Boolean areMonthsEqual = (currentDateTime.getMonthValue() == reminderDateTime.getMonthValue());

            return areDaysEqual && areMonthsEqual;
        }
        return false;
    }

    public void checkEventDates(String initiatedBy) throws MessagingException {
        eventList = eventRepository.findAll();
        List<Event> eventsToSendOut = new ArrayList<>();
        Instant currentDate = Instant.now().truncatedTo(ChronoUnit.DAYS);
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd.MMM.yy");

        for (Event event : eventList) {
            if (Boolean.TRUE.equals(dateVerification(event, currentDate))) {
                LocalDate eventDate =  event.getDate().atZone(ZoneId.systemDefault()).toLocalDate();
                event.setMailDisplayDate(myFormatter.format(eventDate));
                eventsToSendOut.add(event);
            }
        }

        if (eventsToSendOut.isEmpty()) {
            log.info("No events to send out");
            return;
        }

        log.info("Events to be sent:");
        for (Event event: eventsToSendOut) log.info(event.getName());

        sendMailService.sendMimeMailList(eventsToSendOut, initiatedBy);
    }
}
