package com.example.dater.model;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.Size;

public class Log {
    @Id
    private String id;
    @Size(min = 20, max = 26, message = "date value is incorrect")
    private String date;
    @Size(min = 1, max = 35, message = "Name is mandatory")
    private String sentToAddress;
    @Size(min = 1, max = 20, message = "Initiated by is mandatory")
    private String initiatedBy;
    @Size(min = 1, max = 500, message = "mailContent is mandatory")
    private String mailContent;

    public void setDate(String date) {
        this.date = date;
    }

    public void setSentToAddress(String sentToAddress) {
        this.sentToAddress = sentToAddress;
    }

    public void setInitiatedBy(String initiatedBy) {
        this.initiatedBy = initiatedBy;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }


    public String getDate() {
        return date;
    }

    public String getSentToAddress() {
        return sentToAddress;
    }

    public String getInitiatedBy() {
        return initiatedBy;
    }

    public String getMailContent() {
        return mailContent;
    }

    @Override
    public String toString() {
        return String.format(
                "Event[id='%s', sentToAddress='%s', date='%s', initiatedBy='%s', mailContent='%s']",
                id, sentToAddress, date, initiatedBy, mailContent);
    }

}
