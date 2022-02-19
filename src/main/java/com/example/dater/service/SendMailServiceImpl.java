package com.example.dater.service;

import com.example.dater.model.Event;
import com.example.dater.model.Log;
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
import java.time.LocalDateTime;
import java.util.List;



@Service
public class SendMailServiceImpl implements SendMailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final SettingsService settingService;
    private final LogService logService;

    public SendMailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine,
            SettingsService settingService, LogService logService) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.settingService = settingService;
        this.logService = logService;
    }
    private static final Logger log = LoggerFactory.getLogger(SendMailServiceImpl.class);

    public void sendMimeMailList(List<Event> eventList) throws MessagingException {
        Log newLog = new Log();
        String emailAddressTo = "";
        Boolean emailToggle = false;
        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        List<Settings> list = settingService.getSettings();

        try {
            emailAddressTo = list.get(0).getEmailAddress();
            emailToggle = list.get(0).getSendEmails();
        } catch (Exception e) {
            log.warn("error has occured " + e);
        }
        if (!emailToggle) {
            log.info("Emails are not enabled");
            return;
        }
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        Context context = new Context();
        context.setVariable("eventList", eventList);

        String process = templateEngine.process("eventListTemplate", context);

        helper.setText(process, true);

        helper.setTo(emailAddressTo);
        LocalDate currentDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        String subject = ("Event report: " + currentDate);
        helper.setSubject(subject);

        newLog.setSentToAddress(emailAddressTo);
        newLog.setDate(localDateTime.toString());
        newLog.setInitiatedBy("wip");
        newLog.setMailContent(eventList.toString());
        logService.save(newLog);
        javaMailSender.send(mimeMessage);
        log.info("Email sent!");

    }
}