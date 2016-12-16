import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;

public class Game implements Powers {

    // Ρυθμίσεις
    private static Integer SCORE_TO_WIN, WORDS_TO_MAKE,
            MAX_SWAPS, MAX_ROW_DELETES,
            MAX_TABLE_SHUFFLES, MAX_COLUMN_SHUFFLES,
            MAX_ROW_SHUFFLES;
    // Ιδιότητες
    private Player player;
    private Board board;
    private Dictionary dictionary;
    private Integer boardSize;

    // Αρχικοποίση κλάσης παιχνδιού
    public Game(Integer score_to_win, Integer words_to_make, Integer max_swaps, Integer max_row_deletes,
                Integer max_table_shuffles, Integer max_column_shuffles, Integer max_row_shuffles,
                File DICTIONARY) throws FileNotFoundException {
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
    public void init(String name, Mode mode) throws Exception {
        // αρχικοποίηση του παίκτη
        this.player = new Player();
        player.setName(name);

        // Αρχικοποίηση του board
        switch (mode) {
            case _5x5:
                boardSize = 5;
                break;
            case _8x8:
                boardSize = 8;
                break;
            case _10x10:
                boardSize = 10;
                break;
            default:
                throw new Exception("To mode δεν υποστηρίζεται.");
        }

        board = new Board(boardSize, dictionary);
    }

    public void printBoard() {
        System.out.println(board);
    }

    @Override
    public void swapLetters(Point a, Point b) {
        board.swapLetters(a, b);
    }

    @Override
    public void deleteRow(Integer line) throws Exception {
        board.deleteRow(line);
    }

    @Override
    public void shuffleLetters() {
        board.shuffleLetters();
    }

    @Override
    public void shuffleColumn(int column) throws Exception {
        board.shuffleColumn(column);
    }

    @Override
    public void shuffleRow(int row) throws Exception {
        board.shuffleRow(row);
    }

    @Override
    public void shuffleTable() {
        board.shuffleTable();
    }

    public boolean checkWord(Word w) {
        boolean res = board.checkWord(w);
        System.out.println("Η λέξη " + w.getWord() + ((res) ? " ανήκει" : " δεν ανήκει") + " στις λέξεις.");
        return res;
    }

    public Integer getBoardSize() {
        return boardSize;
    }

    public LinkedHashMap<Point, Letter> getBoardLetters() {
        return board.getLetter();
    }

    enum Mode {
        _5x5, _8x8, _10x10
    }
}
