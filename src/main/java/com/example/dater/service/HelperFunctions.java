package com.example.dater.service;

import com.example.dater.model.Log;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class HelperFunctions {

    public List<Log> obfuscateLogs(List<Log> existingLogs) {
        for (Log log : existingLogs){
            String formattedMail = obfuscateEmail(log.getSentToAddress());
            log.setSentToAddress(formattedMail);
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
}
