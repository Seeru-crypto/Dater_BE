package com.example.dater.schedulingtasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DailyCheck {
    private final EventDateChecker eventDateChecker;
    public String checkIniatedByScheduler = "Scheduler";

    @Autowired
    public DailyCheck(EventDateChecker eventDateChecker) {
        this.eventDateChecker = eventDateChecker;
    }

    private static final Logger log = LoggerFactory.getLogger(DailyCheck.class);

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 10 * 60000)
    public void reportCurrentTime() throws MessagingException {
        String logValue = ("Event checked at: " + dateFormat.format(new Date())+ "next check in 10 min");
        log.info(logValue);
        eventDateChecker.checkEventDates(checkIniatedByScheduler);
    }
}
