package nl.hu.cisq1.lingo.trainer.domain;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Feedback feedback = new Feedback("POEP", List.of(Mark.INVALID, Mark.INVALID,Mark.INVALID,Mark.INVALID));
        System.out.println(feedback.guessIsInvalid());
    }
}
