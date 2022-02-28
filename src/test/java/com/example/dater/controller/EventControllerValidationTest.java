package com.example.dater.controller;

import com.example.dater.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EventControllerValidationTest extends BaseIntegrationTest {


    private void functionBody(byte[] event) throws Exception {

        mockMvc.perform(post("/api/events")
                        .content(event)
                        .contentType(APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    Event event = new Event();
    @BeforeEach
    void setUp() {
        event.setName("Hello world!")
                .setDate("2022-02-19T13:26:13.836Z")
                .setAccountForYear(false)
                .setReminder(false)
                .setReminderDays(1);
    }

    @Test
    void createEvent_shouldThrow_exception_whenNameEmptyString() throws Exception {
        event.setName("");
        functionBody(getBytes(event));
    };

    @Test
    void createEvent_shouldThrow_exception_whenNameTooLong() throws Exception {
        event.setName("Lorem ipsum dolor sit nunc.");
        functionBody(getBytes(event));
    };
   @Test
    void createEvent_shouldThrow_exception_whenNameNull() throws Exception {
       event.setName(null);
       functionBody(getBytes(event));
   };

    @Test
    void createEvent_shouldThrow_exception_whenDateEmpty() throws Exception {
        event.setDate("");
        functionBody(getBytes(event));
    };

    @Test
    void createEvent_shouldThrow_exception_whenDateTooLong() throws Exception {
        event.setDate("2022-02-19T13:26:13.836ZASDASDASFAG");
        functionBody(getBytes(event));
    };

    @Test
    void createEvent_shouldThrow_exception_whenReminderInDaysNull() throws Exception {
        event.setReminderDays(null);
        functionBody(getBytes(event));
    };

    @Test
    void createEvent_shouldThrow_exception_whenReminderDaysTooHigh() throws Exception {
        event.setReminderDays(32);
        functionBody(getBytes(event));
    };


    @Test
    void createEvent_shouldThrow_exception_whenReminderNull() throws Exception {
        event.setReminder(null);
        functionBody(getBytes(event));
    }

    @Test
    void createEvent_shouldThrow_exception_whenAccountForYearNull() throws Exception {
        event.setAccountForYear(null);
        functionBody(getBytes(event));
    }

    @Test
    void createEvent_shouldThrow_exception_whenIdisGiven() throws Exception {
        event.setId("id");
        functionBody(getBytes(event));
    }
}
