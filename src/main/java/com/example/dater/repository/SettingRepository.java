package com.example.dater.repository;

import com.example.dater.model.Settings;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SettingRepository extends MongoRepository<Settings, String> {

    Optional<Settings> findById(String id);
}
