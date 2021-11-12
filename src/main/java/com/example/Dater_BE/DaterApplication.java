package com.example.Dater_BE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.Dater_BE.model.Event;
import com.example.Dater_BE.repository.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
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
		defaultEvent1.setEventName("Default 1");
		defaultEvent1.setDescription("description 1");
		defaultEvent1.setReminder(true);
		defaultEvent1.setReminderDays(11);
		defaultEvent1.setDate("2021-11-07T15:05:53.600Z");

		Event defaultEvent2 = new Event();
		defaultEvent2.setEventName("Default 2");
		defaultEvent2.setDescription("Qui et quia commodi neque sit maxime. Molestiae quia veritatis aliquid."
				+ "Aperiam reprehenderit suscipit dolor unde dolorem aspernatur nisi.");
		defaultEvent2.setReminder(false);
		defaultEvent2.setReminderDays(0);
		defaultEvent2.setDate("2021-11-12T20:09:41.413Z");

		eventRepository.save(defaultEvent1);
		eventRepository.save(defaultEvent2);

		System.out.println("------");
		System.out.println("The following events were created.");

		for (Event event : eventRepository.findAll()) {
			System.out.println(event);
		}

	}

}
