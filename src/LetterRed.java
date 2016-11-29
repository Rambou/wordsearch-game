public class LetterRed extends Letter {

    public LetterRed(Character letter) {
        super(letter);
    }

    @Override
    int computePoint() {
        return super.getPoint() * 2;
    }

}
