package com.example.dater.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class MongoValidator {

        @Bean
        public ValidatingMongoEventListener validatingMongoEventListener(LocalValidatorFactoryBean factory) {
            return new ValidatingMongoEventListener(factory);
        }
}
