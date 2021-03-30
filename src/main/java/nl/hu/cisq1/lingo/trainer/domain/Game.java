package nl.hu.cisq1.lingo.trainer.domain;

import javassist.NotFoundException;
import org.hibernate.annotations.Cascade;

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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="FK_ProgressId")
    private Progress progress = new Progress();
    @Transient
    private List<Round> rondes = new ArrayList<>();

    public Game(){}

    public Progress startNewGame(){
        setScore(0);
        gameStatus = GameStatus.WAITING_FOR_ROUND.toString();
        progress = new Progress(0, null, 0, GameStatus.WAITING_FOR_ROUND.toString());
        return progress;
    }

    public Progress guess (String attempt, Round round) {
        String guess = round.guess(attempt);
        progress.setHints(guess);
        progress.setRoundnumber(rounds.size());
        if (guess.equals("you reached the limit of your guesses")) {
            gameStatus = GameStatus.ELIMINATED.toString();
            progress.setMessage(GameStatus.ELIMINATED.toString());
        } else if (guess.equals("You guessed the word using " + round.getAttempts() + " guess(es)")) {
            score = score + 5 * (5 - round.getAttempts()) + 5;
            gameStatus = GameStatus.WAITING_FOR_ROUND.toString();
            progress.setMessage(GameStatus.WAITING_FOR_ROUND.toString());
            progress.setScore(score);
        }
        return progress;
    }

    public Progress startNewRound(String word) {
        Round round = new Round(word);
        round.startRound();
        rondes.add(round);
        setGameStatus(GameStatus.PLAYING.toString());
        progress.setScore(score);
        progress.setHints(round.getPreviousHint());
        progress.setRoundnumber(rounds.size());
        progress.setMessage(GameStatus.PLAYING.toString());
        return progress;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Round> getRounds(){
        return rounds;
    }

    public void setRound(Round round) {
        rounds.add(round);
    }

    public List<Round> getRondes() {
        return rondes;
    }

    public Progress getProgress() {
        return progress;
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
