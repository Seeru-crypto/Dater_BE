package com.example.dater;

import com.example.dater.model.Settings;
import com.example.dater.repository.SettingRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
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
public class DaterApplication implements CommandLineRunner {

	@Value("${FRONT_URL}")
	private String devLink;

	@Autowired
	private SettingRepository settingRepository;

	public static void main(String[] args) {
		SpringApplication.run(DaterApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins(devLink).allowedMethods("*");
			}
		};
	}

	@Override
	public void run(String... args) {
		long existingRepoLength = settingRepository.count();
		if (existingRepoLength == 0){
			Settings setting = new Settings();
			setting.setEmailAddress("email@gmail.com");
			setting.setIsEmailActive(false);
			settingRepository.save(setting);
			log.info("Settings repo created");
		}
	}
}
