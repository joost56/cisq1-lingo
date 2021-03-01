package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.words.domain.Word;

public class Round {
    private String wordToGuess;
    private int attempts;
    private Feedback feedback;

    public Round(String wordToGuess, int attempts) {
        this.wordToGuess = wordToGuess;
        this.attempts = attempts;
    }

    public String getWordToGuess() {
        return wordToGuess;
    }

//    public String nextRound(){
//        Word newWord = new Word();
//
//    }
}
