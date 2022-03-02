package com.example.dater.controller;

import com.example.dater.model.Event;

import java.time.Instant;

public class TestObject {

    public static Event createEventWithoutCreated() {
        return new Event().setName("Hello world!")
                .setDate("2022-02-19T13:26:13.836Z")
                .setAccountForYear(false)
                .setReminder(false)
                .setReminderDays(1);
    };

    public static Event createEventWithCreatedDate() {
        return new Event().setName("Hello world!")
                .setDate("2022-02-19T13:26:13.836Z")
                .setAccountForYear(false)
                .setReminder(false)
                .setReminderDays(1)
                .setDateCreated(Instant.now());
    }
}
