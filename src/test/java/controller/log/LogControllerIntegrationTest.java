package controller.log;

import com.example.dater.model.Event;
import com.example.dater.model.Log;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.LocalDateTime;

import static controller.TestObjects.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LogControllerIntegrationTest extends LogBaseIntegrationTest {

    @Test
    void shouldSaveNewLog() throws Exception {
        mongoTemplate.insert(createSetting().setIsEmailActive(true));
        LocalDateTime currentDate = LocalDateTime.now();
        Event remindedEvent = createEventWithoutCreatedDate().setDate(currentDate.toString()).setReminder(true).setReminderDays(0);

        mockMvc.perform(post("/api/events")
                        .content(getBytes(remindedEvent))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        // Will initiate an event check, which will generate a log entry
        mockMvc.perform(get("/api/events/checkEvents").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/logs").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").isNotEmpty());
    }

    @Test
    void shouldGetLogs() throws Exception {
        Log existingLog = mongoTemplate.insert(createLog());

        mockMvc.perform(get("/api/logs").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(1))
                .andExpect(jsonPath("$.[0].sentToAddress").value("per...@gmail.com"))
                .andExpect(jsonPath("$.[0].dateCreated").isNotEmpty())
                .andExpect(jsonPath("$.[0].initiatedBy").value("admin"))
                .andExpect(jsonPath("$.[0].mailContent").value("[mail]"))
                .andExpect(jsonPath("$.[0].schedulerValue").value(10))
                .andExpect(jsonPath("$.[0].id").value(existingLog.getId()));
    }
}
