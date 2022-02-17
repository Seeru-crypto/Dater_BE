package com.example.dater;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.dater.model.Event;
import com.example.dater.model.Settings;
import com.example.dater.repository.EventRepository;
import com.example.dater.repository.SettingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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
	public void run(String... args) throws Exception {
		eventRepository.deleteAll();
		settingRepository.deleteAll();

		String date = LocalDateTime.now().toString();

		Event defaultEvent1 = new Event();
		defaultEvent1.setEventName("Send message");
		defaultEvent1.seteventDescription("desc");
		defaultEvent1.setReminder(true);
		defaultEvent1.setReminderDays(0);
		defaultEvent1.setDate("2021-12-01T16:03:50.623");
		defaultEvent1.setAccountForYear(true);

		Event defaultEvent2 = new Event();
		defaultEvent2.setEventName("Maratoni võistlus");
		defaultEvent2.seteventDescription("Qui et quia commodi neque sit maxime. Molestiae quia veritatis aliquid.");
		defaultEvent2.setReminder(false);
		defaultEvent2.setReminderDays(0);
		defaultEvent2.setDate("2021-11-07T15:05:53.600Z");
		defaultEvent2.setAccountForYear(true);

		Event defaultEvent5 = new Event();
		defaultEvent5.setEventName("email send event!");
		defaultEvent5.seteventDescription("This is a eventDescription!");
		defaultEvent5.setReminder(true);
		defaultEvent5.setReminderDays(0);
		defaultEvent5.setDate(date);
		defaultEvent5.setAccountForYear(false);

		Event defaultEvent3 = new Event();
		defaultEvent3.setEventName("Doesnt send email 2");
		defaultEvent3.seteventDescription(" ");
		defaultEvent3.setReminder(true);
		defaultEvent3.setReminderDays(12);
		defaultEvent3.setDate("2021-01-01T21:15:48.976Z");
		defaultEvent3.setAccountForYear(false);

		Event defaultEvent4 = new Event();
		defaultEvent4.setEventName("Buy ISPA stocks");
		defaultEvent4.seteventDescription("This is the second event, which should be sent!");
		defaultEvent4.setReminder(true);
		defaultEvent4.setReminderDays(7);
		defaultEvent4.setDate("2021-11-27T21:15:48.976Z");
		defaultEvent4.setAccountForYear(false);

		eventRepository.save(defaultEvent1);
		eventRepository.save(defaultEvent2);
		eventRepository.save(defaultEvent3);
		eventRepository.save(defaultEvent4);
		eventRepository.save(defaultEvent5);

		Settings setting = new Settings();
		setting.setCheckInterval(150);
		setting.setEmailAddress("email@gmail.com");
		setting.setSendEmails(false);
		setting.setSendSMS(false);
		settingRepository.save(setting);

		 log.info("-------------------");
		 log.info("The following events were created.");
	}
}
