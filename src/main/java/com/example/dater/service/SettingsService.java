package com.example.dater.service;

import com.example.dater.model.Logs;
import com.example.dater.model.Settings;
import com.example.dater.model.SettingsDTO;
import com.example.dater.repository.SettingRepository;
import com.twilio.Twilio;
import com.twilio.exception.ApiException;
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
        String formatSmsTo = helperFunctions.obfuscatePhoneNumber(existingSetting.getSmsTo());
        existingSetting
                .setEmailAddress(formattedEmail)
                .setSmsTo(formatSmsTo);
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
        Logs newLogs = new Logs();
        List <Settings> settingList = settingsRepository.findAll();
        Settings settings = settingList.get(0);

        if (Boolean.FALSE.equals(settings.getIsSmsActive())) return;

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String messageBody = " \n Dater has found " + nrOfEvents+ " upcoming events. Click to view: \n https://date-manager-front.herokuapp.com/eventList";
        if (nrOfEvents==1) messageBody = " \n Dater has found " + nrOfEvents+ " upcoming event. Click to view: \n https://date-manager-front.herokuapp.com/eventList";

        String smsFrom = FROM_NUMBER;
        String smsTo = settings.getSmsTo();

        newLogs.setInitiatedBy(initiatedBy)
                .setMessageContent(messageBody)
                .setSentToAddress(smsTo)
                .setSchedulerValue(SCHEDULER_VALUE_MINUTES)
                .setMessageType(MESSAGE_TYPE_SMS);
        try{
            Message.creator(new PhoneNumber(smsTo), new PhoneNumber(smsFrom), messageBody)
                    .create();
            log.info("Sms has been sent out");
        }
        catch(ApiException e){
            newLogs.setErrorDesc(e.toString());
            log.warn("Error has occured in sms send ", e);
        }
        logService.save(newLogs);
    }
}
