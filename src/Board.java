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

    private <K, V> void shuffleMap(Map<K, V> map) {
        List<V> valueList = new ArrayList<V>(map.values());
        Collections.shuffle(valueList);
        Iterator<V> valueIt = valueList.iterator();
        for (Map.Entry<K, V> e : map.entrySet()) {
            e.setValue(valueIt.next());
        }
    }

    private <K, V> void shuffleRow(Map<K, V> map, int r) {
        List<V> valueList = new ArrayList<V>();
        for (K key : map.keySet()) {
            Point p = (Point) key;
            if (p.x == r) {
                valueList.add(map.get(key));
            }
        }

        Collections.shuffle(valueList);
        Iterator<V> valueIt = valueList.iterator();
        for (Map.Entry<K, V> e : map.entrySet()) {
            Point p = (Point) e.getKey();
            if (p.x == r) {
                e.setValue(valueIt.next());
            }
        }
    }

    private <K, V> void shuffleColumn(Map<K, V> map, int c) {
        List<V> valueList = new ArrayList<V>();
        for (K key : map.keySet()) {
            Point p = (Point) key;
            if (p.y == c) {
                valueList.add(map.get(key));
            }
        }

        Collections.shuffle(valueList);
        Iterator<V> valueIt = valueList.iterator();
        for (Map.Entry<K, V> e : map.entrySet()) {
            Point p = (Point) e.getKey();
            if (p.y == c) {
                e.setValue(valueIt.next());
            }
        }
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

    public boolean checkWord(Word w) {
        return dictionary.isWord(w);
    }

    @Override
    public void swapLetters(Point a, Point b) {

        // κρατάει τα γράμματα που περιέχονται στις αντίστοιχες θέσεις
        Letter letterA = null, letterB = null;

        // κώδικας για να ψάχνουμε ένα-ένα τα στοιχεία του hashmap
        for (Map.Entry<Point, Letter> entry : table.entrySet()) {
            if (entry.getKey().equals(a)) {
                letterA = entry.getValue();
            } else if (entry.getKey().equals(b)) {
                letterB = entry.getValue();
            }
        }

        // αντικαθιστά τα γράμματα στις αντίστοιχες θέσεις
        table.replace(a, letterB);
        table.replace(b, letterA);
    }

    @Override
    public void deleteRow(Integer row) {
        for (Map.Entry<Point, Letter> entry : table.entrySet()) {
            if (entry.getKey().x == row) {
                // τυχαίο γράμμα
                Letter randLetter = new LetterClassic(dictionary.getRandomChar());
                table.replace(entry.getKey(), randLetter);
            }
        }
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
        LinkedHashMap<Point, Letter> newMap = new LinkedHashMap<>(table.size());
        for (Point p : table.keySet()) {
            newMap.put(p, valueIt.next());
        }
        table = newMap;
    }

    @Override
    public void shuffleColumn(int column) {
        shuffleColumn(table, column);
    }

    @Override
    public void shuffleRow(int row) {
        shuffleRow(table, row);
    }

    @Override
    public void shuffleTable() {
        // ανακάτεμα γραμμάτων στον πίνακα
        shuffleMap(table);
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
