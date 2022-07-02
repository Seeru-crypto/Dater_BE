package com.example.dater.service;

import com.example.dater.model.Events;
import com.example.dater.model.Logs;
import com.example.dater.model.Settings;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.List;

import static com.example.dater.schedulingtasks.DailyCheck.SCHEDULER_VALUE_MINUTES;

@RequiredArgsConstructor
@Service
public class SendMailServiceImpl implements SendMailService {
    public static final String MESSAGE_TYPE_MAIL = "mail";
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final SettingsService settingService;
    private final LogService logService;

    private static final Logger log = LoggerFactory.getLogger(SendMailServiceImpl.class);

    public void sendMimeMailList(List<Events> eventsList, String iniatedBy) throws MessagingException {
        Logs newLogs = new Logs();
        String emailAddressTo = "ERROR!";
        Boolean emailToggle = false;
        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        List<Settings> list = settingService.getSettings();

        try {
            emailAddressTo = list.get(0).getEmailAddress();
            emailToggle = list.get(0).getIsEmailActive();
            emailAddressTo = settingService.getFullEmail(list.get(0).getId());
        } catch (Exception e) {
            log.warn("error has occurred ", e);
        }
        if (Boolean.FALSE.equals(emailToggle)) {
            log.info("Emails are not enabled");
            return;
        }
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        Context context = new Context();
        context.setVariable("eventList", eventsList);

        String process = templateEngine.process("eventListTemplate", context);

        helper.setText(process, true);

        helper.setTo(emailAddressTo);
        String subject = ("Dater report: " + LocalDate.now());
        helper.setSubject(subject);
        newLogs.setSentToAddress(emailAddressTo)
                .setInitiatedBy(iniatedBy)
                .setMessageContent(eventsList.toString())
                .setSchedulerValue(SCHEDULER_VALUE_MINUTES)
                .setMessageType(MESSAGE_TYPE_MAIL);
        try {
            javaMailSender.send(mimeMessage);
            logService.save(newLogs);
            log.info("Email sent!");
        } catch (MailException e) {
            newLogs.setErrorDesc(e.toString());
            logService.save(newLogs);
            log.warn("error occurred in mailer");
        }
    }
}