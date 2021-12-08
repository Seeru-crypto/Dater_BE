package com.example.dater.model;

import org.springframework.data.annotation.Id;

public class Settings {

    @Id
    private String id;
    private Boolean sendEmails;
    private Boolean sendSMS;
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

    public Boolean getSendSMS() {
        return sendSMS;
    }

    public void setSendSMS(Boolean sendSMS) {
        this.sendSMS = sendSMS;
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

}
