package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.words.domain.Word;

public class Game {
    private int score;

    public Game() {
    }


    public String nextRound(String word) {
        Round round = new Round(word);
        return null;
    }

    public String startGame(String attempt, String word) {
        Round round = new Round(word);
        while (round.getAttempts() < 5) {
            round.guess(attempt);
//            if (
//                    round.getFeedback(attempt).isWordGuessed()) {
//                score = score + 5 * (5 - round.getAttempts()) + 5;
//                return "wow";
//            }
        }
        return "game over";
    }
}
