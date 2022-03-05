package com.example.dater.service;

import com.example.dater.model.Log;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static controller.TestObjects.createLog;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LogServiceTest {
    private final HelperFunctions testHelperFunction = new HelperFunctions();
    String shortEmailValue = "pe@gmail.com";
    String longEmailValue = "person@gmail.com";

    String longExpectedValue = "per...@gmail.com";
    String shortExpectedValue = "...@gmail.com";

    @Test
    void shouldObfuscateEmailAdress() {
        assertEquals(testHelperFunction.obfuscateEmail(shortEmailValue), shortExpectedValue);
        assertEquals(testHelperFunction.obfuscateEmail(longEmailValue), longExpectedValue);
    }

    @Test
    void shouldObfuscateEmails() {
        Log newLog = createLog().setSentToAddress(longEmailValue);
        Log newLog2 = createLog().setSentToAddress(shortEmailValue);
        List<Log> logList = new ArrayList<>();
        logList.add(newLog);
        logList.add(newLog2);
        List <Log> formattedLogs =  testHelperFunction.obfuscateLogs(logList);
        assertEquals(formattedLogs.get(0).getSentToAddress(), longExpectedValue);
        assertEquals(formattedLogs.get(1).getSentToAddress(), shortExpectedValue);
    }
}