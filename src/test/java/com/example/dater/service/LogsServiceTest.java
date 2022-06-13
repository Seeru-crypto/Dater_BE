package com.example.dater.service;

import com.example.dater.model.Logs;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.example.dater.service.SettingsService.MESSAGE_TYPE_SMS;
import static controller.TestObjects.createLog;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LogsServiceTest {
    private final HelperFunctions testHelperFunction = new HelperFunctions();
    String longEmailValue = "person@gmail.com";
    String longExpectedValue = "per...@gmail.com";

    String shortEmailValue = "pe@gmail.com";
    String shortExpectedValue = "...@gmail.com";

    String phoneNumber = "+372 1234567";
    String expectedPhoneNumber = "+372 123...";

    @Test
    void shouldObfuscateEmailAdress() {
        assertEquals(testHelperFunction.obfuscateEmail(shortEmailValue), shortExpectedValue);
        assertEquals(testHelperFunction.obfuscateEmail(longEmailValue), longExpectedValue);
    }

    @Test
    void shouldObfuscatePhoneNr(){
        assertEquals(testHelperFunction.obfuscatePhoneNumber(phoneNumber), expectedPhoneNumber);
    }

    @Test
    void shouldObfuscateDifferentLogs() {
        Logs newLogs = createLog().setSentToAddress(longEmailValue);
        Logs newLogs2 = createLog().setSentToAddress(phoneNumber).setMessageType(MESSAGE_TYPE_SMS);
        List<Logs> logsList = new ArrayList<>();
        logsList.add(newLogs);
        logsList.add(newLogs2);
        List <Logs> formattedLogs =  testHelperFunction.obfuscateLogs(logsList);
        assertEquals(formattedLogs.get(0).getSentToAddress(), longExpectedValue);
        assertEquals(formattedLogs.get(1).getSentToAddress(), expectedPhoneNumber);
    }

    @Test
    void shouldObfuscateError() {
        String errorMsg = "com.twilio.exception.ApiException: Permission to send an SMS has not been enabled for the region indicated by the 'To' number: +111234567890.";
        String result = testHelperFunction.obfuscateError(errorMsg);
        String expectedResult = "com.twilio.exception.ApiException: Permission to send an SMS has not been enabled for the region indicated by the 'To' number: +111234...";
        assertEquals(expectedResult, result);
    }
}