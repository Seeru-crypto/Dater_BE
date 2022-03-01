package controller.settings;

import com.example.dater.model.Settings;
import controller.BaseIntegrationTest2;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static controller.TestObjects.createSetting;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SettingControllerIntegrationTest extends BaseIntegrationTest2{

    @Test
    void shouldUpdateSettings() throws Exception {
        Settings createdSetting = mongoTemplate.insert(createSetting());

        String path = "/api/settings/" + createdSetting.getId();
        Settings updatedSettings = new Settings()
                .setIsEmailActive(true)
                .setEmailAddress("newEmail@gmail.com");

        mockMvc.perform(put(path).param("pin","154878")
                .content(getBytes(updatedSettings))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isEmailActive").value(true))
                .andExpect(jsonPath("$.emailAddress").value("newEmail@gmail.com"));
    }

    @Test
    void shouldGetSettings() throws Exception {
        mongoTemplate.insert(createSetting());
        mockMvc.perform(get("/api/settings").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(1))
                .andExpect(jsonPath("$.[0].isEmailActive").value(false))
                .andExpect(jsonPath("$.[0].emailAddress").value("email@gmail.com"));
    }
}
