package controller.log;

import com.example.dater.model.Events;
import com.example.dater.model.Logs;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.Instant;

import static controller.TestObjects.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LogsControllerIntegrationTest extends LogBaseIntegrationTest {

    @Test
    void shouldSaveNewLogWithMail() throws Exception {
        mongoTemplate.insert(createSetting().setIsEmailActive(true).setIsSmsActive(false));
        Events remindedEvents = createEventWithoutCreatedDate().setDate(Instant.now()).setReminder(true).setReminderDays(0);

        mockMvc.perform(post("/api/events")
                        .content(getBytes(remindedEvents))
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
    void shouldSaveNewLogWithSms() throws Exception {
        mongoTemplate.insert(createSetting().setIsEmailActive(false).setIsSmsActive(true));
        Events remindedEvents = createEventWithoutCreatedDate().setDate(Instant.now()).setReminder(true).setReminderDays(0);

         mockMvc.perform(post("/api/events")
                        .content(getBytes(remindedEvents))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        // Will initiate an event check, which will generate a log entry
        mockMvc.perform(get("/api/events/checkEvents").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/logs").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").isNotEmpty())
                .andExpect(jsonPath("$.[0].id").isNotEmpty());
    }

    @Test
    void shouldGetLogs() throws Exception {
        Logs existingLogs = mongoTemplate.insert(createLog());

        mockMvc.perform(get("/api/logs").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(1))
                .andExpect(jsonPath("$.[0].sentToAddress").value("per...@gmail.com"))
                .andExpect(jsonPath("$.[0].dateCreated").isNotEmpty())
                .andExpect(jsonPath("$.[0].initiatedBy").value("admin"))
                .andExpect(jsonPath("$.[0].messageContent").value("[mail]"))
                .andExpect(jsonPath("$.[0].schedulerValue").value(10))
                .andExpect(jsonPath("$.[0].id").value(existingLogs.getId()));
    }
}
