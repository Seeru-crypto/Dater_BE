package com.example.Dater_BE.mailer;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.example.Dater_BE.model.Event;

public class Mail {
    private final String PORT = "587";
    private final String HOST = "smtp.mailtrap.io";
    private final String USERNAME = "93fb9fb132fbdd";
    private final String PASSWORD = "21d60ee5dffeea";
    private final String EMAIL = "0ee688744d-d9c16d@inbox.mailtrap.io";
    private final String myMail = "puupuha@gmail.com";

    private final boolean AUTH = true;
    private final boolean STARTTLS = true;

    public void send(Event event) throws AddressException, MessagingException, IOException {
        Message msg = new MimeMessage(setSession(setProperties()));

        msg.setSentDate(new Date());
        msg.setSubject("You're subscribed on newsletter");

        msg.setFrom(new InternetAddress(EMAIL, false));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(myMail));
        String messageBody = ("This event has been triggered! " + event.getEventName());
        msg.setContent(messageBody, "text/html");

        Transport.send(msg);
    }

    private Session setSession(Properties props) {
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });
        return session;
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
