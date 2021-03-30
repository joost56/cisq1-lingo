package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTest {
//    @Test
//    @DisplayName("give guess")
//    void guessIsGiven(){
//        Game game = new Game();
//        game.startNewGame();
//        game.startNewRound("woord");
//        assertEquals("Message: WAITING_FOR_ROUND\n" +
//                "Score: 25\n" +
//                "Hints: You guessed the word using 1 guess(es)\n" +
//                "Roundnumber: 0", game.guess("woord", game.getRondes().get(0)));
//    };

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

    @Test
    public void testHashcodeAndEquals() {
        Game game = new Game();
        Game game1 = new Game();
        assertTrue(game.equals(game1) && game1.equals(game));
        assertTrue(game.hashCode() == game1.hashCode());
    }
}
