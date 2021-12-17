package com.example.dater.controller;

import java.util.List;

import com.example.dater.service.SettingsService;
import com.example.dater.model.Settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(path = "api/")
public class SettingsController {

    private final SettingsService settingService;

    @Autowired
    public SettingsController(SettingsService settingService) {
        this.settingService = settingService;
    }

    @GetMapping("/settings")
    public List<Settings> getItems() {
        return settingService.getSettings();
    }

}
