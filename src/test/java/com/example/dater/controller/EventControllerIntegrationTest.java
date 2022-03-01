package com.example.dater.controller;

import com.example.dater.model.Event;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static com.example.dater.controller.TestObject.createEvent;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventControllerIntegrationTest extends BaseIntegrationTest {

    @Test
     void shouldCreateEvent () throws Exception {
        Event event = createEvent().setName("Event created now!");
        mockMvc.perform(post("/api/events")
                        .content(getBytes(event))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Event created now!"));
    }

    @Test
    void shouldUpdateEvent() throws Exception {
        Event createdEvent =  mongoTemplate.insert(createEvent());

        String path = "/api/events/" + createdEvent.getId();
        Event updatedEvent = new Event()
                .setName("UPDATED_NAME")
                .setDate("2023-02-19T13:26:13.836Z")
                .setAccountForYear(true)
                .setReminder(true)
                .setReminderDays(2);

        mockMvc.perform(put(path)
                .content(getBytes(updatedEvent))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/events").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(1))
                .andDo(print())
                .andExpect(jsonPath("$.[0].name").value("UPDATED_NAME"))
                .andExpect(jsonPath("$.[0].accountForYear").value(true))
                .andExpect(jsonPath("$.[0].reminder").value(true))
                .andExpect(jsonPath("$.[0].reminderDays").value(2))
                .andExpect(jsonPath("$.[0].description").isEmpty());
    }

    @Test
    void shouldGetEvents() throws Exception {
        mongoTemplate.insert(createEvent());

        mockMvc.perform(get("/api/events").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(1))
                .andExpect(jsonPath("$.[0].name").value("Hello world!"))
                .andExpect(jsonPath("$.[0].date").value("2022-02-19T13:26:13.836Z"))
                .andExpect(jsonPath("$.[0].reminder").value(false));
    }

    @Test
    void shouldDeleteEvent() throws Exception {
        Event createdEvent =  mongoTemplate.insert(createEvent());

        String path = "/api/events/" + createdEvent.getId();
        mockMvc.perform(delete(path)).andExpect(status().isOk());
        mockMvc.perform(get("/api/events").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(0));
    }

    @Test
    void shouldDeleteEvents() throws Exception {
        Event createdEvent1 =  mongoTemplate.insert(createEvent());
        Event createdEvent2 =  mongoTemplate.insert(createEvent());

        String[] idList = {createdEvent1.getId(), createdEvent2.getId()};
        mockMvc.perform(post("/api/events/delete").content(getBytes(idList)).contentType(APPLICATION_JSON)).andExpect(status().isOk());
        mockMvc.perform(get("/api/events").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(0));
    }
}
