import java.awt.*;

public class LetterBlue extends Letter {

    public LetterBlue(Character letter) {
        super(letter);
        super.setColor(Color.blue);
    }

    @Override
    int computePoint() {
        return super.getPoint() * 2;
    }

}
