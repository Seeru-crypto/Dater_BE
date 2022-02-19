package com.example.dater.service;

import com.example.dater.model.Settings;
import com.example.dater.repository.SettingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SettingsService {

    private final SettingRepository settingsRepositoy;
    private static final Logger log = LoggerFactory.getLogger(SendMailServiceImpl.class);

    @Autowired
    public SettingsService(SettingRepository settingsRepositoy) {
        this.settingsRepositoy = settingsRepositoy;
    }

    public List<Settings> getSettings() {
        return settingsRepositoy.findAll();
    }

    @Transactional
    public Optional<Settings> update(Settings settings, String settingId, Integer pin) {
        Settings existingSetting = settingsRepositoy.findById(settingId)
                        .orElseThrow(() -> new IllegalStateException("event with ID " + settingId + "does not exist"));
        if  (pin != 154878 ) {
            log.warn("incorrect pin");
            return Optional.empty();
        }
        existingSetting.setSettings(settings);
        return Optional.of(settingsRepositoy.save(existingSetting));
    }
}
