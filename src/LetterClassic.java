public class LetterClassic extends Letter {

    public LetterClassic(Character letter) {
        super(letter);
    }

    @Override
    int computePoint() {
        return super.getPoint();
    }

}
