package com.example.dater.schedulingtasks;

import com.example.dater.model.Events;
import com.example.dater.repository.EventRepository;
import com.example.dater.service.HelperFunctions;
import com.example.dater.service.SettingsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

import static controller.TestObjects.createEventWithCreatedDate;
import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class EventDateCheckerTest {

    @Autowired
    private EventRepository eventRepository;
    private SettingsService settingsService;
    private final EventDateChecker eventDateChecker = new EventDateChecker(eventRepository, settingsService);
    private final HelperFunctions helperFunctions = new HelperFunctions();

    @Test
    void dateVerification_shouldReturnFalse_whenReminderIsFalse() {
        Events events = createEventWithCreatedDate().setReminder(false);
        Instant currentDate = Instant.now().truncatedTo(DAYS);
        Boolean result = eventDateChecker.dateVerification(events, currentDate);
        assertFalse(result);
    }

    @Test
    void dateVerification_shouldReturnFalse_whenReminderIsTrueButDatesDontMatch() {
        Instant currentDate = Instant.now().truncatedTo(DAYS);
        Events events = createEventWithCreatedDate()
                .setReminder(true)
                .setReminderDays(0)
                .setDate(currentDate);
        Instant nextReminderDate = helperFunctions.returnNextReminderDate(events);
        events.setDateNextReminder(nextReminderDate);

        Instant futureDate = currentDate.plus(2, DAYS);

        Boolean result = eventDateChecker.dateVerification(events, futureDate);
        assertFalse(result);
    }

    @Test
    void dateVerification_shouldReturnTrue_whenDatesMatchAndAccountForYearIsTrue() {
        Instant currentDate = Instant.now().truncatedTo(DAYS);
        Events events = createEventWithCreatedDate()
                .setReminder(true).
                setReminderDays(0).
                setDate(currentDate).
                setAccountForYear(true);
        Instant nextReminderDate = helperFunctions.returnNextReminderDate(events);
        events.setDateNextReminder(nextReminderDate);

        Boolean result = eventDateChecker.dateVerification(events, currentDate);
        assertTrue(result);
    }

    @Test
    void dateVerification_shouldReturnTrue_whenDaysAndMonthsMatchAndAccountForYearIsFalse() {
        Instant date = Instant.parse("2022-03-19T00:00:00.000Z");
        Instant reminderDate = Instant.parse("1990-03-19T00:00:00.000Z");
        Events events = createEventWithCreatedDate()
                .setReminder(true).
                setReminderDays(0).
                setDate(date).
                setAccountForYear(false)
                .setDateNextReminder(reminderDate);

        Boolean result = eventDateChecker.dateVerification(events, date);
        assertTrue(result);
    }
}