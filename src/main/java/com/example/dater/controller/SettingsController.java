package com.example.dater.controller;

import java.util.List;

import com.example.dater.model.SettingsDTO;
import com.example.dater.service.SettingsService;
import com.example.dater.model.Settings;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/settings")
public class SettingsController {

    private final SettingsService settingService;

    @GetMapping()
    public List<Settings> getItems() {
        return settingService.getSettings();
    }

    @PutMapping(path = "{settingId}")
    public Settings put(@PathVariable("settingId") String settingId,@Valid @RequestBody SettingsDTO settings, @RequestParam Integer pin ){
        if  (pin != 154878 ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pin incorrect");
        }
        return settingService.update(settings, settingId);
    }
}
