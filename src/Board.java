import java.awt.*;
import java.util.*;
import java.util.List;

public class Board implements Powers {

    private Dictionary dictionary;
    private int size;
    private LinkedHashMap<Point, Letter> table;

    public Board(int size, Dictionary dictionary) {
        this.dictionary = dictionary;
        this.size = size;
        this.table = new LinkedHashMap<>();

        fillTable();
    }

    private void fillTable() {
        // λέξεις με γράμματα λιγότερα γράμματα ή ίσο από το μέγεθος του board
        List<Character> characters = dictionary.getLetters(size * size);
        System.out.println(characters.size());
        int i = 0, j = 0;
        // για κάθε γράμμα της λέξης
        for (Character c : characters) {
            Point p = new Point(i, j++);
            Letter l = new LetterClassic(c);
            table.put(p, l);
            // αλλάζει γραμμή μόλις η στήλη j φτάσει το μέγεθος του πίνακα
            // και μηδενίζει στήλη και αυξάνει γραμμή
            if (j == size) {
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
        //η hashmap δεν έχει standar σειρά, μιας και είναι τύπου Map
        //μπορούμε να περάσουμε τις τιμές της σε μια λίστα και να την
        //κάνουμε shuffle
        //στην συνέχεια μπορούμε να περάσουμε σε κάθε κλειδί(point) το
        //κάθε γράμμα εφόσον δεν μας πειράζει στο ποια θα είναι η αντιστοιχία
        List<Letter> valueList = new ArrayList<>(table.values());
        Collections.shuffle(valueList);
        Iterator<Letter> valueIt = valueList.iterator();
        HashMap<Point,Letter> newMap = new HashMap<Point,Letter>(table.size());
        for(Point p: table.keySet()) {
            newMap.put(p, valueIt.next());
        }
        table = newMap;
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
        // ανακάτεμα γραμμάτων στον πίνακα
        List keys = new ArrayList(table.keySet());
        Collections.shuffle(keys);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int prev = 0; // κρατά την προηγούμενη θέση στον πίνακα στον άξονα x

        // κώδικας για να ψάχνουμε ένα-ένα τα στοιχεία του hashmap
        for (Map.Entry<Point, Letter> entry : table.entrySet()) {
            Point key = entry.getKey();
            Letter value = entry.getValue();

            // ελέγχει αν η προηγούμενη θέση
            // άλλαξε οπότε προσθέτει νέα γραμμή
            if (key.getLocation().x != prev) {
                sb.append("[");
            }

            // Εκτυπώνει το γράμμα
            sb.append(value);

            if (key.getLocation().y == this.size - 1) {
                sb.append("]\n");
            } else {
                sb.append(", ");
            }
            prev = key.getLocation().x;
        }

        return sb.toString();
    }
}
