package com.example.dater.repository;

import com.example.dater.model.Logs;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface LogRepository extends MongoRepository<Logs, String> {
    Optional<Logs> findById(String id);
}
