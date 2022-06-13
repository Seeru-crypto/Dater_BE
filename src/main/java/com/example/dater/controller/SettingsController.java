package com.example.dater.controller;

import com.example.dater.model.Settings;
import com.example.dater.model.SettingsDTO;
import com.example.dater.service.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/setting")
public class SettingsController {

    @Value("${SETTING_PIN}")
    private Integer settingPin;

    private final SettingsService settingService;

    @GetMapping()
    public List<Settings> getItems() {
        return settingService.getSettings();
    }

    @PutMapping(path = "{settingId}")
    public Settings put(@PathVariable("settingId") String settingId,@Valid @RequestBody SettingsDTO settings, @RequestParam Integer pin ){
        if  (!Objects.equals(pin, settingPin)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pin incorrect");
        }
        return settingService.update(settings, settingId);
    }

}
