package nl.hu.cisq1.lingo.trainer.domain;

public class Round {
    private String wordToGuess;
    private int attempts;
    private StringBuilder string = new StringBuilder();
    private Feedback feedback;

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

    public String startRound() {
        string.append(wordToGuess.charAt(0));
        int i = 0;
        while (i < wordToGuess.length() - 1) {
            string.append(".");
            i++;
        }
        return string.toString();
    }

    public String guess(String attempt, String previousHint) {
        if (attempts <= 4) {
            Feedback feedback = new Feedback(attempt);
            if (!attempt.equals(wordToGuess)) {
                attempts = attempts + 1;
                String totalHint = feedback.getFeedback(wordToGuess, attempt).toString() + "\n" + feedback.giveHint(previousHint, wordToGuess, feedback.getFeedback(wordToGuess, attempt));
                return totalHint;
            } else {
                attempts = attempts + 1;
                return "You guessed the word using " + getAttempts() + "guess(es)";
            }
        }
        return "you reached the limit of your guesses";}}