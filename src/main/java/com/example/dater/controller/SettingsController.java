package com.example.dater.controller;

import com.example.dater.model.Settings;
import com.example.dater.model.SettingsDTO;
import com.example.dater.service.SettingsService;
import com.twilio.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping(path = "/sms")
    public void smsSubmit() {
        try{
            settingService.send();
        }
        catch(ApiException e){
            throw e;
        }
    }
}
