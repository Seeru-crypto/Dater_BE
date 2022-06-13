package com.example.dater.service;

import javax.mail.MessagingException;

import com.example.dater.model.Events;

import java.util.List;

public interface SendMailService {

    void sendMimeMailList(List<Events> eventsList, String iniatedBy) throws MessagingException;

}
