package nl.hu.cisq1.lingo.trainer.domain.exception;

public class RoundIdNotFoundException extends RuntimeException{
    public RoundIdNotFoundException(Long msg) {
        super(String.valueOf(msg));
    }
}