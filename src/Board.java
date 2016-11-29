import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board implements Powers {

    private Dictionary dictionary;
    private int size;
    private HashMap<Point, Letter> table;

    public Board(int size, Dictionary dictionary) {
        this.dictionary = dictionary;
        this.size = size;
        this.table = new HashMap<>();

        fillTable();
    }

    private void fillTable() {
        // λέξεις με γράμματα λιγότερα γράμματα ή ίσο από το μέγεθος του board
        List<Character> characters = dictionary.getLetters(size * size);
        int i = 0, j = 0;
        // για κάθε γράμμα της λέξης
        for (Character c : characters) {
            Point p = new Point(i, ++j);
            Letter l = new LetterClassic(c);
            table.put(p, l);
            // αλλάζει γραμμή μόλις η στήλη j φτάσει το μέγεθος του πίνακα
            // και μηδενίζει στήλη και αυξάνει γραμμή
            if (j == size - 1) {
                j = 0;
                i++;
            }
        }
    }

    @Override
    public void swapLetters(Letter a, Letter b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteRow(Integer line) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void shuffleLetters() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void shuffleColumn() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void shuffleRow() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void shuffleTable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // κώδικας για να ψάχνουμε ένα-ένα τα στοιχεία του hashmap
        for (Map.Entry<Point, Letter> entry : table.entrySet()) {
            Point key = entry.getKey();
            Letter value = entry.getValue();

            sb.append(key.getLocation() + " = " + value + "\n");

        }

        return sb.toString();
    }
}
