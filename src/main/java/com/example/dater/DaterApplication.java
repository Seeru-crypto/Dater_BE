package com.example.dater;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
@EnableSwagger2
@Log4j2
public class DaterApplication{

	@Value("${FRONT_URL}")
	private String devLink;

	public static void main(String[] args) {
		SpringApplication.run(DaterApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/events/**").allowedOrigins(devLink).allowedMethods("GET", "PUT", "POST", "DELETE");
				registry.addMapping("/api/settings/**").allowedOrigins(devLink).allowedMethods("GET", "PUT");
				registry.addMapping("/api/logs/**").allowedOrigins(devLink).allowedMethods("GET");
			}
		};
	}
}
