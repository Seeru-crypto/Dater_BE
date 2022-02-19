package com.example.dater.service;

import com.example.dater.model.Log;
import com.example.dater.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LogService {

    private final LogRepository logRepository;

    public List<Log> findAll() {
        return logRepository.findAll();
    }

    public Log save(Log log) {
        return logRepository.save(log);
    }
}
