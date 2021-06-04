package nl.hu.cisq1.lingo.trainer.domain;

import java.util.*;

public class Feedback {
    private String attempt;
    List<Mark> marksList;

    public Feedback(String attempt, List<Mark> marks) {
        this.attempt = attempt;
        this.marksList = marks;
    }
    public Feedback(String attempt){
        this.attempt = attempt;
    }
    public Feedback(){}

    public boolean isWordGuessed(){
        int index = 0;
        if (marksList.size() == attempt.length()) {
            while (marksList.get(index) == Mark.CORRECT) {
                index++;
                if (index == marksList.size()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean guessIsInvalid(){
        int index = 0;
        if (attempt.length() == marksList.size()){
            while (marksList.get(index) == Mark.INVALID) {
                index++;
                if (index == marksList.size()) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Mark> makeInvalidFeedback(String attempt){
        int index = 0;
        List<Mark> invalidMarksList = new ArrayList<>();
        while (index < attempt.length()) {
            invalidMarksList.add(Mark.INVALID);
            index++;
        }
        return invalidMarksList;
    }

    public List<Mark> makeFeedback(String wordToGuess, String attempt){
        List<Mark> marks = new ArrayList<>();
        for (int i = 0; i < wordToGuess.length(); i++) {
            Character charOfAttempt = attempt.charAt(i);
            Character charOfWordToGuess = wordToGuess.charAt(i);
            if (charOfAttempt.toString().equals(charOfWordToGuess.toString())) {
                marks.add(Mark.CORRECT);
            } else if (!wordToGuess.contains(charOfAttempt.toString())) {
                marks.add(Mark.ABSENT);
            } else {
                marks.add(Mark.PRESENT);
            }
        }
        marksList = marks;
        return marks;
    }

    public List<Mark> checkForCorrectUseOfPresent (String wordToGuess, String attempt){
        for (int index = 0; index < marksList.size(); index++) {
            int counterWordToGuessPresent = 0;
            int counterAttemptPresent = 0;
            if (marksList.get(index) == Mark.PRESENT) {
                Character letterVanDePresent = attempt.charAt(index);
                for (int i = 0; i < attempt.length(); i++) {
                    if (attempt.charAt(i) == letterVanDePresent) {
                        counterAttemptPresent ++;
                    }
                    if (wordToGuess.charAt(i)  == letterVanDePresent) {
                        counterWordToGuessPresent ++;
                    }
                }
                if (counterAttemptPresent != counterWordToGuessPresent) {
                    marksList.set(index, Mark.ABSENT);
                }
            }
        }
        return marksList;
    }

    public List<Mark> getFeedback (String wordToGuess, String attempt) {
        if (wordToGuess.length() == attempt.length()) {
            makeFeedback(wordToGuess, attempt);
            return checkForCorrectUseOfPresent(wordToGuess, attempt);
        } else {
            return makeInvalidFeedback(attempt);
        }
    }


    public String giveHint(String previousHint, String wordToGuess, List<Mark> marks) {
        StringBuilder hint = new StringBuilder();
        int i = 0;
        while (i < wordToGuess.length()) {
            if (marks.get(i) == Mark.CORRECT) {
                hint.append(wordToGuess.charAt(i));
                i++;
            } else {
                hint.append('.');
                i++;
            }
        }
            StringBuilder theRealHint = new StringBuilder();
            for (int index = 0; index < previousHint.length(); index++) {
                if (previousHint.charAt(index) != '.') {
                    theRealHint.append(previousHint.charAt(index));
                } else if (hint.charAt(index) != '.') {
                    theRealHint.append(hint.charAt(index));
                } else {
                    theRealHint.append('.');
                }
            }
            return theRealHint.toString();
        }
        

    @Override
    public String toString() {
        return "attempt = "+ attempt + ", marks = " + marksList;
    }
}