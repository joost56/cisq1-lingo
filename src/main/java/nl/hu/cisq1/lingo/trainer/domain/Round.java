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
    private String wordToGuess;
    @Column
    private int attempts;
    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;
    @Transient
    private StringBuilder string = new StringBuilder();
    @Column
    private String previousHint;


    public Round (){}

    public Round(String wordToGuess) {
        this.wordToGuess = wordToGuess;
    }

    public Round(String wordToGuess, int attempts) {
        this.wordToGuess = wordToGuess;
        this.attempts = attempts;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public String startRound() {
        string.append(wordToGuess.charAt(0));
        int i = 0;
        while (i < wordToGuess.length() - 1) {
            string.append(".");
            i++;
        }
        previousHint = string.toString();
        return string.toString();
    }

    public String guess(String attempt) {
        if (attempts <= 4) {
            Feedback feedback = new Feedback(attempt);
            if (!attempt.equals(wordToGuess)) {
                attempts = attempts + 1;
                String hint = feedback.giveHint(previousHint, wordToGuess, feedback.getFeedback(wordToGuess, attempt));
                previousHint = hint;
                String marks = feedback.getFeedback(wordToGuess, attempt).toString();
                String totalHint = marks + "\n" + hint;
                return totalHint;
            } else {
                attempts = attempts + 1;
                return "You guessed the word using " + getAttempts() + " guess(es)";
            }
        }
        return "you reached the limit of your guesses";}

    @Override
    public String toString() {
        return "Round{" +
                "wordToGuess='" + wordToGuess + '\'' +
                ", attempts=" + attempts +
                ", previousHint='" + previousHint + '\'' +
                '}';
       }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public void setWordToGuess(String wordToGuess) {
        this.wordToGuess = wordToGuess;
    }

    public String getPreviousHint() {
        return previousHint;
    }

    public void setPreviousHint(String previousHint) {
        this.previousHint = previousHint;
    }
}