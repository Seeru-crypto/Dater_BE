package com.example.dater;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.dater.model.Event;
import com.example.dater.repository.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableScheduling
@Log4j2
public class DaterApplication implements CommandLineRunner {

	@Autowired
	private EventRepository eventRepository;

	public static void main(String[] args) {
		SpringApplication.run(DaterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		eventRepository.deleteAll();

		Event defaultEvent1 = new Event();
		defaultEvent1.setEventName("Send event 1");
		defaultEvent1.setDescription("This is a test event, which should be shown!");
		defaultEvent1.setReminder(true);
		defaultEvent1.setReminderDays(0);
		String date = LocalDateTime.now().toString();
		defaultEvent1.setDate(date);
		defaultEvent1.setAccountForYear(true);

		Event defaultEvent2 = new Event();
		defaultEvent2.setEventName("Doesnt send email");
		defaultEvent2.setDescription("Qui et quia commodi neque sit maxime. Molestiae quia veritatis aliquid."
				+ "Aperiam reprehenderit suscipit dolor unde dolorem aspernatur nisi.");
		defaultEvent2.setReminder(false);
		defaultEvent2.setReminderDays(0);
		defaultEvent2.setDate("2021-11-07T15:05:53.600Z");
		defaultEvent2.setAccountForYear(true);

		Event defaultEvent3 = new Event();
		defaultEvent3.setEventName("Doesnt send email 2");
		defaultEvent3.setDescription(" ");
		defaultEvent3.setReminder(true);
		defaultEvent3.setReminderDays(12);
		defaultEvent3.setDate("2021-01-01T21:15:48.976Z");
		defaultEvent3.setAccountForYear(false);

		Event defaultEvent4 = new Event();
		defaultEvent4.setEventName("Send event 2");
		defaultEvent4.setDescription("This is the second event, which should be sent!");
		defaultEvent4.setReminder(true);
		defaultEvent4.setReminderDays(0);
		defaultEvent4.setDate(date);
		defaultEvent4.setAccountForYear(false);

		eventRepository.save(defaultEvent1);
		eventRepository.save(defaultEvent2);
		eventRepository.save(defaultEvent3);
		eventRepository.save(defaultEvent4);
		// log.info("-------------------");
		// log.info("The following events were created.");
		for (Event event : eventRepository.findAll()) {
			// log.info("*** " + event.getEventName());
		}

	}
}
