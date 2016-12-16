import java.awt.*;

public class LetterRed extends Letter {

    public LetterRed(Character letter) {
        super(letter);
        super.setColor(Color.red);
    }

    @Override
    int computePoint() {
        return super.getPoint() * 2;
    }

}
