package com.example.dater;

import com.example.dater.model.Event;
import com.example.dater.model.Settings;
import com.example.dater.repository.EventRepository;
import com.example.dater.repository.SettingRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableScheduling
@EnableSwagger2
@Log4j2
public class DaterApplication implements CommandLineRunner {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private SettingRepository settingRepository;

	public static void main(String[] args) {
		SpringApplication.run(DaterApplication.class, args);
	}

	@Override
	public void run(String... args) {
		eventRepository.deleteAll();
		settingRepository.deleteAll();

		String date = LocalDateTime.now().toString();

		Event defaultEvent1 = new Event();
		defaultEvent1.setName("Buy Ispa stocks");
		defaultEvent1.setDescription("divident Ex-Date, buy 20 shares at 17.56 PS");
		defaultEvent1.setReminder(true);
		defaultEvent1.setReminderDays(0);
		defaultEvent1.setDate(date);
		defaultEvent1.setAccountForYear(true);

		Event defaultEvent2 = new Event();
		defaultEvent2.setName("Maratoni võistlus");
		defaultEvent2.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean molestie tincidunt lorem, et tincidunt dui iaculis dui.");
		defaultEvent2.setReminder(true);
		defaultEvent2.setReminderDays(0);
		defaultEvent2.setDate(date);
		defaultEvent2.setAccountForYear(true);

		Event defaultEvent3 = new Event();
		defaultEvent3.setName("Doesnt send email 2");
		defaultEvent3.setDescription(" ");
		defaultEvent3.setReminder(true);
		defaultEvent3.setReminderDays(12);
		defaultEvent3.setDate("2021-01-01T21:15:48.976Z");
		defaultEvent3.setAccountForYear(false);

		Event defaultEvent4 = new Event();
		defaultEvent4.setName("Doesnt send email");
		defaultEvent4.setDescription(" ");
		defaultEvent4.setReminder(false);
		defaultEvent4.setReminderDays(0);
		defaultEvent4.setDate(date);
		defaultEvent4.setAccountForYear(false);

		eventRepository.save(defaultEvent1);
		eventRepository.save(defaultEvent2);
		eventRepository.save(defaultEvent3);
		eventRepository.save(defaultEvent4);

		Settings setting = new Settings();
		setting.setCheckInterval(150);
		setting.setEmailAddress("email@gmail.com");
		setting.setSendEmails(false);
		settingRepository.save(setting);

		log.info("-------------------");
		 log.info("The following events were created.");
	}
}
