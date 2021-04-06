package nl.hu.cisq1.lingo.trainer.domain;

import javassist.NotFoundException;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
    private Progress progress;


    public Game(){}

    public Progress startNewGame(){
        setScore(0);
        gameStatus = GameStatus.WAITING_FOR_ROUND.toString();
        progress = new Progress(0, null, 0, GameStatus.WAITING_FOR_ROUND.toString());
        return progress;
    }

    public Progress guess (String attempt, Round round) {
        if (round.getRoundStatus().equals(RoundStatus.IN_PROGRESS.toString())) {
            String guess = round.guess(attempt);
            progress.setHints(guess);
            progress.setRoundnumber(rounds.size());
            if (guess.contains("you reached the limit of your guesses")) {
                progress.setMessage(GameStatus.ELIMINATED.toString());
                round.setRoundStatus(RoundStatus.FAILED.toString());
            } else if (guess.equals("You guessed the word using " + round.getAttempts() + " guess(es)")) {
                score = score + 5 * (5 - round.getAttempts()) + 5;
                gameStatus = GameStatus.WAITING_FOR_ROUND.toString();
                progress.setMessage(GameStatus.WAITING_FOR_ROUND.toString());
                progress.setScore(score);
                round.setRoundStatus(RoundStatus.COMPLETED.toString());
            }
    }else if (round.getRoundStatus().equals(RoundStatus.COMPLETED.toString()) || round.getRoundStatus().equals(RoundStatus.FAILED.toString())) {
            progress.setMessage(GameStatus.WAITING_FOR_ROUND.toString());
            round.setAttempts(round.getAttempts() - 1);
            return progress;
        }
        return progress;
    }

    public Progress startNewRound(String word) {
        if (!gameStatus.equals(GameStatus.ELIMINATED.toString())) {
            Round round = new Round(word);
            round.startRound();
            rounds.add(round);
            setGameStatus(GameStatus.PLAYING.toString());
            progress.setScore(score);
            progress.setHints(round.getPreviousHint());
            progress.setRoundnumber(rounds.size());
            progress.setMessage(GameStatus.PLAYING.toString());
            return progress;
        }else {
            return progress;
        }
    }

    public Integer getNextWordLength(){
        if (rounds.size() == 0) {
            return 5;
        }
        else if (rounds.get(rounds.size()-1).getWordToGuess().length() == 5) {
            return 6;
        } else if (rounds.get(rounds.size()-1).getWordToGuess().length() == 6) {
            return 7;
        }else{
            return 5;
        }
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
