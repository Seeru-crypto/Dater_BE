package controller.events;

import com.example.dater.model.Events;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static com.example.dater.model.Events.*;
import static controller.TestObjects.createEventWithoutCreatedDate;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EventControllerValidationIntegrationTest extends EventsBaseIntegrationTest {

    private void postFunctionBody(byte[] event) throws Exception {
        mockMvc.perform(post("/api/event")
                        .content(event)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createEvent_shouldThrow_exception_whenDescriptionTooLong() throws Exception {
        String tooLongDesc = new String(new char[MAX_DESC_LEN + 5]).replace('\0', 'a');
        Events events = createEventWithoutCreatedDate().setDescription(tooLongDesc);
        postFunctionBody(getBytes(events));
    };

    @Test
    void createEvent_shouldThrow_exception_whenNameEmptyString() throws Exception {
        Events events = createEventWithoutCreatedDate().setName("");
        postFunctionBody(getBytes(events));
    }

    @Test
    void createEvent_shouldThrow_exception_whenNameTooLong() throws Exception {
        String tooLongName = new String(new char[MAX_NAME_LEN + 5]).replace('\0', 'a');
        Events events = createEventWithoutCreatedDate().setName(tooLongName);
        postFunctionBody(getBytes(events));
    };

    @Test
    void createEvent_shouldThrow_exception_whenNameNull() throws Exception {
       Events events = createEventWithoutCreatedDate().setName(null);
       postFunctionBody(getBytes(events));
   };

    @Test
    void createEvent_shouldThrow_exception_whenDateYearisBefore1960() throws Exception {
        Events events = createEventWithoutCreatedDate().setDate(Instant.parse("1959-12-30T21:00:00.000Z"));
        postFunctionBody(getBytes(events));
    };

    @Test
    void createEvent_shouldThrow_exception_whenDateYearisAfter2040() throws Exception {
        Events events = createEventWithoutCreatedDate().setDate(Instant.parse("2040-12-31T22:00:00.000Z"));
        postFunctionBody(getBytes(events));
    };

    @Test
    void createEvent_shouldThrow_exception_whenReminderInDaysNull() throws Exception {
        Events events = createEventWithoutCreatedDate().setReminderDays(null);
        postFunctionBody(getBytes(events));
    };

    @Test
    void createEvent_shouldThrow_exception_whenReminderDaysTooHigh() throws Exception {
        Events events = createEventWithoutCreatedDate().setReminderDays(REMINDER_DAYS_MAX_VALUE+1);
        postFunctionBody(getBytes(events));
    };

    @Test
    void createEvent_shouldThrow_exception_whenReminderNull() throws Exception {
        Events events = createEventWithoutCreatedDate().setReminder(null);
        postFunctionBody(getBytes(events));
    }

    @Test
    void createEvent_shouldThrow_exception_whenAccountForYearNull() throws Exception {
        Events events = createEventWithoutCreatedDate().setAccountForYear(null);
        postFunctionBody(getBytes(events));
    }

    @Test
    void createEvent_shouldThrow_exception_whenIdIsGiven() throws Exception {
        Events events = createEventWithoutCreatedDate().setId("id");
        postFunctionBody(getBytes(events));
    }

    private void updateFunctionBody(byte[] updatedEvent, String existingId) throws Exception {
        String path = "/api/event/" + existingId;
        mockMvc.perform(put(path)
                        .content(updatedEvent)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateEvent_shouldThrow_exception_whenNoIdIsGiven() throws Exception {
        Events updatedEvents = createEventWithoutCreatedDate();

        String path = "/api/event/";
        mockMvc.perform(put(path)
                        .content(getBytes(updatedEvents))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void updateEvent_shouldThrow_exception_whenNotExistingIdIsGiven() throws Exception {
        Events updatedEvents = createEventWithoutCreatedDate();

        String path = "/api/event/abc";
        mockMvc.perform(put(path)
                        .content(getBytes(updatedEvents))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateEvent_shouldThrow_exception_whenInvalidNameIsGiven() throws Exception {
        Events existingEvents = mongoTemplate.insert(createEventWithoutCreatedDate());
        Events updatedEvents = createEventWithoutCreatedDate().setName("");
        updateFunctionBody(getBytes(updatedEvents), existingEvents.getId());
    }

    @Test
    void deleteEvent_shouldThrow_exception_whenInvalidId() throws Exception {
        mongoTemplate.insert(createEventWithoutCreatedDate());

        String path = "/api/event/adasd";
        mockMvc.perform(delete(path))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/api/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(1));
    }
}
