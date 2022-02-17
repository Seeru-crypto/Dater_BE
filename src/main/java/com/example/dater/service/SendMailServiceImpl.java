package com.example.dater.service;

import com.example.dater.model.Event;
import com.example.dater.model.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.List;



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
    private static final Logger log = LoggerFactory.getLogger(SendMailServiceImpl.class);

    public void sendMimeMailList(List<Event> eventList) throws MessagingException {

        String emailAddressTo = "";
        Boolean emailToggle = false;
        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        List<Settings> list = settingService.getSettings();

        try {
            emailAddressTo = list.get(0).getEmailAddress();
            emailToggle = list.get(0).getSendEmails();
        } catch (Exception e) {
            log.info("error has occured " + e);
        }
        if (!emailToggle) {
            log.info("Emails are not enabled");
            return;
        }
        // POST TO LOGS DATE, CONTENT as LIST, what caused the email, email address
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        Context context = new Context();
        context.setVariable("eventList", eventList);

        String process = templateEngine.process("eventListTemplate", context);

        helper.setText(process, true);
        log.info("Email address set to ", emailAddressTo);
        helper.setTo(emailAddressTo);
        LocalDate currentDate = LocalDate.now();
        String subject = ("Event report: " + currentDate);
        helper.setSubject(subject);
        javaMailSender.send(mimeMessage);
    }
}