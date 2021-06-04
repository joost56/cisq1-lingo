package nl.hu.cisq1.lingo.trainer.domain.exception;

public class RoundIdNotFoundException extends RuntimeException{
    public RoundIdNotFoundException(Long id) {
        super("Could not find id of round " + id);
    }
}