package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {
    @Test
    @DisplayName("word is guessed if all letters are correct")
    void wordIsGuessed(){
        Feedback feedback = new Feedback("woord", List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT));
        assertTrue(feedback.isWordGuessed());
    };

    @Test
    @DisplayName("word is guessed if all letters are correct")
    void wordIsNotGuessed(){
        Feedback feedback = new Feedback("woord", List.of(Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT));
        assertFalse(feedback.isWordGuessed());
    };

    @Test
    @DisplayName("word is invalid if number of letters are incorrect or word is nonexistent")
    void guessIsInvalid(){
        Feedback feedback = new Feedback("woord", List.of(Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID));
        assertTrue(feedback.guessIsInvalid());
    };

    @Test
    @DisplayName("word is invalid if number of letters are incorrect or word is nonexistent")
    void guessIsNotInvalid(){
        Feedback feedback = new Feedback("woord", List.of(Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT));
        assertFalse(feedback.guessIsInvalid());
    };

    @ParameterizedTest
    @MethodSource("provideHintExamples")
    @DisplayName("give hint")
    void hintIsGiven(String previousHint, String wordToGuess, List<Mark> mark) {
        Feedback feedback = new Feedback();
        assertEquals("woo.d", feedback.giveHint(previousHint, wordToGuess, mark));
    }

    static Stream<Arguments> provideHintExamples() {
        return Stream.of(
                Arguments.of("woo.d", "woord", List.of(Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT)),
                Arguments.of("woo.d", "woord", List.of(Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT)),
                Arguments.of("woo.d", "woord", List.of(Mark.ABSENT, Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT)),
                Arguments.of("woo.d", "woord", List.of(Mark.ABSENT, Mark.ABSENT, Mark.CORRECT, Mark.ABSENT, Mark.ABSENT)),
                Arguments.of("woo.d", "woord", List.of(Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.CORRECT)),

                Arguments.of("woo.d", "woord", List.of(Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT)),
                Arguments.of(".oo.d", "woord", List.of(Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT)),
                Arguments.of("w.o.d", "woord", List.of(Mark.ABSENT, Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT)),
                Arguments.of("wo..d", "woord", List.of(Mark.ABSENT, Mark.ABSENT, Mark.CORRECT, Mark.ABSENT, Mark.ABSENT)),
                Arguments.of("woo..", "woord", List.of(Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.CORRECT)),

                Arguments.of("....d", "woord", List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.ABSENT, Mark.CORRECT)),
                Arguments.of("woo..", "woord", List.of(Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.CORRECT)),
                Arguments.of("wo...", "woord", List.of(Mark.ABSENT, Mark.ABSENT, Mark.CORRECT, Mark.ABSENT, Mark.CORRECT)),
                Arguments.of("w....", "woord", List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.ABSENT, Mark.CORRECT)),
                Arguments.of("woo.d", "woord", List.of(Mark.ABSENT, Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT)));

    }

//    @Test
//    @DisplayName("word is invalid if number of letters are incorrect or word is nonexistent")
//    void nextRound(){
//        Round round = new Round("woord", 5);
//    };

}