package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.words.application.WordService;
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

//    @Test
//    @DisplayName("A new round is started when a new game is started")
//    void providesNewGame() {
//        Progress progress = service.startNewGame();
//        assertEquals(GameStatus.PLAYING.toString(), progress.getMessage());
//        assertEquals(0, progress.getScore());
//        assertEquals(5, progress.getHints().length());
//        assertEquals(1, progress.getRoundnumber());
//    }

    @Test
    @DisplayName("provides new round")
    void providesNewRound() {

    }

    @Test
    @DisplayName("provides new guess")
    void providesNewGuess() {

    }


}