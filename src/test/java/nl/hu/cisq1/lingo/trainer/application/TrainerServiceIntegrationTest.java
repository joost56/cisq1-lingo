package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.presentation.DTO.ProgressDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(CiTestConfiguration.class)
class TrainerServiceIntegrationTest {

    @Autowired
    private TrainerService service;

    @Test
    @DisplayName("A new round is started when a new game is started")
    void providesNewGame() {
        ProgressDTO progress = service.startNewGame();
        assertEquals("Take a wild guess", progress.message);
        assertEquals(0, progress.score);
        assertEquals(5, progress.hints.length());
        assertEquals(1, progress.roundnumber);
    }
}