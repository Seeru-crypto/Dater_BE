package com.example.dater.service;

import com.example.dater.model.Settings;
import com.example.dater.repository.SettingRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SettingsService {

    private final SettingRepository settingsRepositoy;
    private static final Logger log = LoggerFactory.getLogger(SendMailServiceImpl.class);

    public List<Settings> getSettings() {
        return settingsRepositoy.findAll();
    }

    public Settings update(Settings settings, String settingId) {
        Settings existingSetting = settingsRepositoy.findById(settingId)
                        .orElseThrow(() -> new IllegalStateException("Setting with ID " + settingId + "does not exist"));
        existingSetting.setSettings(settings);
        return settingsRepositoy.save(existingSetting);
    }
}
