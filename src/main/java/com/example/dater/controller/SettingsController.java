package com.example.dater.controller;

import java.util.List;
import com.example.dater.service.SettingsService;
import com.example.dater.model.Settings;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public void put(@PathVariable("settingId") String settingId, @RequestBody Settings settings, @RequestParam Integer pin ){
        settingService.update(settings, settingId, pin);
    }

}
