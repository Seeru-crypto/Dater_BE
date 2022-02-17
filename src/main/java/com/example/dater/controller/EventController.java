package com.example.dater.controller;

import com.example.dater.model.Event;
import com.example.dater.schedulingtasks.EventDateChecker;
import com.example.dater.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping(path = "api/events")
public class EventController {

    private final EventService eventService;
    private final EventDateChecker eventDateChecker;

    @GetMapping
    public List<Event> findAll() {
        return eventService.findAll();
    }

    @PostMapping
    public Event save(@RequestBody Event newEvent) {
        return eventService.save(newEvent);
    }

    @DeleteMapping(path = "{eventId}")
    public void delete(@PathVariable("eventId") String eventId){
        eventService.delete(eventId);
    }

//    @PutMapping("/employees/{id}")
//    Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
//
//        return repository.findById(id)
//                .map(employee -> {
//                    employee.setName(newEmployee.getName());
//                    employee.setRole(newEmployee.getRole());
//                    return repository.save(employee);
//                })
//                .orElseGet(() -> {
//                    newEmployee.setId(id);
//                    return repository.save(newEmployee);
//                });
//    }



    @GetMapping("/checkEvents")
    public void checkItems() {
        try {
            eventDateChecker.checkEventDates();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
