package com.example.dater.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;

import com.example.dater.model.Event;
import java.util.List;
import java.time.LocalDate;

@Service
public class SendMailServiceImpl implements SendMailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public SendMailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;

    }

    @Override
    public void sendMimeMail(Event event) throws MessagingException {

        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        Context context = new Context();
        context.setVariable("event", event);
        String process = templateEngine.process("welcome", context);

        helper.setText(process, true);
        helper.setTo("iBlueman260@gmail.com");
        helper.setSubject("Tulevad sündmused!");

        javaMailSender.send(mimeMessage);
    }

    public void sendMimeMailList(List<Event> eventList) throws MessagingException {

        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        Context context = new Context();
        context.setVariable("eventList", eventList);
        String process = templateEngine.process("eventListTemplate", context);

        helper.setText(process, true);
        helper.setTo("iBlueman260@gmail.com");
        LocalDate currentDate = LocalDate.now();

        String subject = ("Event report: " + currentDate);

        helper.setSubject(subject);

        javaMailSender.send(mimeMessage);
    }
}