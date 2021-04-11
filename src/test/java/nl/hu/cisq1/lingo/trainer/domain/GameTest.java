package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.presentation.DTO.ProgressDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    @Test
    @DisplayName("give guess")
    void guessIsGiven(){
        Game game = new Game("woord");
        assertEquals("[CORRECT, CORRECT, CORRECT, CORRECT, CORRECT], woord", game.guess("woord", game.getRounds().get(0)));
        assertEquals("This round is done", game.guess("welke", game.getRounds().get(0)));
    };

    @Test
    @DisplayName("No playing possible in finished round")
    void roundIsFinished(){
        Game game = new Game("woord");
        game.guess("woord", game.getRounds().get(0));
        game.guess("woord", game.getRounds().get(0));
        assertEquals("message:You guessed right! Start a new round to continue the game.\nscore:25\nhints:[CORRECT, CORRECT, CORRECT, CORRECT, CORRECT], woord\nroundnumber:1", new ProgressDTO(game).toString());
    }

    @Test
    @DisplayName("Test round is correctly handled when completed")
    void RoundIsCompleted(){
        Game game = new Game("woord");
        game.guess("woord", game.getRounds().get(0));
        assertEquals(25, game.getScore());
        assertEquals(RoundStatus.COMPLETED.toString(), game.getRounds().get(0).getRoundStatus());
        game.guess("werkt", game.getRounds().get(0));
        assertEquals(1, game.getRounds().get(0).getAttempts());
    }

    @Test
    @DisplayName("Starting a new round")
    void NewRoundIsStarted() {
        Game game = new Game("woord");
        game.guess("woord", game.getRounds().get(0));
        game.startNewRound("hoeden");
        assertEquals(GameStatus.PLAYING.toString(), game.getGameStatus());
        assertEquals(game, game.getRounds().get(1).getGame());
        assertEquals(game.getRounds().get(game.getRounds().size()-1) ,game.startNewRound("aalmoes"));
    }

    @Test
    @DisplayName("check length of new word")
    void NewWordLength() {

    }

    @Test
    public void testToString()
    {
        Game game = new Game();
        String expected = "Game{" +
                "id=" + game.getId() +
                ", score=" + game.getScore() +
                ", gameStatus='" + game.getGameStatus() + '\'' +
                ", rounds=" + game.getRounds() +
                '}';
        assertEquals(expected, game.toString());
    }
}