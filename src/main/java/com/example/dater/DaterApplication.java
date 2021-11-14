package com.example.dater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.dater.model.Event;
import com.example.dater.repository.EventRepository;

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
		defaultEvent1.setEventName("email send event!");
		defaultEvent1.setDescription("This is a description!");
		defaultEvent1.setReminder(true);
		defaultEvent1.setReminderDays(0);
		defaultEvent1.setDate("2021-11-14T20:09:41.413Z");
		defaultEvent1.setAccountForYear(false);

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
		defaultEvent4.setEventName("Isadepäev!");
		defaultEvent4.setDescription("");
		defaultEvent4.setReminder(true);
		defaultEvent4.setReminderDays(0);
		defaultEvent4.setDate("2021-11-14T21:15:48.976Z");
		defaultEvent4.setAccountForYear(false);

		eventRepository.save(defaultEvent1);
		eventRepository.save(defaultEvent2);
		eventRepository.save(defaultEvent3);
		eventRepository.save(defaultEvent4);

		System.out.println("------");
		System.out.println("The following events were created.");

		for (Event event : eventRepository.findAll()) {
			System.out.println(event);
		}

	}
}
