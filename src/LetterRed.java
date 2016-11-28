public class LetterRed extends Letter {

    public LetterRed(Character letter, int point) {
        super(letter, point);
    }


    @Override
    int computePoint() {
        return super.getPoint() * 2;
    }

}
