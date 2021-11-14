package com.example.dater.service;

import javax.mail.MessagingException;

import com.example.dater.model.Mail;

public interface SendMailService {
    void sendMail(Mail mail);

    void sendMailWithAttachments(Mail mail) throws MessagingException;
}
