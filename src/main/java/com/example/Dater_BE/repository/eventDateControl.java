package com.example.Dater_BE.repository;

import java.lang.reflect.Array;
import java.util.HashMap;

import com.example.Dater_BE.model.Event;
import com.example.Dater_BE.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class eventDateControl {

    @Autowired
    private static EventRepository eventRepository;

    static String test = "test";

    public static void main(String[] args) {

        System.out.println(test);
        getdates();
    }

    public static void getdates() {
        String[] dates = { "date1", "date2" };
        String[] eventIds = { "eventId1", "eventId2" };
        String currentDate = "currentDate";
        Integer[] daysBeforeList = { 0, 10 };

        /*
         * for (String string : dates) { System.out.println(string); }
         */

        for (int a = 0; a < dates.length; a++) {
            System.out.println(dates[a]);
            System.out.println(daysBeforeList[a]);
            System.out.println("current date is " + currentDate);

        }

        for (Event event : eventRepository.findAll()) {
            System.out.println(event);
        }

    }

}
