package com.example.dater.repository;

import com.example.dater.model.Log;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface LogRepository extends MongoRepository<Log, String> {
    Optional<Log> findById(String id);
}
