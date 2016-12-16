public class LetterJoker extends Letter {

    public LetterJoker(Character letter) {
        super(letter);
    }

    @Override
    public void setLetter(Character letter) {
        super.setLetter(letter);
    }

    @Override
    int computePoint() {
        return super.getPoint();
    }

}
