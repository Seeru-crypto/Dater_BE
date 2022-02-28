package com.example.dater.controller;

import com.example.dater.model.Event;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.core.annotation.Order;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventControllerIntegrationTest extends BaseIntegrationTest {

    String generatedID = new String();
    @Test
    @Order (1)
    void createEvent() throws Exception {
        System.out.println("TEST: Creating");
        Event event = new Event()
                .setName("TEST_DATA")
                .setDate("2022-02-19T13:26:13.836Z")
                .setAccountForYear(false)
                .setReminder(false)
                .setReminderDays(1);
        ResultActions result = mockMvc.perform(post("/api/events")
                        .content(getBytes(event))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        MvcResult mvcResult = result.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Event createdEvent = objectMapper.readValue(contentAsString, Event.class);

        assertEquals("TEST_DATA",createdEvent.getName());
        generatedID = createdEvent.getId();
    };

    @Test
    @Order(2)
    void updateEvent() throws Exception {
        System.out.println("TEST: Updating");
        String path = "/api/events/" + generatedID;
        Event event = new Event()
                .setName("NEW_NAME")
                .setDate("2023-02-19T13:26:13.836Z")
                .setAccountForYear(true)
                .setReminder(true)
                .setReminderDays(2);

        mockMvc.perform(put(path)
                .content(getBytes(event))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    };

    @Test
    @Order(2)
    void getEvents() throws Exception {
        mockMvc.perform(get("/api/events").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty())
                .andExpect(content().string(containsString(generatedID)))
                .andExpect(content().string(containsString("NEW_NAME")));
    };

    @Test
    @Order(3)
    void deleteEvent() throws Exception {
        System.out.println("TEST: DEleting");
        String path = "/api/events/" + generatedID;
        mockMvc.perform(delete(path)).andExpect(status().isOk());
    };
}
