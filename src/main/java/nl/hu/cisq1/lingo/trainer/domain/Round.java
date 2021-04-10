package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.*;

@Entity
@Table(name = "rounds")
public class Round {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String roundStatus;
    @Column
    private String wordToGuess;
    @Column
    private int attempts;
    @Column
    private String previousHint;
    @ManyToOne
    @JoinColumn(name ="FK_GameId")
    private Game game;
    @Transient
    private StringBuilder string = new StringBuilder();

    public Round (){}

    public Round(String wordToGuess) {
        this.wordToGuess = wordToGuess;
        startRound();
    }

    public Round(String wordToGuess, int attempts) {
        this.wordToGuess = wordToGuess;
        this.attempts = attempts;
    }

    public int getAttempts() {
        return attempts;
    }

    public String startRound() {
        string.append(wordToGuess.charAt(0));
        int i = 0;
        while (i < wordToGuess.length() - 1) {
            string.append(".");
            i++;
        }
        previousHint = string.toString();
        roundStatus = RoundStatus.IN_PROGRESS.toString();
        return string.toString();
    }

    public String guess(String attempt) {
            if (attempts <= 4) {
                Feedback feedback = new Feedback(attempt);
                if (!attempt.equals(wordToGuess)) {
                    attempts = attempts + 1;
                    String hint = feedback.giveHint(previousHint, wordToGuess, feedback.getFeedback(wordToGuess, attempt));
                    String marks = feedback.getFeedback(wordToGuess, attempt).toString();
                    String totalHint = marks + ", " + hint;
                    if (marks.contains(Mark.INVALID)){
                        previousHint = marks + ", " + wordToGuess.charAt(0) + "....";
                    } else {
                        previousHint = hint;
                    }
                    if (attempts == 5) {
                        roundStatus = RoundStatus.FAILED.toString();
                        game.setGameStatus(GameStatus.ELIMINATED.toString());
                        return totalHint + ", you reached the limit of your guesses";
                    }
                    return totalHint;
                } else {
                    String marks = feedback.getFeedback(wordToGuess, attempt).toString();
                    previousHint = marks + ", " + attempt;
                    attempts = attempts + 1;
                    roundStatus = RoundStatus.COMPLETED.toString();
                    return "You guessed the word using " + getAttempts() + " guess(es)";
                }
            }
            roundStatus = RoundStatus.FAILED.toString();
            return "you reached the limit of your guesses";
        }

    @Override
    public String toString() {
        return "Round{" +
                "wordToGuess='" + wordToGuess + '\'' +
                ", attempts=" + attempts +
                ", previousHint='" + previousHint + '\'' +
                '}';
       }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getWordToGuess() {
        return wordToGuess;
    }

    public String getPreviousHint() {
        return previousHint;
    }

    public void setAttempts(int attempts) {this.attempts = attempts;}

    public String getRoundStatus() {
        return roundStatus;
    }

    public void setRoundStatus(String roundStatus) {this.roundStatus = roundStatus;}

    public StringBuilder getString() {
        return string;
    }
}