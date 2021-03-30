package nl.hu.cisq1.lingo.trainer.domain;

import com.fasterxml.jackson.annotation.JacksonInject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "progress")
public class Progress {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private int score;
    @Column
    private String hints;
    @Column
    private int roundnumber;
    @Column
    private String message;
    @JoinColumn
    @OneToOne(cascade = CascadeType.ALL)
    private Game game;

    public Progress(){}

    public Progress(int score, String hints, int roundnumber, String message) {
        this.score = score;
        this.hints = hints;
        this.roundnumber = roundnumber;
        this.message = message;
    }

    public void setPrgress(int score, String hints, int roundnumber, String message) {
        this.score = score;
        this.hints = hints;
        this.roundnumber = roundnumber;
        this.message = message;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getHints() {
        return hints;
    }

    public String getMessage() {return message;}

    public int getScore() {
        return score;
    }

    public int getRoundnumber() {
        return roundnumber;
    }

    public void setHints(String hints) {
        this.hints = hints;
    }

    public void setRoundnumber(int roundnumber) {
        this.roundnumber = roundnumber;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message: " + message + "\nScore: " + score + "\nHints: " + hints + "\nRoundnumber: " + roundnumber;
    }
}
