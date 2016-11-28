public class LetterBlue extends Letter {

    public LetterBlue(Character letter, int point) {
        super(letter, point);
    }

    @Override
    int computePoint() {
        return super.getPoint() * 2;
    }

}
