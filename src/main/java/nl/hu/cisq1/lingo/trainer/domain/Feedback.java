package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Feedback {
    private String attempt;
    List<Mark> mark;

    public Feedback(String attempt, List<nl.hu.cisq1.lingo.trainer.domain.Mark> mark) {
        this.attempt = attempt;
        this.mark = mark;
    }

//    public List<Character> attemptSplitter(){
//        List<Character> letterByLetter = new ArrayList<>();
//
//            letterByLetter.add(attempt.charAt(i));
//            i++;
//        }
//        return letterByLetter;
//    }

    public boolean isWordGuessed(){
        int index = 0;
        if (mark.size() == attempt.length()) {
            while (mark.get(index) == Mark.CORRECT){
                index++;
                if (index == mark.size()){
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public boolean guessIsInvalid(){
        int index = 0;
        if (attempt.length() == mark.size()){
            while (mark.get(index) == Mark.INVALID) {
                index++;
                if (index == mark.size()) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(attempt, feedback.attempt) &&
                Objects.equals(mark, feedback.mark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attempt, mark);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "attempt='" + attempt + '\'' +
                ", mark=" + mark +
                '}';
    }
}
