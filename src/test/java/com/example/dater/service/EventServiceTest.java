package com.example.dater.service;

import com.example.dater.model.Event;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static controller.TestObjects.createEventWithoutCreatedDate;
import static org.junit.jupiter.api.Assertions.*;

class EventServiceTest {
    private final HelperFunctions testHelperFunction = new HelperFunctions();

    @Test
    void shouldReturnNextReminderDate() {
        Event event = createEventWithoutCreatedDate().setDate(Instant.parse("2022-03-13T02:00:00.000Z")).setReminderDays(7).setReminder(true);
        Instant expectedResult = Instant.parse("2022-03-06T02:00:00Z");
        Instant reminderDate = testHelperFunction.returnNextReminderDate(event);
        assertEquals(reminderDate, expectedResult);
    }

    @Test
    void shouldNotReturnNextReminderDate() {
        Event event = createEventWithoutCreatedDate().setDate(Instant.parse("2022-03-13T02:00:00.000Z")).setReminderDays(7).setReminder(false);
        Instant reminderDate = testHelperFunction.returnNextReminderDate(event);
        assertNull(reminderDate);
    }
}