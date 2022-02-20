package com.example.dater.service;

import javax.mail.MessagingException;

import com.example.dater.model.Event;
import java.util.List;

public interface SendMailService {

    void sendMimeMailList(List<Event> eventList, String iniatedBy) throws MessagingException;

}
