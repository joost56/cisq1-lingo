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

    public Game(){}

    public Game(String wordToGuess) {
        Round round = new Round(wordToGuess);
        gameStatus = GameStatus.PLAYING.toString();
        round.setGame(this);
        rounds.add(round);
    }

    public String guess (String attempt, Round round) {
        if (round.getRoundStatus().equals(RoundStatus.IN_PROGRESS.toString())) {
            String guess1 = round.guess(attempt);
            if (guess1.equals("You guessed the word using " + round.getAttempts() + " guess(es)")) {
                score = score + 5 * (5 - round.getAttempts()) + 5;
                gameStatus = GameStatus.WAITING_FOR_ROUND.toString();
                round.setRoundStatus(RoundStatus.COMPLETED.toString());
            }
    } else if (round.getRoundStatus().equals(RoundStatus.FAILED.toString())) {
            round.setAttempts(round.getAttempts() - 1);
            return "deze ronde is klaar nu";
        }else if (round.getRoundStatus().equals(RoundStatus.COMPLETED.toString())){
            round.setAttempts(round.getAttempts() - 1);
            return "deze ronde is klaar nu";
        }
        return round.getPreviousHint();
    }

    public Round startNewRound(String word) {
        if (gameStatus.equals(GameStatus.WAITING_FOR_ROUND.toString())) {
            Round round = new Round(word);
            rounds.add(round);
            setGameStatus(GameStatus.PLAYING.toString());
            round.setGame(this);
        }
        return rounds.get(rounds.size()-1);
    }

    public Integer getNextWordLength(){
        if (rounds.size() == 0) {
            return 5;
        } else if (rounds.get(rounds.size()-1).getWordToGuess().length() == 5) {
            return 6;
        } else if (rounds.get(rounds.size()-1).getWordToGuess().length() == 6) {
            return 7;
        } else {
            return 5;
        }
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
        return Objects.equals(score, game.score) &&
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