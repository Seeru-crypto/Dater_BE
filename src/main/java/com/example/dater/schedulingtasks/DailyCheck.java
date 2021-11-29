package com.example.dater.schedulingtasks;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyCheck {
    private EventDateChecker eventDateChecker;

    @Autowired
    public DailyCheck(EventDateChecker eventDateChecker) {
        this.eventDateChecker = eventDateChecker;
    }

    private static final Logger log = LoggerFactory.getLogger(DailyCheck.class);

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 10000)
    public void reportCurrentTime() throws MessagingException {
        log.info(dateFormat.format(new Date()), " : Checking event dates");
        eventDateChecker.checkEventDates();
    }
}
