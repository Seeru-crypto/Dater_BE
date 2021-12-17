package com.example.dater.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.example.dater.service.SettingsService;
import com.example.dater.model.Settings;

import javax.mail.MessagingException;

import com.example.dater.model.Event;
import java.util.List;
import java.time.LocalDate;

@Service
public class SendMailServiceImpl implements SendMailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final SettingsService settingService;

    public SendMailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine,
            SettingsService settingService) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.settingService = settingService;
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
        List<Settings> list = settingService.getSettings();
        String emailAddressTo = "";
        try {
            emailAddressTo = list.get(0).getEmailAddress();
        } catch (Exception e) {
            System.out.println("error has occured " + e);
        }
        // String emailAddressTo = list.get(0).getEmailAddress();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        Context context = new Context();
        context.setVariable("eventList", eventList);
        String process = templateEngine.process("eventListTemplate", context);

        helper.setText(process, true);
        System.out.println("emailAddressTo is  " + emailAddressTo);

        helper.setTo(emailAddressTo);
        LocalDate currentDate = LocalDate.now();

        String subject = ("Event report: " + currentDate);

        helper.setSubject(subject);

        Boolean sendEmailToggle = list.get(0).getSendEmails();

        if (sendEmailToggle)
            javaMailSender.send(mimeMessage);
    }
}