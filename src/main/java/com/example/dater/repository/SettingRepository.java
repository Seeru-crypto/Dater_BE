package com.example.dater.repository;

import com.example.dater.model.Settings;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "settings", path = "settings")

public interface SettingRepository extends MongoRepository<Settings, String> {

}
