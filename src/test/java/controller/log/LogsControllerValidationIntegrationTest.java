package controller.log;

import com.example.dater.model.Logs;
import org.junit.jupiter.api.Test;

import static controller.TestObjects.createLog;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LogsControllerValidationIntegrationTest extends LogBaseIntegrationTest {

    @Test
    void createLog_shouldThrow_exception() throws Exception {
        Logs newLogs = createLog();
        mockMvc.perform(post("/api/log")
                        .content(getBytes(newLogs))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteLog_shouldThrow_exception() throws Exception {
        Logs newLogs = mongoTemplate.insert(createLog());
        String path = "/api/log/"+ newLogs.getId();

        mockMvc.perform(delete(path))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/api/log").contentType(APPLICATION_JSON))
                .andExpect(jsonPath("length()").value(1));
    };

    @Test
    void putLog_shouldThrow_exception() throws Exception {
        Logs newLogs = mongoTemplate.insert(createLog());
        String path = "/api/log/"+ newLogs.getId();
        Logs changedLogs = createLog().setInitiatedBy("parrot");

        mockMvc.perform(put(path)
                .content(getBytes(changedLogs))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/api/log").contentType(APPLICATION_JSON))
                .andExpect(jsonPath("length()").value(1))
                .andExpect(jsonPath("$.[0].initiatedBy").value("admin"));
    }
}
