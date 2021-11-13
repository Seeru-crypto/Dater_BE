package com.example.dater.model;

import org.springframework.data.annotation.Id;

public class Event {
    @Id
    private String id;
    private String eventName;
    private String date;
    private Boolean reminder;
    private Integer reminderDays;
    private String description;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getReminder() {
        return reminder;
    }

    public void setReminder(Boolean reminder) {
        this.reminder = reminder;
    }

    public Integer getReminderDays() {
        return reminderDays;
    }

    public void setReminderDays(Integer reminderDays) {
        this.reminderDays = reminderDays;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format(
                "Event[id=%s, eventName='%s', date='%s', reminder='%s', reminderDays='%s', description='%s']", id,
                eventName, date, reminder, reminderDays, description);
    }

}
