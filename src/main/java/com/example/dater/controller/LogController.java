package com.example.dater.controller;

import com.example.dater.model.Logs;
import com.example.dater.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/log")
public class LogController {
    private final LogService logService;

    @GetMapping
    public List<Logs> findAll() {
        return logService.findAll();
    }
}
