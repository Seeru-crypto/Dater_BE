package com.example.dater.service;

import com.example.dater.model.Settings;
import com.example.dater.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SettingsService {

    private final SettingRepository settingsRepositoy;

    @Autowired
    public SettingsService(SettingRepository settingsRepositoy) {
        this.settingsRepositoy = settingsRepositoy;
    }

    public List<Settings> getSettings() {
        return settingsRepositoy.findAll();
    }

    @Transactional
    public Settings update(Settings settings, String settingId) {
        Settings existingSetting = settingsRepositoy.findById(settingId)
                        .orElseThrow(() -> new IllegalStateException("event with ID " + settingId + "does not exist"));

        existingSetting.setSettings(settings);
        return settingsRepositoy.save(existingSetting);
    }
}
