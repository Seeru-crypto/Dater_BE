package com.example.dater.service;

import com.example.dater.model.Logs;
import com.example.dater.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LogService {
    private final LogRepository logRepository;
    private final HelperFunctions helperFunctions;

    public List<Logs> findAll() {
        return helperFunctions.obfuscateLogs(logRepository.findAll());
    }

    public Logs save(Logs logs) {
        logs.setDateCreated(Instant.now());
        return logRepository.save(logs);
    }
}
