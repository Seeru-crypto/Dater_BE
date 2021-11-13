package com.example.Dater_BE.schedulingtasks;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class dailyCheck {
    private EventDateChecker eventDateChecker;

    @Autowired
    public dailyCheck(EventDateChecker eventDateChecker) {
        this.eventDateChecker = eventDateChecker;
    }

    private static final Logger log = LoggerFactory.getLogger(dailyCheck.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 10000)
    public void reportCurrentTime() throws AddressException, MessagingException, IOException {
        log.info(dateFormat.format(new Date()), " : Checking event dates");
        eventDateChecker.getEventData();
    }
}
