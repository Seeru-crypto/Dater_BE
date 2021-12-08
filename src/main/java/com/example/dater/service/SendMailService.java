package com.example.dater.service;

import javax.mail.MessagingException;

import com.example.dater.model.Event;
import java.util.List;

public interface SendMailService {

    void sendMimeMail(Event event) throws MessagingException;

    void sendMimeMailList(List<Event> eventList) throws MessagingException;

}
