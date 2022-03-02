package controller.log;

import controller.settings.SettingBaseIntegrationTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LogControllerIntegrationTestSetting extends SettingBaseIntegrationTest {

    @Test
    void shoulSaveNewLog() throws Exception {
        // add new event
        // check if system creates new log because of that!
        // check all fields if possible
    }

    @Test
    void shouldGetLogs() throws Exception {
    }
}
