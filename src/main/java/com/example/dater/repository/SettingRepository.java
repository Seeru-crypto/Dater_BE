package com.example.dater.repository;

import com.example.dater.model.Settings;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SettingRepository extends MongoRepository<Settings, String> {

}
