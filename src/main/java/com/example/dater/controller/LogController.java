package com.example.dater.controller;

import com.example.dater.model.Log;
import com.example.dater.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ToDo when deploying remove localhost from CORS list
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/log")
public class LogController {

    private final LogService logService;

    @CrossOrigin(origins = {"http://localhost:3000", "https://date-manager-front.herokuapp.com/"})
    @GetMapping
    public List<Log> findAll() {
        return logService.findAll();
    }

    @CrossOrigin(origins = {"http://localhost:8080", "https://date-manager-back.herokuapp.com/"})
    @PostMapping
    public Log save(@RequestBody Log log) {
        return logService.save(log);
    }
}
