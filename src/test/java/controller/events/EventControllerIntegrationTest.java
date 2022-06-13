package controller.events;

import com.example.dater.model.Events;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.Instant;

import static controller.TestObjects.createEventWithCreatedDate;
import static controller.TestObjects.createEventWithoutCreatedDate;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventControllerIntegrationTest extends EventsBaseIntegrationTest {

    @Test
     void shouldCreateEvent () throws Exception {
        Events events = createEventWithoutCreatedDate().setName("Event created now!");
        mockMvc.perform(post("/api/event")
                        .content(getBytes(events))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Event created now!"))
                .andExpect(jsonPath("$.dateCreated").isNotEmpty())
                .andExpect(jsonPath("$.dateUpdated").isEmpty())
                .andExpect(jsonPath("$.dateNextReminder").value("2022-02-18T13:26:13.836Z"));
    }

    @Test
    void shouldUpdateEvent() throws Exception {
        Events createdEvents =  mongoTemplate.insert(createEventWithCreatedDate());

        String path = "/api/event/" + createdEvents.getId();
        Events updatedEvents = new Events()
                .setName("UPDATED_NAME")
                .setDate(Instant.parse("2023-02-19T13:26:13.836Z"))
                .setAccountForYear(true)
                .setReminder(true)
                .setReminderDays(2);

        mockMvc.perform(put(path)
                .content(getBytes(updatedEvents))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/event").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(1))
                .andExpect(jsonPath("$.[0].name").value("UPDATED_NAME"))
                .andExpect(jsonPath("$.[0].accountForYear").value(true))
                .andExpect(jsonPath("$.[0].date").value("2023-02-19T13:26:13.836Z"))
                .andExpect(jsonPath("$.[0].reminder").value(true))
                .andExpect(jsonPath("$.[0].reminderDays").value(2))
                .andExpect(jsonPath("$.[0].description").isEmpty())
                .andExpect(jsonPath("$.[0].dateCreated").isNotEmpty())
                .andExpect(jsonPath("$.[0].dateUpdated").isNotEmpty())
                .andExpect(jsonPath("$.[0].dateNextReminder").value("2022-02-17T13:26:13.836Z"));
    }

    @Test
    void shouldGetEvents() throws Exception {
        mongoTemplate.insert(createEventWithoutCreatedDate());

        mockMvc.perform(get("/api/event").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(1))
                .andExpect(jsonPath("$.[0].name").value("Hello world!"))
                .andExpect(jsonPath("$.[0].date").value("2022-02-19T13:26:13.836Z"))
                .andExpect(jsonPath("$.[0].reminder").value(true));
    }

    @Test
    void shouldDeleteEvent() throws Exception {
        Events createdEvents =  mongoTemplate.insert(createEventWithoutCreatedDate());
        mockMvc.perform(get("/api/event").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(1));

        String path = "/api/event/" + createdEvents.getId();
        mockMvc.perform(delete(path)).andExpect(status().isOk());
        mockMvc.perform(get("/api/event").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(0));
    }

    @Test
    void shouldDeleteEvents() throws Exception {
        Events createdEvents1 =  mongoTemplate.insert(createEventWithoutCreatedDate());
        Events createdEvents2 =  mongoTemplate.insert(createEventWithoutCreatedDate());

        String[] idList = {createdEvents1.getId(), createdEvents2.getId()};
        mockMvc.perform(post("/api/event/delete").content(getBytes(idList))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/event").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(0));
    }

    @Test
    void shouldCheckEvents() throws Exception {
        mockMvc.perform(get("/api/event/checkEvents").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(status().isOk());
    }
}
