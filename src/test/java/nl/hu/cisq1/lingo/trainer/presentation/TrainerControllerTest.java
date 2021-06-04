package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.CiTestConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Import(CiTestConfiguration.class)
@AutoConfigureMockMvc
public class TrainerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("start a new game")
    void startNewGame()throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/lingo/startGame");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.score", greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.attempt", is(0)))
                .andExpect(jsonPath("$.roundnumber", is(1)))
                .andExpect(jsonPath("$.message", is("Take a wild guess")));
    }

    @Test
    @DisplayName("start a new round")
    void startNewRound()throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/lingo/startRound/1");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.score", greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.attempt", is(0)))
                .andExpect(jsonPath("$.roundnumber", greaterThanOrEqualTo(1)));
    }
}
