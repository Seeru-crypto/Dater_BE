package com.example.Dater_BE.schedulingtasks;

import com.example.Dater_BE.model.Event;
import com.example.Dater_BE.model.minEvent;
import com.example.Dater_BE.repository.EventRepository;

public class EventDateChecker {

    private EventRepository repository;

    public void getEventData() {

        for (Event event : repository.findAll()) {
            System.out.println(event);
        }
        System.out.println();<z

        // TODO Auto-generated tub
        return null;
    }

    public String getReminderDate() {
        // TODO Auto-generated method stub
        return null;
    }

    public void checkEventDates() {
        // TODO Auto-generated method stub

    }

}
