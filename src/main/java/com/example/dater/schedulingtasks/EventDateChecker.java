package com.example.dater.schedulingtasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;
import java.util.List;
import javax.mail.MessagingException;
import com.example.dater.service.SendMailService;

import com.example.dater.model.Event;
import com.example.dater.service.EventService;
import com.example.dater.model.Mail;

@Configuration
public class EventDateChecker {

    Boolean sentStatus = false;
    List<Event> eventList;

    private final EventService eventService;
    private SendMailService sendMailService;

    @Autowired
    public EventDateChecker(EventService eventService, SendMailService sendMailService) {
        this.eventService = eventService;
        this.sendMailService = sendMailService;

    }

    public void getEventData() throws MessagingException {
        eventList = eventService.getStorage();
        LocalDate currentDate = LocalDate.now();
        Mail newMail = getMail();
        for (Event event : eventList) {
            if (Boolean.FALSE.equals(event.getReminder()))
                continue;
            String date = event.getDate().substring(0, 10);
            LocalDate myObj = LocalDate.parse(date);
            Long reminderInDays = Long.parseLong(Integer.toString(event.getReminderDays()));
            LocalDate eventReminderDate = myObj.minusDays(reminderInDays);

            if (currentDate.equals(eventReminderDate) && !sentStatus) {
                System.out.println("Notify about " + event.getEventName());
                newMail.setMessage(event.getEventName());
                sendMailService.sendMail(newMail);
                sentStatus = true;

            }
        }
    }

    public Mail getMail() {
        Mail newMail = new Mail();
        newMail.setRecipient("iBlueman260@gmail.com");
        newMail.setSubject("test subject");
        return newMail;
    }

}
