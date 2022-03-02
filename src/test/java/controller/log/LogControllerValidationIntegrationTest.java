package controller.log;

import controller.settings.SettingBaseIntegrationTest;
import org.junit.jupiter.api.Test;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LogControllerValidationIntegrationTest extends SettingBaseIntegrationTest {

    private void putFunctionBody(byte[] newSetting, String settingId, String pinValue) throws Exception {
        String path = "/api/settings/" + settingId;
        mockMvc.perform(put(path).param("pin",pinValue)
                        .content(newSetting)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }
    @Test
    void createLog_shouldThrow_exception() throws Exception {

    }
    @Test
    void deleteLog_shouldThrow_exception() throws Exception {
    }
    @Test
    void putLog_shouldThrow_exception() throws Exception {
    }
}
