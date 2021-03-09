package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.words.domain.Word;

import javax.persistence.*;
import java.util.ArrayList;

@Entity(name = "game")
public class Game {
    private int score;
    GameStatus gameStatus;
    private Round round;
    private String id;

    public Game() {
    }

    public String guess (String attempt, Round round){
        String guess = round.guess(attempt);
        if (guess.equals("you reached the limit of your guesses")) {
            gameStatus = GameStatus.ELIMINATED;
        } else if (guess.equals("You guessed the word using " + round.getAttempts() + " guess(es)")) {
            score = score + 5* (5-round.getAttempts()) + 5;
            gameStatus = GameStatus.WAITING_FOR_ROUND;
        }
        return guess;
    }

    public Round startNewRound(String word){
        Round round = new Round(word);
        round.startRound();
        gameStatus = GameStatus.PLAYING;
        return round;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int getScore() {
        return score;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    @OneToOne
    @JoinColumn(name = "rounds_id", nullable = false)
    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }
}
