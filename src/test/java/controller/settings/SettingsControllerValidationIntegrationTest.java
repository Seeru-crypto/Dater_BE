package controller.settings;

import com.example.dater.model.Settings;
import org.junit.jupiter.api.Test;

import static com.example.dater.model.Settings.MAX_EMAIL_LEN;
import static controller.TestObjects.createSetting;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SettingsControllerValidationIntegrationTest extends SettingBaseIntegrationTest {

    String correctPin = "154878";
    private void putFunctionBody(byte[] newSetting, String settingId, String pinValue) throws Exception {
        String path = "/api/settings/" + settingId;
        mockMvc.perform(put(path).param("pin",pinValue)
                        .content(newSetting)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void createSetting_shouldThrow_exception() throws Exception {
        Settings settings = createSetting();
        mockMvc.perform(post("/api/settings")
                        .content(getBytes(settings))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void deleteSetting_shouldThrow_exception() throws Exception {
        Settings createdSetting = mongoTemplate.insert(createSetting());
        mockMvc.perform(get("/api/settings").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(1))
                .andExpect(jsonPath("$.[0].id").value(createdSetting.getId()));

        String path = "/api/settings/" + createdSetting.getId();
        mockMvc.perform(delete(path))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void updateSetting_shouldThrow_exception_whenPinIncorrect() throws Exception {
        Settings createdSetting = mongoTemplate.insert(createSetting());
        Settings correctSetting = createSetting();
        String newPin = "abc";
        putFunctionBody(getBytes(correctSetting), createdSetting.getId(), newPin);
    }

    @Test
    void updateSetting_shouldThrow_exception_whenPinEmpty() throws Exception {
        Settings createdSetting = mongoTemplate.insert(createSetting());
        Settings correctSetting = createSetting();
        String newPin = "";
        putFunctionBody(getBytes(correctSetting), createdSetting.getId(), newPin);
    }

    @Test
    void updateSetting_shouldThrow_exception_whenEmailEmpty() throws Exception {
        Settings createdSetting = mongoTemplate.insert(createSetting());
        Settings newSetting = createSetting().setEmailAddress("");
        putFunctionBody(getBytes(newSetting), createdSetting.getId(), correctPin);
    }

    @Test
    void updateSetting_shouldThrow_exception_whenEmailTooLong() throws Exception {
        String tooLongEmailBody = new String(new char[MAX_EMAIL_LEN]).replace('\0', 'a');
        String longEmail = tooLongEmailBody + "@gmail.com";
        Settings createdSetting = mongoTemplate.insert(createSetting());
        Settings newSetting = createSetting().setEmailAddress(longEmail);
        putFunctionBody(getBytes(newSetting), createdSetting.getId(), correctPin);
    }

    @Test
    void updateSetting_shouldThrow_exception_whenIsEmailActiveIsNull() throws Exception {
        Settings createdSetting = mongoTemplate.insert(createSetting());
        Settings newSetting = createSetting().setIsEmailActive(null);
        putFunctionBody(getBytes(newSetting), createdSetting.getId(), correctPin);

    }
}
