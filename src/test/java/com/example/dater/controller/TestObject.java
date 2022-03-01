package com.example.dater.controller;

import com.example.dater.model.Event;

public class TestObject {

    public static Event createEvent() {
        return new Event().setName("Hello world!")
                .setDate("2022-02-19T13:26:13.836Z")
                .setAccountForYear(false)
                .setReminder(false)
                .setReminderDays(1);
    }
}
