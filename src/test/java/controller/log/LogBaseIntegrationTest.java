package controller.log;

import com.example.dater.Application;
import com.example.dater.model.Events;
import com.example.dater.model.Logs;
import com.example.dater.model.Settings;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
public
class LogBaseIntegrationTest {
    static {
      new MongoDBContainer(DockerImageName.parse("mongo:4.0.10")).withExposedPorts(27070).start();
    }

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    protected MongoTemplate mongoTemplate;

    protected MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        mongoTemplate.dropCollection(Logs.class);
        mongoTemplate.dropCollection(Events.class);
        mongoTemplate.dropCollection(Settings.class);
    }

    protected byte[] getBytes(Events events) throws JsonProcessingException {
        byte[] content = objectMapper.writeValueAsBytes(events);
        return content;
    }
    protected byte[] getBytes(Logs logs) throws JsonProcessingException {
        byte[] content = objectMapper.writeValueAsBytes(logs);
        return content;
    }

}
