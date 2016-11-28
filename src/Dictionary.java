import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Dictionary {

    // Λίστα που κρατά όλες τις λέξεις
    private List<Word> words;

    public Dictionary(String dictionary) throws FileNotFoundException {
        // αρχικοποίηση λίστας
        this.words = new ArrayList();

        // διαβάζει το αρχείο με τις λέξεις
        Scanner scanner = new Scanner(new File(dictionary));

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

    public List getWords(Integer num) {
        // Επιστρέφει μια λίστα με λέξεις


        return words.subList(0, num);
    }

    // αφαιρεί τις δοσμένες λέξεις από την λίστα
    public void removeWords(List<Word> words){
        this.words.removeAll(words);
    }
}
