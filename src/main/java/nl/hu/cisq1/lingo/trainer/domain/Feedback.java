package nl.hu.cisq1.lingo.trainer.domain;

import java.util.*;

public class Feedback {
    private String attempt;
    List<Mark> marks;

    public Feedback(String attempt, List<Mark> marks) {
        this.attempt = attempt;
        this.marks = marks;
    }
    public Feedback(String attempt){
        this.attempt = attempt;
    }
    public Feedback(){}

    public boolean isWordGuessed(){
        int index = 0;
        if (marks.size() == attempt.length()) {
            while (marks.get(index) == Mark.CORRECT){
                index++;
                if (index == marks.size()){
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public boolean guessIsInvalid(){
        int index = 0;
        if (attempt.length() == marks.size()){
            while (marks.get(index) == Mark.INVALID) {
                index++;
                if (index == marks.size()) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public List<Mark> getFeedback (String wordToGuess, String attempt) {
        List<Mark> marks = new ArrayList<>();
        if (wordToGuess.length() == attempt.length()) {
            for(int i = 0; i < wordToGuess.length(); i++) {
                Character kar = attempt.charAt(i);
                Character kara = wordToGuess.charAt(i);
                if (kar.toString().equals(kara.toString())) {
                    marks.add(Mark.CORRECT);
                } else if (!wordToGuess.contains(kar.toString())) {
                    marks.add(Mark.ABSENT);
                } else {
                    marks.add(Mark.PRESENT);
                }
            }
        }
        else {
            //invalid
            int index = 0;
            List<Mark> invalidMarksList = new ArrayList<>();
            while (index < attempt.length()) {
                invalidMarksList.add(Mark.INVALID);
                index++;
            }
            return invalidMarksList;
            }
            return marks;
    }


    public String giveHint(String previousHint, String wordToGuess, List<Mark> marks) {
        StringBuilder stringbuilder = new StringBuilder();
        int i = 0;
        while (i < wordToGuess.length()) {
            if (marks.get(i) == Mark.CORRECT) {
                stringbuilder.append(wordToGuess.charAt(i));
                i++;
            } else {
                stringbuilder.append('.');
                i++;
            }
        }
        if (previousHint.equals("null")) {
            return stringbuilder.toString();
        }
            StringBuilder theRealHint = new StringBuilder();
            for (int index = 0; index < previousHint.length(); index++) {
                if (previousHint.charAt(index) != '.') {
                    theRealHint.append(previousHint.charAt(index));
                } else if (stringbuilder.charAt(index) != '.') {
                    theRealHint.append(stringbuilder.charAt(index));
                } else {
                    theRealHint.append('.');
                }
            }

            return theRealHint.toString();
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(attempt, feedback.attempt) &&
                Objects.equals(marks, feedback.marks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attempt, marks);
    }

    @Override
    public String toString() {
        return "attempt = "+ attempt + ", marks = " + marks;
    }
}
