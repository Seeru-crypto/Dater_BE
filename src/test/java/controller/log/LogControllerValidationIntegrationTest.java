package controller.log;

import com.example.dater.model.Log;
import org.junit.jupiter.api.Test;

import static controller.TestObjects.createLog;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LogControllerValidationIntegrationTest extends LogBaseIntegrationTest {

    @Test
    void createLog_shouldThrow_exception() throws Exception {
        Log newLog = createLog();
        mockMvc.perform(post("/api/log")
                        .content(getBytes(newLog))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteLog_shouldThrow_exception() throws Exception {
        Log newLog = mongoTemplate.insert(createLog());
        String path = "/api/log/"+newLog.getId();

        mockMvc.perform(delete(path))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/api/logs").contentType(APPLICATION_JSON))
                .andExpect(jsonPath("length()").value(1));
    };

    @Test
    void putLog_shouldThrow_exception() throws Exception {
        Log newLog = mongoTemplate.insert(createLog());
        String path = "/api/log/"+newLog.getId();
        Log ChangedLog = createLog().setInitiatedBy("parrot");

        mockMvc.perform(put(path)
                .content(getBytes(ChangedLog))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/api/logs").contentType(APPLICATION_JSON))
                .andExpect(jsonPath("length()").value(1))
                .andExpect(jsonPath("$.[0].initiatedBy").value("admin"));
    }
}
