package nl.hu.cisq1.lingo.trainer.domain.exception;

public class GameIdNotFoundException extends RuntimeException{
    public GameIdNotFoundException(Long msg) {
        super(String.valueOf(msg));
    }
}
