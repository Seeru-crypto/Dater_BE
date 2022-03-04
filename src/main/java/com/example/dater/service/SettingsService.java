package com.example.dater.service;

import com.example.dater.model.Settings;
import com.example.dater.repository.SettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SettingsService {
    private final SettingRepository settingsRepository;
    private final HelperFunctions helperFunctions;

    public List<Settings> getSettings() {
        List <Settings> settingList = settingsRepository.findAll();
        Settings existingSetting = settingList.get(0);
        String formattedEmail = helperFunctions.obfuscateEmail(existingSetting.getEmailAddress());
        existingSetting.setEmailAddress(formattedEmail);
        settingList.set(0, existingSetting);
        return settingList;
    }

    public Settings update(Settings settingDto, String settingId) {
        Settings existingSetting = settingsRepository.findById(settingId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Setting with ID " + settingId + "does not exist"));
        existingSetting
                .setIsEmailActive(settingDto.getIsEmailActive())
                .setEmailAddress(settingDto.getEmailAddress())
                .setDateUpdated(Instant.now());
        return settingsRepository.save(existingSetting);
    }
}
