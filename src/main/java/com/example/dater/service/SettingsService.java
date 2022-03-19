package com.example.dater.service;

import com.example.dater.model.Log;
import com.example.dater.model.Settings;
import com.example.dater.model.SettingsDTO;
import com.example.dater.repository.SettingRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.dater.schedulingtasks.DailyCheck.SCHEDULER_VALUE_MINUTES;

@RequiredArgsConstructor
@Service
public class SettingsService {
    public static final String MESSAGE_TYPE_SMS = "sms";
    private static final Logger log = LoggerFactory.getLogger(SendMailServiceImpl.class);
    private final SettingRepository settingsRepository;
    private final LogService logService;
    private final HelperFunctions helperFunctions;

    @Value("${TWILIO_ACCOUNT_SID}")
    private String ACCOUNT_SID;

    @Value("${TWILIO_AUTH_TOKEN}")
    private String AUTH_TOKEN;

    @Value("${TWILIO_PHONE_NUMBER}")
    private String FROM_NUMBER;

    public List<Settings> getSettings() {
        List <Settings> settingList = settingsRepository.findAll();
        Settings existingSetting = settingList.get(0);
        String formattedEmail = helperFunctions.obfuscateEmail(existingSetting.getEmailAddress());
        existingSetting.setEmailAddress(formattedEmail);
        settingList.set(0, existingSetting);
        return settingList;
    }

    public Settings update(SettingsDTO settingDto, String settingId) {
        Settings existingSetting = settingsRepository.findById(settingId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Setting with ID " + settingId + "does not exist"));

        String emailAdressToSave;
        if (settingDto.getEmailAddress() == null || Objects.equals(settingDto.getEmailAddress(), "")) emailAdressToSave = existingSetting.getEmailAddress();
        else emailAdressToSave = settingDto.getEmailAddress();

        String smsTo;
        if (settingDto.getSmsTo() == null || Objects.equals(settingDto.getSmsTo(), "")) smsTo = existingSetting.getSmsTo();
        else smsTo = settingDto.getSmsTo();

        Boolean isSmsActive = Optional.ofNullable(settingDto.getIsSmsActive()).orElse(existingSetting.getIsSmsActive());

        existingSetting
                .setIsEmailActive(settingDto.getIsEmailActive())
                .setEmailAddress(emailAdressToSave)
                .setDateUpdated(Instant.now())
                .setSmsTo(smsTo)
                .setIsSmsActive(isSmsActive);
        return settingsRepository.save(existingSetting);
    }

    public String getFullEmail(String settingId) {
        Settings existingSetting = settingsRepository.findById(settingId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Setting with ID " + settingId + "does not exist"));
        return existingSetting.getEmailAddress();
    }

    public void send(int nrOfEvents, String initiatedBy) {
        Log newLog = new Log();
        List <Settings> settingList = settingsRepository.findAll();
        Settings settings = settingList.get(0);

        if (Boolean.FALSE.equals(settings.getIsSmsActive())) return;

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String messageBody = "Dater has found " + nrOfEvents+ " upcoming events";
        String smsFrom = FROM_NUMBER;
        String smsTo = settings.getSmsTo();

        Message.creator(new PhoneNumber(smsTo), new PhoneNumber(smsFrom), messageBody)
                .create();
        newLog.setInitiatedBy(initiatedBy)
                .setMessageContent(messageBody)
                .setSentToAddress(smsTo)
                .setSchedulerValue(SCHEDULER_VALUE_MINUTES)
                .setMessageType(MESSAGE_TYPE_SMS);
        logService.save(newLog);
        log.info("Sms has been sent out");
    }
}
