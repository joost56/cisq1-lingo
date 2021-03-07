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
        Feedback feedback1 = new Feedback("woord", List.of(Mark.ABSENT));
        assertFalse(feedback1.isWordGuessed());
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
        Feedback feedback1 = new Feedback("woord", List.of(Mark.ABSENT));
        assertFalse(feedback1.guessIsInvalid());
    };

    @ParameterizedTest
    @MethodSource("provideHintExamples")
    @DisplayName("give hint")
    void hintIsGiven(String previousHint, String wordToGuess, List<Mark> mark, String expectedHint) {
        Feedback feedback = new Feedback();
        assertEquals(expectedHint, feedback.giveHint(previousHint, wordToGuess, mark));
    }

    static Stream<Arguments> provideHintExamples() {
        return Stream.of(
                Arguments.of("....d", "woord", List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.ABSENT, Mark.CORRECT), "woo.d"),
                Arguments.of("woo..", "woord", List.of(Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.CORRECT), "woo.d"),
                Arguments.of("wo...", "woord", List.of(Mark.ABSENT, Mark.ABSENT, Mark.CORRECT, Mark.ABSENT, Mark.CORRECT), "woo.d"),
                Arguments.of("w....", "woord", List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.ABSENT, Mark.CORRECT), "woo.d"),
                Arguments.of("woo.d", "woord", List.of(Mark.ABSENT, Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT), "woo.d"),
                Arguments.of("null", "woord", List.of(Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT), "w...."));
    }

    @ParameterizedTest
    @MethodSource("provideFeedbackExamples")
    @DisplayName("give feedback")
    void feedbackIsGiven(String wordToGuess, String attempt, List<Mark> expectedFeedback){
        Feedback feedback = new Feedback();
        assertEquals(expectedFeedback, feedback.getFeedback(wordToGuess, attempt));
    };

    static Stream<Arguments> provideFeedbackExamples() {
        return Stream.of(
                Arguments.of("woord", "woord", List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT)),
                Arguments.of("woord", "waren", List.of(Mark.CORRECT, Mark.ABSENT, Mark.PRESENT, Mark.ABSENT, Mark.ABSENT)),
                Arguments.of("woord", "woont", List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.ABSENT, Mark.ABSENT)),
                Arguments.of("woord", "woonde", List.of(Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID)));
    }

    @Test
    public void testToString()
    {
        Feedback feedback = new Feedback("woord", List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT));
        String expected = "attempt = woord, marks = [CORRECT, CORRECT, CORRECT, CORRECT, CORRECT]" ;
        assertEquals(expected, feedback.toString());
    }

    @Test
    public void testHashcodeAndEquals() {
        Feedback feedback = new Feedback();
        Feedback feedback1 = new Feedback();
        assertTrue(feedback.equals(feedback1) && feedback1.equals(feedback));
        assertTrue(feedback.hashCode() == feedback1.hashCode());
    }
}