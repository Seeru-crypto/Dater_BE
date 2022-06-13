package com.example.dater.service;

import com.example.dater.model.Events;
import com.example.dater.model.Logs;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.dater.service.SendMailServiceImpl.MESSAGE_TYPE_MAIL;

@Configuration
public class HelperFunctions {
    public static final String ERROR_PHONE_REGEX = "\\+[0-9]{1,3}[0-9]{7,10}.";

    public List<Logs> obfuscateLogs(List<Logs> existingLogs) {
        for (Logs logs : existingLogs){
            if (logs.getErrorDesc() != null){
                String formattedError = obfuscateError(logs.getErrorDesc());
                logs.setErrorDesc(formattedError);
            }
            if (Objects.equals(logs.getMessageType(), MESSAGE_TYPE_MAIL)) {
                String formattedMail = obfuscateEmail(logs.getSentToAddress());
                logs.setSentToAddress(formattedMail);
            }
            else{
                String formattedPhoneNumber = obfuscatePhoneNumber(logs.getSentToAddress());
                logs.setSentToAddress(formattedPhoneNumber);
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

    public Instant returnNextReminderDate(Events events) {
        if (Boolean.FALSE.equals(events.getReminder())) return null;

        if (Boolean.FALSE.equals(events.getAccountForYear())) {
            return events.getDate().minus(events.getReminderDays(), ChronoUnit.DAYS);
        }
        else{
            Instant correctMonth = events.getDate().minus(events.getReminderDays(), ChronoUnit.DAYS);
            LocalDate currentDate = LocalDate.now();
            LocalDate date = correctMonth.atZone(ZoneOffset.UTC).toLocalDate();

            return correctMonth.atZone(ZoneOffset.UTC)
                    .withDayOfMonth(date.getDayOfMonth())
                    .withMonth(date.getMonthValue())
                    .withYear(currentDate.getYear())
                    .toInstant();
        }
    }

    public String obfuscateError(String errorMsg) {
        Pattern pattern = Pattern.compile(ERROR_PHONE_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(errorMsg);
        String phoneNr = "";
        int startIndex = 0;
        while (matcher.find()){
            startIndex = matcher.start();
        }
        if (startIndex != 0){
            phoneNr = errorMsg.substring(startIndex, startIndex+7)+"...";
            return errorMsg.substring(0, startIndex) + phoneNr;
        }
        return errorMsg;
    }
}
