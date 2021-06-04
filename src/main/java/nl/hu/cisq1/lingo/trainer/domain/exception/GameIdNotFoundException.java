package nl.hu.cisq1.lingo.trainer.domain.exception;

public class GameIdNotFoundException extends RuntimeException{
    public GameIdNotFoundException(Long id) {
        super("Could not find id of game " + id);
    }
}