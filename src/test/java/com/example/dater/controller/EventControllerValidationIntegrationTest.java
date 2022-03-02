package com.example.dater.controller;

import com.example.dater.model.Event;
import org.junit.jupiter.api.Test;

import static com.example.dater.controller.TestObject.createEventWithoutCreated;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EventControllerValidationIntegrationTest extends BaseIntegrationTest {

    private void postFunctionBody(byte[] event) throws Exception {
        mockMvc.perform(post("/api/events")
                        .content(event)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createEvent_shouldThrow_exception_whenNameEmptyString() throws Exception {
        Event event = createEventWithoutCreated().setName("");
        postFunctionBody(getBytes(event));
    }

    @Test
    void createEvent_shouldThrow_exception_whenNameTooLong() throws Exception {
        Event event = createEventWithoutCreated().setName("Lorem ipsum dolor sit nunc.");
        postFunctionBody(getBytes(event));
    };

    @Test
    void createEvent_shouldThrow_exception_whenNameNull() throws Exception {
       Event event = createEventWithoutCreated().setName(null);
       postFunctionBody(getBytes(event));
   };

    @Test
    void createEvent_shouldThrow_exception_whenDateEmpty() throws Exception {
        Event event = createEventWithoutCreated().setDate("");
        postFunctionBody(getBytes(event));
    };

    @Test
    void createEvent_shouldThrow_exception_whenDateTooLong() throws Exception {
        Event event = createEventWithoutCreated().setDate("2022-02-19T13:26:13.836ZASDASDASFAG");
        postFunctionBody(getBytes(event));
    };

    @Test
    void createEvent_shouldThrow_exception_whenReminderInDaysNull() throws Exception {
        Event event = createEventWithoutCreated().setReminderDays(null);
        postFunctionBody(getBytes(event));
    };

    @Test
    void createEvent_shouldThrow_exception_whenReminderDaysTooHigh() throws Exception {
        Event event = createEventWithoutCreated().setReminderDays(32);
        postFunctionBody(getBytes(event));
    };

    @Test
    void createEvent_shouldThrow_exception_whenReminderNull() throws Exception {
        Event event = createEventWithoutCreated().setReminder(null);
        postFunctionBody(getBytes(event));
    }

    @Test
    void createEvent_shouldThrow_exception_whenAccountForYearNull() throws Exception {
        Event event = createEventWithoutCreated().setAccountForYear(null);
        postFunctionBody(getBytes(event));
    }

    @Test
    void createEvent_shouldThrow_exception_whenIdIsGiven() throws Exception {
        Event event = createEventWithoutCreated().setId("id");
        postFunctionBody(getBytes(event));
    }

    private void updateFunctionBody(byte[] updatedEvent, String existingId) throws Exception {
        String path = "/api/events/" + existingId;
        mockMvc.perform(put(path)
                        .content(updatedEvent)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateEvent_shouldThrow_exception_whenNoIdIsGiven() throws Exception {
        Event updatedEvent = createEventWithoutCreated();

        String path = "/api/events/";
        mockMvc.perform(put(path)
                        .content(getBytes(updatedEvent))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void updateEvent_shouldThrow_exception_whenNotExistingIdIsGiven() throws Exception {
        Event updatedEvent = createEventWithoutCreated();

        updateFunctionBody(getBytes(updatedEvent), "abc");
    }

    @Test
    void updateEvent_shouldThrow_exception_whenInvalidNameIsGiven() throws Exception {
        Event existingEvent = mongoTemplate.insert(createEventWithoutCreated());
        Event updatedEvent = createEventWithoutCreated().setName("");
        updateFunctionBody(getBytes(updatedEvent), existingEvent.getId());
    }

    @Test
    void deleteEvent_shouldThrow_exception_whenInvalidId() throws Exception {
        mongoTemplate.insert(createEventWithoutCreated());

        String path = "/api/events/adasd";
        mockMvc.perform(delete(path))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(1));
    }
}
