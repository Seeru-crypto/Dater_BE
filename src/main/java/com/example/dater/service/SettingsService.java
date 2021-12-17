package com.example.dater.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.dater.model.Settings;
import com.example.dater.repository.SettingRepository;

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
}
