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
		defaultEvent1.setEventName("email send event!");
		defaultEvent1.setDescription("description 1");
		defaultEvent1.setReminder(true);
		defaultEvent1.setReminderDays(0);
		defaultEvent1.setDate("2021-11-13T20:09:41.413Z");

		Event defaultEvent2 = new Event();
		defaultEvent2.setEventName("Default 2");
		defaultEvent2.setDescription("Qui et quia commodi neque sit maxime. Molestiae quia veritatis aliquid."
				+ "Aperiam reprehenderit suscipit dolor unde dolorem aspernatur nisi.");
		defaultEvent2.setReminder(false);
		defaultEvent2.setReminderDays(0);
		defaultEvent2.setDate("2021-11-07T15:05:53.600Z");

		Event defaultEvent3 = new Event();
		defaultEvent3.setEventName("Doesnt send email");
		defaultEvent3.setDescription(" ");
		defaultEvent3.setReminder(true);
		defaultEvent3.setReminderDays(12);
		defaultEvent3.setDate("2021-03-02T21:15:48.976Z");

		eventRepository.save(defaultEvent1);
		eventRepository.save(defaultEvent2);
		eventRepository.save(defaultEvent3);

		System.out.println("------");
		System.out.println("The following events were created.");

		for (Event event : eventRepository.findAll()) {
			System.out.println(event);
		}

	}
}
