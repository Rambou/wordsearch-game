import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Game {

    // Ρυθμίσεις
    private static Integer SCORE_TO_WIN, WORDS_TO_MAKE,
            MAX_SWAPS, MAX_ROW_DELETES,
            MAX_TABLE_SHUFFLES, MAX_COLUMN_SHUFFLES,
            MAX_ROW_SHUFFLES;
    // Ιδιότητες
    private Player player;
    private Board board;
    private Dictionary dictionary;
    private int score;
    private int no_of_words;
    private ArrayList<Letter> letters;

    // Αρχικοποίση κλάσης παιχνδιού
    public Game(Integer score_to_win, Integer words_to_make, Integer max_swaps, Integer max_row_deletes,
                Integer max_table_shuffles, Integer max_column_shuffles, Integer max_row_shuffles,
                String DICTIONARY) throws FileNotFoundException {
        // θέτει τους κανόνες
        this.SCORE_TO_WIN = score_to_win;
        this.WORDS_TO_MAKE = words_to_make;
        this.MAX_SWAPS = max_swaps;
        this.MAX_ROW_DELETES = max_row_deletes;
        this.MAX_TABLE_SHUFFLES = max_table_shuffles;
        this.MAX_COLUMN_SHUFFLES = max_column_shuffles;
        this.MAX_ROW_SHUFFLES = max_row_shuffles;

        // Αρχικοποιεί το λεξικό με λέξεις που δόθηκαν από κάποιο αρχείο
        dictionary = new Dictionary(DICTIONARY);
    }

    // αρχικοποίηση παιχνιδιού
    public void init(String name, Integer mode) throws Exception {
        // αρχικοποίηση του παίκτη
        this.player = new Player();
        player.setName(name);

        // Αρχικοποίηση του board
        switch (mode) {
            case 1:
                board = new Board(5);
                break;
            case 2:
                board = new Board(8);
                break;
            case 3:
                board = new Board(10);
                break;
            default:
                throw new Exception("Unmet mode. Must be from 1 to 3.");
        }
    }

    enum mode {
        _5x5, _8x8, _10x10
    }

}
