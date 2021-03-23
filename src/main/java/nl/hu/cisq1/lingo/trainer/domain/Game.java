package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "game")
public class Game {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private int score;
    @Column
    private String gameStatus;
    @OneToMany(mappedBy="game", cascade = CascadeType.ALL)
    private List<Round> rounds = new ArrayList<>();
    @Transient
    private List<Round> ronde = new ArrayList<>();

    public Game(){}

    public void startNewGame(){
        setScore(0);
        gameStatus = GameStatus.WAITING_FOR_ROUND.toString();
    }

    public String guess (String attempt, Round round){
        if (gameStatus != GameStatus.ELIMINATED.toString()) {
            String guess = round.guess(attempt);
            if (guess.equals("you reached the limit of your guesses")) {
                gameStatus = GameStatus.ELIMINATED.toString();
            } else if (guess.equals("You guessed the word using " + round.getAttempts() + " guess(es)")) {
                score = score + 5* (5-round.getAttempts()) + 5;
                gameStatus = GameStatus.WAITING_FOR_ROUND.toString();
            }
            return guess;
            }
        return "You have been eliminated, start a new game";
    }

    public String startNewRound(String word){
        Round round = new Round(word);
        String show = round.startRound();
        ronde.add(round);
        setGameStatus(GameStatus.PLAYING.toString());
        return show;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int getScore() {
        return score;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public List<Round> getRonde() {
        return ronde;
    }

    public List<Round> getRounds(){
        return rounds;
    }

    public void setRound(Round round) {
        rounds.add(round);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return score == game.score &&
                Objects.equals(id, game.id) &&
                Objects.equals(gameStatus, game.gameStatus) &&
                Objects.equals(rounds, game.rounds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, score, gameStatus, rounds);
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", score=" + score +
                ", gameStatus='" + gameStatus + '\'' +
                ", rounds=" + rounds +
                '}';
    }
}
