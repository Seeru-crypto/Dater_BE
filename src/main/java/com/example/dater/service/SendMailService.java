package com.example.dater.service;

import javax.mail.MessagingException;

import com.example.dater.model.Event;

public interface SendMailService {
    void sendMail();

    void sendMimeMail(Event event) throws MessagingException;
}
