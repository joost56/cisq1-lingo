package nl.hu.cisq1.lingo.words.domain;

import nl.hu.cisq1.lingo.trainer.domain.Round;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordTest {

    @Test
    @DisplayName("length is based on given word")
    void lengthBasedOnWord() {
        Word word = new Word("woord");
        int length = word.getLength();
        assertEquals(5, length);
    }

    @Test
    public void testToString()
    {
        Word word = new Word();
        String expected = word.getValue();
        assertEquals(expected, word.toString());
    }
}
