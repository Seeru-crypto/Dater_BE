package controller.events;

import com.example.dater.model.Event;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static com.example.dater.model.Event.*;
import static controller.TestObjects.createEventWithoutCreatedDate;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EventControllerValidationIntegrationTest extends EventBaseIntegrationTest {

    private void postFunctionBody(byte[] event) throws Exception {
        mockMvc.perform(post("/api/events")
                        .content(event)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void createEvent_shouldThrow_exception_whenDescriptionTooLong() throws Exception {
        String tooLongDesc = new String(new char[MAX_DESC_LEN + 5]).replace('\0', 'a');
        Event event = createEventWithoutCreatedDate().setDescription(tooLongDesc);
        postFunctionBody(getBytes(event));
    };

    @Test
    void createEvent_shouldThrow_exception_whenNameEmptyString() throws Exception {
        Event event = createEventWithoutCreatedDate().setName("");
        postFunctionBody(getBytes(event));
    }

    @Test
    void createEvent_shouldThrow_exception_whenNameTooLong() throws Exception {
        String tooLongName = new String(new char[MAX_NAME_LEN + 5]).replace('\0', 'a');
        Event event = createEventWithoutCreatedDate().setName(tooLongName);
        postFunctionBody(getBytes(event));
    };

    @Test
    void createEvent_shouldThrow_exception_whenNameNull() throws Exception {
       Event event = createEventWithoutCreatedDate().setName(null);
       postFunctionBody(getBytes(event));
   };

    @Test
    void createEvent_shouldThrow_exception_whenDateYearisBefore1960() throws Exception {
        Event event = createEventWithoutCreatedDate().setDate(Instant.parse("1959-12-30T21:00:00.000Z"));
        postFunctionBody(getBytes(event));
    };

    @Test
    void createEvent_shouldThrow_exception_whenDateYearisAfter2040() throws Exception {
        Event event = createEventWithoutCreatedDate().setDate(Instant.parse("2040-12-31T22:00:00.000Z"));
        postFunctionBody(getBytes(event));
    };

    @Test
    void createEvent_shouldThrow_exception_whenReminderInDaysNull() throws Exception {
        Event event = createEventWithoutCreatedDate().setReminderDays(null);
        postFunctionBody(getBytes(event));
    };

    @Test
    void createEvent_shouldThrow_exception_whenReminderDaysTooHigh() throws Exception {
        Event event = createEventWithoutCreatedDate().setReminderDays(REMINDER_DAYS_MAX_VALUE+1);
        postFunctionBody(getBytes(event));
    };

    @Test
    void createEvent_shouldThrow_exception_whenReminderNull() throws Exception {
        Event event = createEventWithoutCreatedDate().setReminder(null);
        postFunctionBody(getBytes(event));
    }

    @Test
    void createEvent_shouldThrow_exception_whenAccountForYearNull() throws Exception {
        Event event = createEventWithoutCreatedDate().setAccountForYear(null);
        postFunctionBody(getBytes(event));
    }

    @Test
    void createEvent_shouldThrow_exception_whenIdIsGiven() throws Exception {
        Event event = createEventWithoutCreatedDate().setId("id");
        postFunctionBody(getBytes(event));
    }

    private void updateFunctionBody(byte[] updatedEvent, String existingId) throws Exception {
        String path = "/api/events/" + existingId;
        mockMvc.perform(put(path)
                        .content(updatedEvent)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateEvent_shouldThrow_exception_whenNoIdIsGiven() throws Exception {
        Event updatedEvent = createEventWithoutCreatedDate();

        String path = "/api/events/";
        mockMvc.perform(put(path)
                        .content(getBytes(updatedEvent))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void updateEvent_shouldThrow_exception_whenNotExistingIdIsGiven() throws Exception {
        Event updatedEvent = createEventWithoutCreatedDate();

        String path = "/api/events/abc";
        mockMvc.perform(put(path)
                        .content(getBytes(updatedEvent))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateEvent_shouldThrow_exception_whenInvalidNameIsGiven() throws Exception {
        Event existingEvent = mongoTemplate.insert(createEventWithoutCreatedDate());
        Event updatedEvent = createEventWithoutCreatedDate().setName("");
        updateFunctionBody(getBytes(updatedEvent), existingEvent.getId());
    }

    @Test
    void deleteEvent_shouldThrow_exception_whenInvalidId() throws Exception {
        mongoTemplate.insert(createEventWithoutCreatedDate());

        String path = "/api/events/adasd";
        mockMvc.perform(delete(path))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(1));
    }
}
