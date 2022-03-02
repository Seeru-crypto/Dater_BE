package com.example.dater.controller;

import com.example.dater.model.Event;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static com.example.dater.controller.TestObject.createEventWithCreatedDate;
import static com.example.dater.controller.TestObject.createEventWithoutCreated;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventControllerIntegrationTest extends BaseIntegrationTest {

    @Test
     void shouldCreateEvent () throws Exception {
        Event event = createEventWithoutCreated().setName("Event created now!");
        mockMvc.perform(post("/api/events")
                        .content(getBytes(event))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name").value("Event created now!"))
                .andExpect(jsonPath("$.dateCreated").isNotEmpty());
    }

    @Test
    void shouldUpdateEvent() throws Exception {
        Event createdEvent =  mongoTemplate.insert(createEventWithCreatedDate());

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
                .andExpect(jsonPath("$.[0].name").value("UPDATED_NAME"))
                .andExpect(jsonPath("$.[0].accountForYear").value(true))
                .andExpect(jsonPath("$.[0].date").value("2023-02-19T13:26:13.836Z"))
                .andExpect(jsonPath("$.[0].reminder").value(true))
                .andExpect(jsonPath("$.[0].reminderDays").value(2))
                .andExpect(jsonPath("$.[0].description").isEmpty())
                .andExpect(jsonPath("$.[0].dateCreated").isNotEmpty())
                .andExpect(jsonPath("$.[0].dateUpdated").isNotEmpty());
    }

    @Test
    void shouldGetEvents() throws Exception {
        mongoTemplate.insert(createEventWithoutCreated());

        mockMvc.perform(get("/api/events").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(1))
                .andExpect(jsonPath("$.[0].name").value("Hello world!"))
                .andExpect(jsonPath("$.[0].date").value("2022-02-19T13:26:13.836Z"))
                .andExpect(jsonPath("$.[0].reminder").value(false));
    }

    @Test
    void shouldDeleteEvent() throws Exception {
        Event createdEvent =  mongoTemplate.insert(createEventWithoutCreated());
        mockMvc.perform(get("/api/events").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(1));

        String path = "/api/events/" + createdEvent.getId();
        mockMvc.perform(delete(path)).andExpect(status().isOk());
        mockMvc.perform(get("/api/events").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(0));
    }

    @Test
    void shouldDeleteEvents() throws Exception {
        Event createdEvent1 =  mongoTemplate.insert(createEventWithoutCreated());
        Event createdEvent2 =  mongoTemplate.insert(createEventWithoutCreated());

        String[] idList = {createdEvent1.getId(), createdEvent2.getId()};
        mockMvc.perform(post("/api/events/delete").content(getBytes(idList))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/events").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(0));
    }

    @Test
    void shouldCheckEvents() throws Exception {
        mockMvc.perform(get("/api/events/checkEvents").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(status().isOk());
    }
}
