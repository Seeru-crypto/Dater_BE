package com.example.dater.service;

import com.example.dater.model.Settings;
import com.example.dater.model.SettingsDTO;
import com.example.dater.repository.SettingRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class SettingsService {
    private final SettingRepository settingsRepository;
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

        existingSetting
                .setIsEmailActive(settingDto.getIsEmailActive())
                .setEmailAddress(emailAdressToSave)
                .setDateUpdated(Instant.now());
        return settingsRepository.save(existingSetting);
    }

    public String getFullEmail(String settingId) {
        Settings existingSetting = settingsRepository.findById(settingId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Setting with ID " + settingId + "does not exist"));
        return existingSetting.getEmailAddress();
    }

    public void send() {
        List <Settings> settingList = settingsRepository.findAll();
        Settings settings = settingList.get(0);

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(new PhoneNumber(settings.getSmsTo()), new PhoneNumber(FROM_NUMBER), settings.getSmsMessage())
                .create();
        System.out.println("here is my id:"+message.getSid());// Unique resource ID created to manage this transaction
    }
}
