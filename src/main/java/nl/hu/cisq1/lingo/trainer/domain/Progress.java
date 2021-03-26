package nl.hu.cisq1.lingo.trainer.domain;

public class Progress {
    private int score;
    private String hints;
    private int roundnumber;

    public Progress(){}

    public Progress(int score, String hints, int roundnumber) {
        this.score = score;
        this.hints = hints;
        this.roundnumber = roundnumber;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getHints() {
        return hints;
    }

    public void setHints(String hints) {
        this.hints = hints;
    }

    public int getRoundnumber() {
        return roundnumber;
    }

    public void setRoundnumber(int roundnumber) {
        this.roundnumber = roundnumber;
    }

    @Override
    public String toString() {
        return "Score: " + score + "\nHints: " + hints + "Roundnumber: " + roundnumber;
    }
}
