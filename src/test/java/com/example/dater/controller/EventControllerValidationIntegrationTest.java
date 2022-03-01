package com.example.dater.controller;

import com.example.dater.model.Event;
import org.junit.jupiter.api.Test;

import static com.example.dater.controller.TestObject.createEvent;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
        Event event = createEvent().setName("");
        postFunctionBody(getBytes(event));
    }

    @Test
    void createEvent_shouldThrow_exception_whenNameTooLong() throws Exception {
        Event event = createEvent().setName("Lorem ipsum dolor sit nunc.");
        postFunctionBody(getBytes(event));
    };
   @Test
    void createEvent_shouldThrow_exception_whenNameNull() throws Exception {
       Event event = createEvent().setName(null);
       postFunctionBody(getBytes(event));
   };

    @Test
    void createEvent_shouldThrow_exception_whenDateEmpty() throws Exception {
        Event event = createEvent().setDate("");
        postFunctionBody(getBytes(event));
    };

    @Test
    void createEvent_shouldThrow_exception_whenDateTooLong() throws Exception {
        Event event = createEvent().setDate("2022-02-19T13:26:13.836ZASDASDASFAG");
        postFunctionBody(getBytes(event));
    };

    @Test
    void createEvent_shouldThrow_exception_whenReminderInDaysNull() throws Exception {
        Event event = createEvent().setReminderDays(null);
        postFunctionBody(getBytes(event));
    };

    @Test
    void createEvent_shouldThrow_exception_whenReminderDaysTooHigh() throws Exception {
        Event event = createEvent().setReminderDays(32);
        postFunctionBody(getBytes(event));
    };

    @Test
    void createEvent_shouldThrow_exception_whenReminderNull() throws Exception {
        Event event = createEvent().setReminder(null);
        postFunctionBody(getBytes(event));
    }

    @Test
    void createEvent_shouldThrow_exception_whenAccountForYearNull() throws Exception {
        Event event = createEvent().setAccountForYear(null);
        postFunctionBody(getBytes(event));
    }

    @Test
    void createEvent_shouldThrow_exception_whenIdisGiven() throws Exception {
        Event event = createEvent().setId("id");
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
        Event updatedEvent = createEvent().setName("New Name");

        String path = "/api/events/";
        mockMvc.perform(put(path)
                        .content(getBytes(updatedEvent))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }
    @Test
    void updateEvent_shouldThrow_exception_whenNotExistingPinIsGiven() throws Exception {
        Event updatedEvent = createEvent().setName("New Name");

        updateFunctionBody(getBytes(updatedEvent), "abc");
    }

    // ToDo Fix test
    //    @Test
//    void updateEvent_shouldThrow_exception_whenInvalidNameIsGiven() throws Exception {
//        Event existingEvent = mongoTemplate.insert(createEvent());
//        Event updatedEvent = createEvent().setName("");
//
//        updateFunctionBody(getBytes(updatedEvent), existingEvent.getId());
//    }
}
