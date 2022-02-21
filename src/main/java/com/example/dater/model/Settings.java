package com.example.dater.model;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Settings {

    @Id
    private String id;
    @NotNull
    private Boolean sendEmails;
    @Size(max = 35, message = "Email is too long")
    private String emailAddress;
    private int checkInterval;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getSendEmails() {
        return sendEmails;
    }

    public void setSendEmails(Boolean sendEmails) {
        this.sendEmails = sendEmails;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getCheckInterval() {
        return checkInterval;
    }

    public void setCheckInterval(int checkInterval) {
        this.checkInterval = checkInterval;
    }

    public void setSettings(Settings settings) {
        this.sendEmails = settings.sendEmails;
        this.emailAddress = settings.emailAddress;
        this.checkInterval = settings.checkInterval;
    }

}
