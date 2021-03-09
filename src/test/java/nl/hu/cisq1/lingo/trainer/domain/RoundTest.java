package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    @Test
    @DisplayName("Start round")
    void roundIsStarted(){
        Round round = new Round("woord");
        assertEquals(round.startRound(), "w....");
    };

//    @Test
//    @DisplayName("guess")
//    void guess(){
//        Round round = new Round("woord");
//        assertEquals("w....", round.guess("welke", "w...."));
//    }

    @ParameterizedTest
    @MethodSource("provideguessExamples")
    @DisplayName("give feedback")
    void feedbackIsGiven(String attempt, String expectedFeedback){
        Round round = new Round("woord", 0);
        round.startRound();
        assertEquals(expectedFeedback, round.guess(attempt));
    }

    static Stream<Arguments> provideguessExamples() {
        return Stream.of(
                Arguments.of("woont", List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.ABSENT, Mark.ABSENT) + "\n" + "woo.." ),
                Arguments.of("worms", List.of(Mark.CORRECT, Mark.CORRECT, Mark.PRESENT, Mark.ABSENT, Mark.ABSENT) + "\n" + "wo..."),
                Arguments.of("woord", "You guessed the word using " + 1 + " guess(es)"));
    }

    @Test
    @DisplayName("attempt limit reached")
    void attemptsHigherThenFive(){
        Round round = new Round("woord", 5);
        assertEquals("you reached the limit of your guesses", round.guess("woont"));
    };

    @Test
    @DisplayName("getAttempts")
    void getAttempts() {
        Round round = new Round("woord");
        assertEquals(0, round.getAttempts());
    }
}