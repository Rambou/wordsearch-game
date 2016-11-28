public class LetterClassic extends Letter {

    public LetterClassic(Character letter, int point) {
        super(letter, point);
    }

    @Override
    int computePoint() {
        return super.getPoint();
    }

}
