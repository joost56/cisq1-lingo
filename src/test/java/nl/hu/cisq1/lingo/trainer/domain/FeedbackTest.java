package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

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

}