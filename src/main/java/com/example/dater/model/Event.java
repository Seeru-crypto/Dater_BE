package com.example.dater.model;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Event {
    @Id
    private String id;
    @NotBlank(message = "Name is mandatory")
    @Size(min = 1, max = 25, message = "Name is mandatory")
    private String eventName;
    private String date;
    @NotNull
    private Boolean reminder;
    @Max(31)
    private Integer reminderDays;
    private String eventDescription;
    private Boolean accountForYear;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getAccountForYear() {
        return accountForYear;
    }

    public void setAccountForYear(Boolean accountForYear) {
        this.accountForYear = accountForYear;
    }

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

    public String geteventDescription() {
        return eventDescription;
    }

    public void seteventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    @Override
    public String toString() {
        return String.format(
                "Event[id='%s', eventName='%s', date='%s', reminder='%s', reminderDays='%s', eventDescription='%s', accountForYear='%s']",
                id, eventName, date, reminder, reminderDays, eventDescription, accountForYear);

    }

}
