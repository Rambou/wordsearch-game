import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Dictionary {

    // Λίστα που κρατά όλες τις λέξεις
    private List<Word> words;
    private Character[] alphabet = {'Α', 'Β', 'Γ', 'Δ', 'Ε', 'Ζ', 'Η', 'Θ', 'Ι', 'Κ', 'Λ', 'Μ', 'Ν', 'Ξ', 'Ο', 'Π', 'Ρ', 'Σ', 'Τ', 'Υ', 'Φ', 'Χ', 'Ψ', 'Ω'};
    private Random r;

    public Dictionary(File dictionary) throws FileNotFoundException {
        this.r = new Random();
        // αρχικοποίηση λίστας
        this.words = new ArrayList();

        // διαβάζει το αρχείο με τις λέξεις
        Scanner scanner = new Scanner(dictionary);

        // αν βρεί λέξη μέσα στο αρχείο μπορεί να υπάρχουν κενά ή αριθμοί
        // (επιλέγονται μόνο οι λέξεις)
        while (scanner.hasNext()) {
            // Φορτώνει την κάθε λέξει σε μια λίστα για γρηγορότερη εύρεση
            Word w = new Word(scanner.next());
            words.add(w);
        }

        // Ανακατεύει τις λέξεις τυχαία
        Collections.shuffle(words);
    }

    public List<Character> getLetters(Integer num) {
        Integer sum = 0, i = 0;
        List<Character> letters = new ArrayList<>();

        // Επιστρέφει μια λίστα με λέξεις
        while (true) {
            // επιλέγει με την σειρά μια λέξη από την ανακαταμένη
            // λίστα λέξεων και ελέγχει αν το άθροισμα των γραμμάτων
            // της με τις προηγούμενες επιλαχόντες λέξεις είναι ίσο
            // με το σύνολο των γραμμάτων του πίνακα (board)
            // Διαφορετικά βγαίνει από την επανάληψη με break
            if (words.get(i).getLength() + sum <= num) {

                // για κάθε γράμμα της λέξης
                // το προσθέτει στην λίστα με τις γράμματα
                for (int j = 0; j < words.get(i).getLength(); j++) {
                    letters.add(words.get(i).getLetter(j));
                }

                sum += words.get(i).getLength();
                // αφαίρεση λέξης από την λίστα
                this.words.remove(words.get(i++));
            } else {
                break;
            }
        }

        // εάν η λίστα δεν έχει γεμίσει με γράμματα βάζουμε τυχαία κάποια
        // γράμμα από το αλφάβητο μέχρι να γεμίσει
        while (letters.size() < num) {
            letters.add(alphabet[r.nextInt(alphabet.length)]);
        }

        // εδώ ο πίνακας γεμίζει τυχαία αντικαθιστώντας
        // γράμματα με το ? που αντιστιχεί σε μπαλαντέρ
        // επανάληψη με i μέχρι τυχαίο αριθμό από 1 έως 2
        for (i = 0; i < 1 + (int) (Math.random() * 2); i++) {
            // επιλογή τυχαίας θέσης στον πίνακα και
            // αντικατάσταση γράμματος με το '?'
            letters.remove(r.nextInt(letters.size()));
            letters.add('?');
        }

        // ανακάτεμα γραμμάτων
        Collections.shuffle(letters);

        return letters;
    }

    // αφαιρεί τις δοσμένες λέξεις από την λίστα
    public void removeWords(List<Word> words) {
        this.words.removeAll(words);
    }

    public boolean isWord(Word word) {
        for (Word w : words) {
            if (w.getWord().equals(word.getWord())) {
                return true;
            }
        }
        return false;
    }

    public Character getRandomChar() {
        int i = r.nextInt(alphabet.length);
        return alphabet[i];
    }

}
