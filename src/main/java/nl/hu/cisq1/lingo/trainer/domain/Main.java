package nl.hu.cisq1.lingo.trainer.domain;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.startNewGame();
        game.startNewRound("baard");
//        System.out.println(game.guess("baard", game.getRonde().get(0)));
    }


}
