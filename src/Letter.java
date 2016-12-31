import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

abstract class Letter extends JButton {
    private static HashMap<Character, Integer> values = new HashMap<>();

    static {
        values.put('Α', 1);
        values.put('Β', 8);
        values.put('Γ', 4);
        values.put('Δ', 4);
        values.put('Ε', 1);
        values.put('Ζ', 8);
        values.put('Η', 1);
        values.put('Θ', 8);
        values.put('Ι', 1);
        values.put('Κ', 2);
        values.put('Λ', 3);
        values.put('Μ', 3);
        values.put('Ν', 1);
        values.put('Ξ', 10);
        values.put('Ο', 1);
        values.put('Π', 2);
        values.put('Ρ', 2);
        values.put('Σ', 1);
        values.put('Τ', 1);
        values.put('Υ', 2);
        values.put('Φ', 8);
        values.put('Χ', 10);
        values.put('Ψ', 10);
        values.put('Ω', 3);
    }

    //Πίνακας αξιών γραμμάτων
    private Character letter;
    private Integer point;
    private Color color;

    // Αρχικοποίηση κλάσης
    public Letter(Character letter) {
        // επιστρέφει τον πόντο της λέξης ανάλογα
        // με το γράμμα του ελληνικού αλφάβητου
        this.point = values.get(letter);
        setColor(Color.white);
        setLetter(letter);
    }

    // Επιστροφή γράμματος
    public Character getLetter() {
        return letter;
    }

    // θέτει το γράμμα
    public void setLetter(Character letter) {
        this.letter = letter;
        this.setText("<html><center><font size=\"6\">" + letter.toString() + "</font><font  size=\"1\" color=#5858FA>" + ((point != null) ? point : "") + "</font>");
    }

    // Επιστροφή πόντου
    public int getPoint() {
        return point;
    }

    // Υπολογισμός πόντου
    abstract int computePoint();

    public boolean isSelected() {
        // αν το χρώμα του κουμπίου δεν είναι το αρχικό του
        // τότε θεωρούμε ότι έχει επιλεχθεί
        return !getBackground().equals(color);
    }

    public void select() {
        if (isSelected()) {
            setBackground(getColor());
        } else {
            setBackground(Color.yellow);
        }
    }

    public void diselect() {
        setBackground(getColor());
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        super.setBackground(color);
    }

    @Override
    public String toString() {
        return getLetter().toString();
    }
}
