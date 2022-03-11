package com.example.dater.service;

import com.example.dater.model.Log;
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

    public List<Log> findAll() {
        return helperFunctions.obfuscateLogs(logRepository.findAll());
    }

    public Log save(Log log) {
        log.setDateCreated(Instant.now());
        return logRepository.save(log);
    }
}
