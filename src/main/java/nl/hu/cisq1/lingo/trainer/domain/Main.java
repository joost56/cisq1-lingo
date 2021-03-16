package nl.hu.cisq1.lingo.trainer.domain;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Round round = new Round("paard");
        System.out.println(round);
        System.out.println(game.guess("pppop", round));
        System.out.println(game.guess("pakke", round));
        System.out.println(game.getGameStatus());
        System.out.println(game.guess("poaaa", round));
        System.out.println(game.guess("ploih", round));
        System.out.println(game.guess("paard", round));
        System.out.println(game.getScore());
        System.out.println(game.guess("paard", round));

    }
}
