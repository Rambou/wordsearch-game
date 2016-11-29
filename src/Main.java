import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        // ρυθμίσεις
        String dictionary = "Dictionary";
        int score_to_win = 100;
        int words_to_make = 6;
        int max_swaps = 4;
        int max_row_deletes = 2;
        int max_table_shuffles = 1;
        int max_column_shuffles = 2;
        int max_row_shuffles = 3;

        // Αρχικοποίηση παιχνιδιού και ρυθμίσεων
        Game game = new Game(score_to_win, words_to_make, max_swaps, max_row_deletes, max_table_shuffles, max_column_shuffles, max_row_shuffles, "Dictionary");
        Integer mode; // Είδος παιχνιδιού
        String name; // Όνομα παίκτη

        // Αρχικοποίηση scanner
        Scanner keyboard = new Scanner(System.in);

        // Ζητήται από τον πάικτη το όνομα
        // και το είδος του παιχνιδιού που θέλει να παίξει
        System.out.print("Δώσε όνομα: ");
        name = keyboard.next();

        // Έλεγχος εισόδου, πρέπει να είναι από 1 ή 2 ή 3
        // έλεγχος με regex
        System.out.print("Δώσε είδος παιχνιδιού: ");
        while (!keyboard.hasNext("[1-3]")) {
            System.out.print("Επέλεξε είδος παιχνιδιού απο 1 έως 3: ");
            keyboard.next();
        }
        mode = keyboard.nextInt();

        // Αρχικοποίηση παιχνιδιού
        try {
            // επιλογή του mode
            Game.Mode m;
            switch (mode) {
                case 1:
                    m = Game.Mode._5x5;
                    break;
                case 2:
                    m = Game.Mode._8x8;
                    break;
                default:
                    m = Game.Mode._10x10;
                    break;
            }

            // εκκίνηση
            game.init(name, m);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // κλείσιμο scanner
        keyboard.close();

    }
}
