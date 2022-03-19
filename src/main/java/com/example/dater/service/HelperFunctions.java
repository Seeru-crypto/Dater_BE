package com.example.dater.service;

import com.example.dater.model.Event;
import com.example.dater.model.Log;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Configuration
public class HelperFunctions {

    public List<Log> obfuscateLogs(List<Log> existingLogs) {
        for (Log log : existingLogs){
            if (Objects.equals(log.getMessageType(), "mail")) {
                String formattedMail = obfuscateEmail(log.getSentToAddress());
                log.setSentToAddress(formattedMail);
            }
            else{
                String formattedPhoneNumber = obfuscatePhoneNumber(log.getSentToAddress());
                log.setSentToAddress(formattedPhoneNumber);
            }
        }
        return existingLogs;
    }

    public String obfuscateEmail(String originalMail) {
        int atIndex = originalMail.indexOf("@");
        String mainPart = originalMail.substring(0, atIndex);
        String lastPart = originalMail.substring(atIndex);

        String formattedMain;
        if (mainPart.length() <= 3) formattedMain = "...";
        else formattedMain = mainPart.substring(0, 3) + "...";
        return formattedMain+lastPart;
    }

    public String obfuscatePhoneNumber(String number){
        return number.substring(0, 8) + "...";
    }

    public Instant returnNextReminderDate(Event event) {
        if (Boolean.FALSE.equals(event.getReminder())) return null;

        if (Boolean.FALSE.equals(event.getAccountForYear())) {
            return event.getDate().minus(event.getReminderDays(), ChronoUnit.DAYS);
        }
        else{
            Instant correctMonth = event.getDate().minus(event.getReminderDays(), ChronoUnit.DAYS);
            LocalDate currentDate = LocalDate.now();
            LocalDate date = correctMonth.atZone(ZoneOffset.UTC).toLocalDate();

            return correctMonth.atZone(ZoneOffset.UTC)
                    .withDayOfMonth(date.getDayOfMonth())
                    .withMonth(date.getMonthValue())
                    .withYear(currentDate.getYear())
                    .toInstant();
        }
    }
}
