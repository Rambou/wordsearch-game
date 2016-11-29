import java.awt.*;
import java.util.HashMap;
import java.util.List;

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
        // λέξεις με γράμματα λιγότερα ή ίσο από το μέγεθος του board
        List<Character> characters = dictionary.getLetters(size * size);
        int i = 0, j = 0;
        // για κάθε γράμμα της λέξης
        for (Character c : characters) {
            table.put(new Point(i, ++j), new LetterClassic(c));
            // αλλάζει γραμμή μόλις η στήλη j φτάσει το μέγεθος του πίνακα
            // και μηδενίζει στήλη και αυξάνει γραμμή
            if (j == size - 1) {
                j = 0;
                i++;
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //table = new RandomLetter();
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
}
