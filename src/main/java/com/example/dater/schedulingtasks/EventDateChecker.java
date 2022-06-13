package com.example.dater.schedulingtasks;

import com.example.dater.model.Events;
import com.example.dater.repository.EventRepository;
import com.example.dater.service.SendMailService;
import com.example.dater.service.SettingsService;
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
    private final SettingsService settingsService;

    List<Events> eventsList;

    @Autowired
    public EventDateChecker(EventRepository eventRepository, SendMailService sendMailService, SettingsService settingsService) {
        this.eventRepository = eventRepository;
        this.sendMailService = sendMailService;
        this.settingsService = settingsService;
    }

    protected Boolean dateVerification(Events events, Instant currentDate) {
        if (Boolean.FALSE.equals(events.getReminder())) {
            return false;
        }
        Instant reminderDate = events.getDateNextReminder().truncatedTo(ChronoUnit.DAYS);

        if (Boolean.TRUE.equals(events.getAccountForYear()) && currentDate.equals(reminderDate)) {
            return true;
        }

        else if (Boolean.FALSE.equals(events.getAccountForYear())){
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
        eventsList = eventRepository.findAll();
        List<Events> eventsToSendOut = new ArrayList<>();
        Instant currentDate = Instant.now().truncatedTo(ChronoUnit.DAYS);
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd.MMM.yy");

        for (Events events : eventsList) {
            if (Boolean.TRUE.equals(dateVerification(events, currentDate))) {
                LocalDate eventDate = events.getDate().atZone(ZoneId.systemDefault()).toLocalDate();
                events.setMailDisplayDate(myFormatter.format(eventDate));
                eventsToSendOut.add(events);

            }
        }

        if (eventsToSendOut.isEmpty()) {
            log.info("No Events to send out");
            return;
        }
        log.info("Events to be sent:");
        for (Events events : eventsToSendOut) log.info(events.getName());
        sendMailService.sendMimeMailList(eventsToSendOut, initiatedBy);
        settingsService.send(eventsToSendOut.size(), initiatedBy);
    }
}
