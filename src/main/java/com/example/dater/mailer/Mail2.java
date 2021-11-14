package com.example.dater.mailer;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.example.dater.model.Event;

public class Mail2 {
    private static final String PORT = "587";
    private static final String HOST = "smtp.gmail.com";
    private static final String USERNAME = "my.gmail@gmail.com";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "0ee688744d-d9c16d@inbox.mailtrap.io";
    private static final String MYMAIL = "puupuha@gmail.com";

    private static final boolean AUTH = true;
    private static final boolean STARTTLS = true;

    public void send(Event event) throws MessagingException {
        Message msg = new MimeMessage(setSession(setProperties()));

        msg.setSentDate(new Date());
        msg.setSubject("You're subscribed on newsletter");

        msg.setFrom(new InternetAddress(EMAIL, false));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(MYMAIL));
        String messageBody = ("This event has been triggered! " + event.getEventName());
        msg.setContent(messageBody, "text/html");

        Transport.send(msg);
    }

    private Session setSession(Properties props) {
        return Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });
    }

    private Properties setProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.auth", AUTH);
        props.put("mail.smtp.starttls.enable", STARTTLS);

        return props;
    }
}
