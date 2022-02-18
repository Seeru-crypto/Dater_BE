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
    @Size(min = 1, max = 26, message = "Name is mandatory")
    private String name;
    @Size(min = 20, max = 26, message = "date value is incorrect")
    private String date;
    @NotNull
    private Boolean reminder;
    @Max(31)
    private Integer reminderDays;
    @Size(max = 121, message = "desc len is too high")
    private String description;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDescription() {
        return description;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEvent(Event event) {
        this.name = event.name;
        this.date = event.date;
        this.reminder = event.reminder;
        this.reminderDays = event.reminderDays;
        this.description = event.description;
        this.accountForYear = event.accountForYear;
    }

    @Override
    public String toString() {
        return String.format(
                "Event[id='%s', name='%s', date='%s', reminder='%s', reminderDays='%s', description='%s', accountForYear='%s']",
                id, name, date, reminder, reminderDays, description, accountForYear);
    }
}
