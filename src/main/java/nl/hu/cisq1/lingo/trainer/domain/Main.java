package nl.hu.cisq1.lingo.trainer.domain;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.startNewRound("paard");
        Round round = game.getRonde().get(0);
        System.out.println(game.guess("pasar", round));
        System.out.println(game.guess("parwa", round));
        System.out.println(game.guess("party", round));
        System.out.println(game.guess("pasja", round));
        System.out.println(game.guess("passe", round));
    }
}
