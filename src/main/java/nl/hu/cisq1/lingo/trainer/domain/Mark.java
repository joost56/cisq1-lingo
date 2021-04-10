package nl.hu.cisq1.lingo.trainer.domain;

public enum Mark implements CharSequence {
    CORRECT,
    ABSENT,
    PRESENT,
    INVALID;

    @Override
    public int length() {
        return 0;
    }

    @Override
    public char charAt(int index) {
        return 0;
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return null;
    }
}