package nl.hu.cisq1.lingo.trainer.presentation.DTO;

import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.RoundStatus;

public class ProgressDTO {
    public int score;
    public String hints;
    public int roundnumber;
    public String message;

    public ProgressDTO(Game game) {
        score = game.getScore();
        hints = game.getRounds().get(game.getRounds().size()-1).getPreviousHint();
        roundnumber = game.getRounds().size();
        message = message(game);
    }

    public String message (Game game) {
        if (game.getGameStatus().equals(GameStatus.ELIMINATED.toString()) && game.getRounds().get(game.getRounds().size() - 1).getRoundStatus().equals(RoundStatus.FAILED.toString())) {
            return "You are eliminated, start a new game.";
        } else if (game.getGameStatus().equals(GameStatus.PLAYING.toString()) && game.getRounds().get(game.getRounds().size() - 1).getRoundStatus().equals(RoundStatus.IN_PROGRESS.toString())) {
            return "Take a wild guess";
        } else if (game.getGameStatus().equals(GameStatus.WAITING_FOR_ROUND.toString()) && game.getRounds().get(game.getRounds().size() - 1).getRoundStatus().equals(RoundStatus.COMPLETED.toString())) {
            return "You guessed right! Start a new round to continue the game.";
        } else if (game.getRounds().get(game.getRounds().size() - 1).getRoundStatus().equals(RoundStatus.FAILEDBYNONEXISTINGWORD.toString())){
            return "This word does not exist! You are eliminated.";
        }
            return "hier hoor je niet te zijn";
    }

    @Override
    public String toString() {
        return "message:" + message + "\nscore:" + score +"\nhints:" + hints +"\nroundnumber:" + roundnumber;
    }
}