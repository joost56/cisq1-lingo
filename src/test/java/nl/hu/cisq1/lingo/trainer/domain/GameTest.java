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
    @ParameterizedTest
    @MethodSource("provideGuessExamples")
    @DisplayName("give guess")
    void guessIsGiven(String attempt, String actualWord, String expectedReturn){
        Game game = new Game();
        game.startNewGame();
        game.startNewRound("woord");
        assertEquals(game.getProgress(), game.guess("woord", game.getRounds().get(0)));
    };

    static Stream<Arguments> provideGuessExamples() {
        return Stream.of(
                Arguments.of("woord", "woord", "Message: Guess again\nScore: 0\nHints: You guessed the word using 1 guess(es)\nRoundnumber: 1"),
                Arguments.of("wonen", "woord", "Message: Guess again\nScore: 0\nHints: [CORRECT, CORRECT, ABSENT, ABSENT, ABSENT]\nwo...\n" + "Roundnumber: 1"),
                Arguments.of("wonen", "woord", "Message: Guess again\nScore: 0\nHints: [CORRECT, CORRECT, ABSENT, ABSENT, ABSENT]\nwo...\n" + "Roundnumber: 1"),
                Arguments.of("wonen", "woord", "Message: Guess again\nScore: 0\nHints: [CORRECT, CORRECT, ABSENT, ABSENT, ABSENT]\nwo...\n" + "Roundnumber: 1"),
                Arguments.of("wonen", "woord", "Message: Guess again\nScore: 0\nHints: [CORRECT, CORRECT, ABSENT, ABSENT, ABSENT]\nwo...\n" + "Roundnumber: 1"),
                Arguments.of("wonen", "woord", "Message: Guess again\nScore: 0\nHints: [CORRECT, CORRECT, ABSENT, ABSENT, ABSENT]\nwo...\n" + "Roundnumber: 1"));
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

    @Test
    public void testHashcodeAndEquals() {
        Game game = new Game();
        Game game1 = new Game();
        assertTrue(game.equals(game1) && game1.equals(game));
        assertTrue(game.hashCode() == game1.hashCode());
    }
}
