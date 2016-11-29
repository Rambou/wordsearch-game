public class LetterBlue extends Letter {

    public LetterBlue(Character letter) {
        super(letter);
    }

    @Override
    int computePoint() {
        return super.getPoint() * 2;
    }

}
