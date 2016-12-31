import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class MainGUI {

    // μεταβλητές παιχνιδιού
    private Game game;
    private String name = "noPlayer";
    private Game.Mode m = Game.Mode._5x5;
    private LinkedHashMap<Point, Letter> boardButtons;
    private LinkedList<Letter> WordQueue;
    // γραφικά στοιχεία
    private JMenuBar menuBar;
    private JMenu menu, tools;
    private JMenuItem newGame, endGame, addPlayerInformation, helpSettings, addDictionary, exit, help, about;
    private JLabel deleteRowUsed, swapLettersUsed, shuffleColumnUsed, shuffleRowUsed, shuffleTableUsed, goal, score, wordPoints, wordsFound;
    private JFrame jframe, settingsFrame;
    private JButton saveSettings, deleteRow, swapLetters, shuffleColumn, shuffleRow, shuffleTable, checkWord;
    private JTextField score_to_win, words_to_make, max_swaps, max_row_deletes, max_table_shuffles, max_column_shuffles, max_row_shuffles,
            deleteRow_txt, shuffleRow_txt, shuffleColumn_txt;
    private JComboBox modeSelect;
    private JPanel boardPanel;
    // μεταβλητές συστήματος
    private Integer SCORE = 100;
    private Integer WORDS = 6;
    private Integer SWAPS = 4;
    private Integer DELETES = 2;
    private Integer SHUFFLES = 2;
    private Integer COL_SHUFFLES = 2;
    private Integer ROW_SHUFFLES = 3;
    private Integer GAME_MODE = 1;
    private File dictionary = new File("Dictionary");

    public MainGUI() {
        // Αρχικοποίηση παιχνιδιού και ρυθμίσεων
        try {
            game = new Game(SCORE, WORDS, SWAPS, DELETES, SHUFFLES, COL_SHUFFLES, ROW_SHUFFLES, dictionary);
            game.init(name, m);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Το αρχείο λεξικού δεν βρέθηκε", "Πρόβλημα!", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "Πρόβλημα!", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

        initializeGUI();

        WordQueue = new LinkedList<>();
    }

    public boolean isAnySelected() {
        // έλεγχος αν υπάρχουν άλλα επιλεγμένα κουμπιά στο ταμπλό
        for (Letter l : boardButtons.values()) {
            if (l.isSelected()) {
                return true;
            }
        }

        return false;
    }

    public void checkWord_clicked(ActionEvent e) {
        // έλεγχος αν έχει επιλεγεί λέξη με τουλάχιστον 3 χαρακτήρες
        if (WordQueue.size() < 3) {
            JOptionPane.showMessageDialog(null, "Παρακαλώ φτιάξε λέξη τουλάχιστον τριών χαρακτήρων.", "Λάθος", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // χτίσιμο λέξης
        StringBuilder word = new StringBuilder();
        Integer points = 0;
        for (Letter l : WordQueue) {
            word.append(l.getLetter());
            points += l.computePoint();
        }

        // έλεγχος λέξης
        if (game.checkWord(new Word(word.toString()))) {
            JOptionPane.showMessageDialog(null, "Σωστά, βρήκες την λέξη " + word + "!", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
            // ενημέρωση των λέξεων που βρέθηκαν
            Integer i = Integer.parseInt(wordsFound.getText().split("/")[0]);
            wordsFound.setText(++i + "/" + WORDS);
            wordPoints.setText(points.toString());
            points += Integer.parseInt(score.getText());
            score.setText(points.toString());

            // καθαρίζει τα γράμματα από την μνήμη
            WordQueue.removeAll(WordQueue);

            // έλεγχος αν κέρδισε το παιχνίδι
            if (points >= SCORE) {
                JOptionPane.showMessageDialog(null, "Κέρδισες το παιχνίδι.", "Νίκησες!!!", JOptionPane.INFORMATION_MESSAGE);
                newGame_menu_clicked(e);
                return;
            }

            // έλεγχος αν τελείωσαν οι λέξεις
            if (i >= WORDS) {
                JOptionPane.showMessageDialog(null, "Βρήκες και τις " + WORDS.toString() + " λέξεις και συγκέντρωσες " + score.getText() + " πόντους από τους " + SCORE + ".", "Τέλος παιχνιδιού, έχασες!", JOptionPane.INFORMATION_MESSAGE);
                newGame_menu_clicked(e);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Αποτυχία, η λέξη " + word + " δεν υπάρχει.", "Λάθος", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void boardLetter_clicked(ActionEvent e) {
        Letter letter = ((Letter) e.getSource());

        // έλεγχος του τύπου γράμματος
        if (letter instanceof LetterBlue || letter instanceof LetterClassic || letter instanceof LetterRed) {

            // αν υπάρχει έστω και ένα επιλεγμένο κουμπί στο ταμπλό γίνεται έλεγχος γειτονικών
            // διαφορετικά θέτουμε το κουμπί ως επιλεγμένο
            if (isAnySelected()) {
                Boolean b = letter.isSelected() && WordQueue.getLast().equals(letter);
                // αν είναι επιλεγμένο το απεπιλέγουμε
                if (letter.isSelected()) {
                    // αν είναι το τελευαταίο γράμμα
                    if (WordQueue.getLast().equals(letter)) {
                        letter.select();
                        WordQueue.removeLast();
                    }

                } else { // αλλιώς έλεγχος γειτονικών
                    // εύρεση του σημείου του κουμπιού
                    Point point = null;
                    for (Map.Entry l : boardButtons.entrySet()) {
                        if (l.getValue().equals(letter)) {
                            point = (Point) l.getKey();
                        }
                    }

                    // έλεγχος γειτονικών
                    for (Map.Entry l : boardButtons.entrySet()) {
                        // αν το κουμπί είναι επιλεγμένο
                        if (((Letter) l.getValue()).isSelected()) {
                            // έλεγχος αν είναι γειτονικό
                            Point p = (Point) l.getKey();
                            double t = point.distance(p);
                            if (t < 2) {
                                letter.select();
                                WordQueue.add(letter);
                                return;
                            }
                        }

                    }
                }
            } else { // μπαίνει αν είναι το πρώτο γράμμα
                letter.select();

                if (letter.isSelected()) {
                    WordQueue.add(letter);
                } else {
                    WordQueue.removeLast();
                }
            }

        } else if (letter instanceof LetterJoker) {
            // Ζητήται το γράμμα που θέλει να τοποθετηθεί
            JComboBox list = new JComboBox(new Character[]{'Α', 'Β', 'Γ', 'Δ', 'Ε', 'Ζ', 'Η', 'Θ', 'Ι', 'Κ', 'Λ', 'Μ', 'Ν', 'Ξ', 'Ο', 'Π', 'Ρ', 'Σ', 'Τ', 'Υ', 'Φ', 'Χ', 'Ψ', 'Ω'});
            list.setEditable(true);
            JOptionPane.showMessageDialog(null, list, "Επίλεξε ένα γράμμα", JOptionPane.OK_CANCEL_OPTION);
            System.out.println("Ο χρήστης επίλεξε το μπαλαντέρ να γίνει " + (Character) list.getSelectedItem());

            // βρίσκουμε το κουμπί και το αντικαθιστούμε με την επιλογή του χρήστη
            for (Map.Entry f : boardButtons.entrySet()) {
                if (f.getValue().equals(letter)) {
                    System.out.println(f.getKey());
                    f.setValue(new LetterClassic((Character) list.getSelectedItem()));
                }
            }

            // φορτώνεις το καινούργιο ταμπλό
            loadBoard();
        }
    }

    public void newGame_menu_clicked(ActionEvent e) {
        // Αρχικοποίηση παιχνιδιού
        try {
            // επιλογή του mode
            switch (GAME_MODE) {
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
            jframe.dispose();
            initializeGUI();

            // εκτύπωση του board
            System.out.println("Αρχικός πίνακας");
            game.printBoard();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "Πρόβλημα!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void endGame_menu_clicked(ActionEvent e) {

    }

    public void addPlayerInformation_menu_clicked(ActionEvent e) {
        // Ζητήται από τον πάικτη το όνομα του
        name = JOptionPane.showInputDialog("Εισήγαγε το ονοματεπώνυμο του παίκτη: ", "όνομα");
    }

    public void helpSettings_menu_clicked(ActionEvent e) {
        // έλεγξε αν το παράθυρο είναι ήδη ανοιχτό και κλείστο
        if (settingsFrame != null) {
            if (settingsFrame.isShowing()) {
                settingsFrame.dispose();
            }
        }

        // δημιουργία του JFrame που θα κρατά το καινούργιο παράθυρο
        settingsFrame = new JFrame("Ρυθμίσεις");
        settingsFrame.setSize(250, 300);
        settingsFrame.setResizable(false);

        // δημιουργία του πάνελ που θα περιέχονται
        // τα πεδία εισαγωγής των ρυθμίσεων και οι αντίστοιχες
        // ετικέτες τους
        JPanel settings = new JPanel();
        settings.setLayout(new BoxLayout(settings, BoxLayout.PAGE_AXIS));
        settings.add(new JLabel("Μέγιστο σκόρ νίκης"));
        settings.add(score_to_win = new JTextField(SCORE.toString()));
        settings.add(new JLabel("Λέξεις που πρέπει να φτιάξει"));
        settings.add(words_to_make = new JTextField(WORDS.toString()));
        settings.add(new JLabel("Βοήθειες εναλλαγής"));
        settings.add(max_swaps = new JTextField(SWAPS.toString()));
        settings.add(new JLabel("Βοήθειες διαγραφής"));
        settings.add(max_row_deletes = new JTextField(DELETES.toString()));
        settings.add(new JLabel("Βοήθειες ανακατέματος πίνακα"));
        settings.add(max_table_shuffles = new JTextField(SHUFFLES.toString()));
        settings.add(new JLabel("Βοήθειες ανακατέματος στήλης"));
        settings.add(max_column_shuffles = new JTextField(COL_SHUFFLES.toString()));
        settings.add(new JLabel("Βοήθειες ανακατέματος γραμμής"));
        settings.add(max_row_shuffles = new JTextField(ROW_SHUFFLES.toString()));
        settings.add(new JLabel("Μέγεθος board"));
        String modes[] = {"5x5", "8x8", "10x10"};
        settings.add(modeSelect = new JComboBox(modes));
        modeSelect.setSelectedIndex(GAME_MODE - 1);

        saveSettings = new JButton("Αποθήκευση");
        saveSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                saveSettings_clicked(e, settingsFrame);
            }
        });
        settings.add(saveSettings, BorderLayout.CENTER);

        // προσθήκη του πάνελ στο frame
        settingsFrame.add(settings);

        // κώδικας για τοποθέτηση του frame στο κέντρο της οθόνης
        // χρησιμοποιείτε πριν το pack() σύμφωνα με το javaDoc
        settingsFrame.setLocationRelativeTo(null);

        // θέτουμε το frame ορατό
        settingsFrame.setVisible(true);
    }

    public void addDictionary_menu_clicked(ActionEvent e) {
        // δημιουργία του file chooser
        JFileChooser chooser = new JFileChooser();
        // τιμή που κρατά την επιλογή του χρήστη
        // π.χ. αν επέλεξε αρχείο ή πάτησε ακύρωση
        int returnVal = chooser.showOpenDialog(jframe);
        // αν επιλέχθηκε κάποιο αρχείο τότε διαβάζει
        // την λίστα των λέξεων
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Επιλέχθηκε το αρχείο: " +
                    chooser.getSelectedFile().getName());
            dictionary = chooser.getSelectedFile();
        }
    }

    public void exit_menu_clicked(ActionEvent e) {
        // κλήση για έξοδο του προγράμματος
        System.exit(0);
    }

    public void help_menu_clicked(ActionEvent e) {
        String developers = "<html><body><div width='500px' align='left'>Σε κάθε γράμμα του Ελληνικού αλφαβήτου που είναι τοποθετημένο στο κεντρικό πάνελ\n" +
                "αναγράφεται ένας ακέραιος θετικός αριθμός που αντιστοιχεί στους πόντους που θα λάβει ο\n" +
                "παίκτης αν χρησιμοποιήσει το συγκεκριμένο γράμμα για την δημιουργία μιας λέξης.\n" +
                "<br><br>\n" +
                "Έτσι, αν ο παίκτης επιλέξει και τα 2 αυτά γράμματα για την διαμόρφωση της λέξης του θα\n" +
                "έχει συγκεντρώσει τουλάχιστον 12 πόντους. Ο παίκτης κερδίζει στο παιχνίδι όταν θα έχει\n" +
                "καταφέρει να συγκεντρώσει ένα συγκεκριμένο αριθμό πόντων καθορισμένο από το παιχνίδι,\n" +
                "δημιουργώντας ένα προκαθορισμένο πλήθος λέξεων. Ο αριθμός των πόντων που πρέπει ο\n" +
                "παίκτης να συγκεντρώσει και το πλήθος των λέξεων είναι δική σας επιλογή βάση της λογικής\n" +
                "που θεωρείτε καλύτερη για το παιχνίδι (τυχαία ή με βάση κάποιο κριτήριο).\n" +
                "<br><br>\n" +
                "Το παιχνίδι ολοκληρώνεται με αποτυχία σε περίπτωση που ο χρήστης δημιουργήσει τον\n" +
                "συγκεκριμένο αριθμό λέξεων που ζητείται από το παιχνίδι αλλά δεν έχει καταφέρει να\n" +
                "συγκεντρώσει το επιθυμητό αριθμό των πόντων. Αν ο παίκτης κατά την εξέλιξη του\n" +
                "παιχνιδιού θεωρήσει ότι δεν μπορεί να δημιουργήσει το σύνολο των λέξεων που πρέπει,\n" +
                "μπορεί είτε να τερματίσει το παιχνίδι είτε να ζητήσει επανεκκίνηση του παιχνιδιού. Στη\n" +
                "δεύτερη περίπτωση θα πρέπει να γίνεται επαν-αρχικοποίηση όλων των παραμέτρων του\n" +
                "παιχνιδιού και επαναδημιουργία του κεντρικού πάνελ.<br><br>" +
                "Ο παίκτης κατά την εξέλιξη του παιχνιδιού θα πρέπει να έχει στη διάθεση του τρείς\n" +
                "βοηθητικές επιλογές :<br>\n" +
                "<ul>\n" +
                "<li><b>Τη δυνατότητα να ανταλλάσσει 2 γράμματα</b> της επιλογής του. Χρησιμοποιώντας\n" +
                "αυτή τη βοήθεια ο χρήστης θα επιλέγει με το ποντίκι το 1ο και το 2ο γράμμα και στη\n" +
                "συνέχεια το πρόγραμμα θα ανταλλάσσει τη θέση τους στο ταμπλό.</li>\n" +
                "<li><b>Διαγραφή γραμμής.</b> Ο χρήστης θα μπορεί να επιλέγει με το ποντίκι ή με είσοδο\n" +
                "αριθμού γραμμής και η εφαρμογή θα διαγράφει τη ζητούμενη γραμμή και θα την\n" +
                "αντικαθιστά με νέα γράμματα. Όλες οι άλλες γραμμές του ταμπλό δεν θα\n" +
                "τροποποιούνται.</li>\n" +
                "<li><b>Αναδιάταξη γραμμάτων.</b> Με την επιλογή αυτή ο χρήστης ζητά από την εφαρμογή\n" +
                "να γίνει ολική αναδιάταξη των γραμμάτων στο ταμπλό. Τα γράμματα παραμένουν\n" +
                "ίδια αλλά μεταβάλλεται η θέση τους τυχαία.</li>\n" +
                "<li><b>Αναδιάταξη στήλης.</b> Με την επιλογή αυτή ζητείται να γίνει αναδιάταξη των\n" +
                "γραμμάτων συγκεκριμένης στήλης.</li>\n" +
                "<li><b>Αναδιάταξη γραμμής.</b> Με την συγκεκριμένη επιλογή ζητείται να γίνει αναδιάταξη\n" +
                "των γραμμάτων συγκεκριμένης γραμμής.</li>\n" +
                "</ul>" +
                "</div></body></html>";
        JLabel messageLabel = new JLabel(developers);
        JOptionPane.showMessageDialog(jframe, messageLabel);
    }

    public void about_menu_clicked(ActionEvent e) {
        String developers = "<html><body><div width='150px' align='center'>Η εφαρμογή υλοποιήθηκε από τους <b>Μπούσιο Νικόλαος</b> και <b>Αλέξανδρο Φακή</b></div></body></html>";
        JLabel messageLabel = new JLabel(developers);
        JOptionPane.showMessageDialog(jframe, messageLabel);
    }

    public void saveSettings_clicked(ActionEvent e, JFrame frame) {
        // αποθηκεύει τις τιμές από τα JTextFields
        SCORE = Integer.parseInt(score_to_win.getText());
        WORDS = Integer.parseInt(words_to_make.getText());
        SWAPS = Integer.parseInt(max_swaps.getText());
        DELETES = Integer.parseInt(max_row_deletes.getText());
        SHUFFLES = Integer.parseInt(max_table_shuffles.getText());
        COL_SHUFFLES = Integer.parseInt(max_column_shuffles.getText());
        ROW_SHUFFLES = Integer.parseInt(max_row_shuffles.getText());
        GAME_MODE = modeSelect.getSelectedIndex() + 1;

        // κλείνει το παράθυρο
        frame.dispose();
    }

    public void deleteRow_clicked(ActionEvent e) {
        try {
            Integer i = Integer.parseInt(deleteRowUsed.getText().split("/")[0]);

            // έλεγχος αν επιτρέπεται
            // η χρήση της βοήθειας
            if (i < DELETES) {

                // διαγραφή πρώτης γραμμής
                game.deleteRow(Integer.parseInt(deleteRow_txt.getText()));

                // εκτύπωση του board
                System.out.println("Πίνακας με διαγραμμένη την " + deleteRow_txt.getText() + " γραμμή");
                game.printBoard();

                // ενημέρωση των χρησιμοποιημένων διαγραφών
                deleteRowUsed.setText(++i + "/" + DELETES);

                // φορτώνεις το καινούργιο ταμπλό
                loadBoard();

                // απανεργοποίηση στοιχείων σε περίπτωση
                // που όλες οι βοήθειες έχουν χρησιμοποιηθεί
                if (i == DELETES) {
                    deleteRow_txt.setEnabled(false);
                    deleteRow.setEnabled(false);
                    JOptionPane.showMessageDialog(null, "Όλες οι βοήθειες δαγραφής έχουν εξαντληθεί.", "Τέλος!", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Εισήγαγε αριθμό!", "Λάθος!", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "Λάθος!", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void swapLetters_clicked(ActionEvent e) {
        // ενημέρωση των χρησιμοποιημένων διαγραφών
        Integer i = Integer.parseInt(swapLettersUsed.getText().split("/")[0]);

        // έλεγχος αν επιτρέπεται
        // η χρήση της βοήθειας
        if (i < SWAPS) {

            // ενημέρωση των χρησιμοποιημένων ενναλλαγής γραμμάτων
            swapLettersUsed.setText(++i + "/" + SWAPS);

            // φορτώνεις το καινούργιο ταμπλό
            loadBoard();

            // απανεργοποίηση στοιχείων σε περίπτωση
            // που όλες οι βοήθειες έχουν χρησιμοποιηθεί
            if (i == SWAPS) {
                swapLetters.setEnabled(false);
                JOptionPane.showMessageDialog(null, "Όλες οι βοήθειες ενναλλαγής γραμμάτων έχουν εξαντληθεί.", "Τέλος!", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public void shuffleColumn_clicked(ActionEvent e) {
        try {
            // ενημέρωση των χρησιμοποιημένων ανακατεμέτων στήλης
            Integer i = Integer.parseInt(shuffleColumnUsed.getText().split("/")[0]);

            // έλεγχος αν επιτρέπεται
            // η χρήση της βοήθειας
            if (i < COL_SHUFFLES) {
                // ανακάτεμα στήλης
                game.shuffleColumn(Integer.parseInt(shuffleColumn_txt.getText()));

                // εκτύπωση του board
                System.out.println("Ανακατεμένος της " + shuffleColumn_txt.getText() + " στήλης του πίνακα");
                game.printBoard();

                // ενημέρωση των χρησιμοποιημένων ανακατεμάτων
                shuffleColumnUsed.setText(++i + "/" + COL_SHUFFLES);

                // φορτώνεις το καινούργιο ταμπλό
                loadBoard();

                // απανεργοποίηση στοιχείων σε περίπτωση
                // που όλες οι βοήθειες έχουν χρησιμοποιηθεί
                if (i == COL_SHUFFLES) {
                    shuffleColumn_txt.setEnabled(false);
                    shuffleColumn.setEnabled(false);
                    JOptionPane.showMessageDialog(null, "Όλες οι βοήθειες ανακατεμάτων γραμμάτων στήλης έχουν εξαντληθεί.", "Τέλος!", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Εισήγαγε αριθμό!", "Λάθος!", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "Λάθος!", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void shuffleRow_clicked(ActionEvent e) {
        try {
            // ενημέρωση των χρησιμοποιημένων ανακατεμέτων γραμμής
            Integer i = Integer.parseInt(shuffleRowUsed.getText().split("/")[0]);

            // έλεγχος αν επιτρέπεται
            // η χρήση της βοήθειας
            if (i < ROW_SHUFFLES) {

                // ανακάτεμα γραμμής
                game.shuffleRow(Integer.parseInt(shuffleRow_txt.getText()));

                // εκτύπωση του ταμπλό
                System.out.println("Ανακατεμένος της " + shuffleRow_txt.getText() + " γραμμής του πίνακα");
                game.printBoard();

                // ενημέρωση των χρησιμοποιημένων ανακατεμάτων
                shuffleRowUsed.setText(++i + "/" + ROW_SHUFFLES);

                // φορτώνεις το καινούργιο ταμπλό
                loadBoard();

                // απανεργοποίηση στοιχείων σε περίπτωση
                // που όλες οι βοήθειες έχουν χρησιμοποιηθεί
                if (i == ROW_SHUFFLES) {
                    shuffleRow_txt.setEnabled(false);
                    shuffleRow.setEnabled(false);
                    JOptionPane.showMessageDialog(null, "Όλες οι βοήθειες ανακατεμάτων γραμμάτων γραμμής έχουν εξαντληθεί.", "Τέλος!", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Εισήγαγε αριθμό!", "Λάθος!", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "Λάθος!", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void shuffleTable_clicked(ActionEvent e) {

        // ενημέρωση των χρησιμοποιημένων ανακατεμέτων
        Integer i = Integer.parseInt(shuffleTableUsed.getText().split("/")[0]);

        // έλεγχος αν επιτρέπεται
        // η χρήση της βοήθειας
        if (i < SHUFFLES) {
            // ανακάτεμα board
            game.shuffleTable();

            // εκτύπωση του board
            System.out.println("Ανακατεμένος πίνακας");
            game.printBoard();

            // ενημέρωση των χρησιμοποιημένων ανακατεμάτων
            shuffleTableUsed.setText(++i + "/" + SHUFFLES);

            // φορτώνεις το καινούργιο ταμπλό
            loadBoard();

            // απενεργοποίηση στοιχείων σε περίπτωση
            // που όλες οι βοήθειες έχουν χρησιμοποιηθεί
            if (i == SHUFFLES) {
                shuffleTable.setEnabled(false);
                JOptionPane.showMessageDialog(null, "Όλες οι βοήθειες ανακατεμάτων γραμμάτων ταμπλό έχουν εξαντληθεί.", "Τέλος!", JOptionPane.WARNING_MESSAGE);
            }
        }

        // απεπιλέγει τα επιλεγμένα καθώς ο πίνακας
        // ανακατεύτηκε και αυτά ενδεχομένως να βρίσκονται
        // σε διάσπαρτες θέσεις που δεν ακολουθούν τους κανόνες
        unSelectAll();
    }

    public void initializeGUI() {

        // Αρχικοποίηση γραφικών στοιχείων
        deleteRowUsed = new JLabel("0/" + DELETES);
        swapLettersUsed = new JLabel("0/" + SWAPS);
        shuffleColumnUsed = new JLabel("0/" + COL_SHUFFLES);
        shuffleRowUsed = new JLabel("0/" + ROW_SHUFFLES);
        shuffleTableUsed = new JLabel("0/" + SHUFFLES);
        goal = new JLabel(SCORE.toString());
        score = new JLabel("0");
        wordPoints = new JLabel("0");
        wordsFound = new JLabel("0/" + WORDS);

        // παράθυρο που περιέχει το board και τις λοιπές πληροφορίες
        jframe = new JFrame("Κρυπτόλεξο");
        // ανάλογα με το μέγεθος του board ορίζουμε κατάλληλο μέγεθος για το παράθυρο
        switch (GAME_MODE) {
            case 1:
                jframe.setSize(710, 280);
                break;
            case 2:
                jframe.setSize(900, 400);
                break;
            case 3:
                jframe.setSize(1045, 440);
                break;
        }
        jframe.setResizable(false);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // δημιουργία της μπάρας μενού
        menuBar = new JMenuBar();

        // δημιουργία των μενού
        menu = new JMenu("Μενού");
        menu.setMnemonic(KeyEvent.VK_M);
        tools = new JMenu("Εργαλεία");
        tools.setMnemonic(KeyEvent.VK_T);

        // προσθήκη των μενού στην μπάρα
        menuBar.add(menu);
        menuBar.add(tools);

        // δημιουργία υπομενού του μενού και προσθήκη σε αυτό
        newGame = new JMenuItem("Νέο παιχνίδι");
        endGame = new JMenuItem("Ακύρωση/Τερματισμός παιχνιδιού");
        addPlayerInformation = new JMenuItem("Εισαγωγή στοιχείων παίκτη");
        helpSettings = new JMenuItem("Ρυθμίσεις βοηθειών");
        addDictionary = new JMenuItem("Αναζήτηση αρχείου λέξεων");
        exit = new JMenuItem("Έξοδος");

        // προσθήκη των κουμπιών σε αυτό
        menu.add(newGame);
        menu.add(endGame);
        menu.add(addPlayerInformation);
        menu.add(helpSettings);
        menu.add(addDictionary);
        menu.add(exit);

        // δημιουργία υπομενού του μενού και προσθήκη σε αυτό
        help = new JMenuItem("Βοήθεια");
        about = new JMenuItem("Σχετικά...");

        // προσθήκη των κουμπιών σε αυτό
        tools.add(help);
        tools.add(about);

        // προσθήκη μενού στο παράθυρο
        jframe.setJMenuBar(menuBar);

        // πάνελ που περιέχει τις βοήθειες και
        // τις πληροφορίες παιχνιδιού
        JPanel helpPanel = new JPanel();
        helpPanel.setLayout(new BoxLayout(helpPanel, BoxLayout.PAGE_AXIS));

        JPanel help1 = new JPanel();
        help1.setLayout(new GridLayout(3, 3));

        help1.add(deleteRow = new JButton("Διαγραφή γραμμής"));
        help1.add(deleteRow_txt = new JTextField("0", 2));
        help1.add(deleteRowUsed);
        help1.add(shuffleRow = new JButton("Αναδιάταξη γραμμής"));
        help1.add(shuffleRow_txt = new JTextField("0", 2));
        help1.add(shuffleRowUsed);
        help1.add(shuffleColumn = new JButton("Αναδιάταξη στήλης"));
        help1.add(shuffleColumn_txt = new JTextField("0", 2));
        help1.add(shuffleColumnUsed);

        JPanel help2 = new JPanel();
        help2.setLayout(new GridLayout(6, 2));

        help2.add(shuffleTable = new JButton("Αναδιάταξη Ταμπλό"));
        help2.add(shuffleTableUsed);
        help2.add(swapLetters = new JButton("Εναλλαγή Γραμμάτων"));
        help2.add(swapLettersUsed);
        help2.add(new JLabel("Στόχος:"));
        help2.add(goal);
        help2.add(new JLabel("Συνολική βαθμολογία:"));
        help2.add(score);
        help2.add(new JLabel("Βαθμολογία λέξης:"));
        help2.add(wordPoints);
        help2.add(new JLabel("Λέξεις που βρέθηκαν:"));
        help2.add(wordsFound);

        helpPanel.add(help1);
        helpPanel.add(help2);

        // πάνελ που περιέχει το board
        boardPanel = new JPanel();
        // διαβάζουμε το μέγεθος του board
        Integer size = game.getBoardSize();
        // δημιουργούμε το πάνελ με gridlayout κατάλληλου μεγέθους βάση board
        boardPanel.setLayout(new GridLayout(size, size));

        // προσθήκη των πάνελ πάνω στο jframe μέσω ενός τρίτου πάνελ
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));

        // πάνελ που περιέχει τα γράμματα και το κουμπί ελέγχου
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
        rightPanel.add(boardPanel);
        rightPanel.add(checkWord = new JButton("Έλεγχος λέξης"), BorderLayout.CENTER);

        mainPanel.add(rightPanel);
        mainPanel.add(helpPanel);

        // προσθήκη πάνελ στο frame
        jframe.add(mainPanel);

        loadBoard();

        // προσθήκη των event listeners
        // των γραφικών στοιχείων (κυρίως κουμπιών)
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                newGame_menu_clicked(actionEvent);
            }
        });
        endGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                endGame_menu_clicked(actionEvent);
            }
        });
        addPlayerInformation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                addPlayerInformation_menu_clicked(actionEvent);
            }
        });
        helpSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                helpSettings_menu_clicked(actionEvent);
            }
        });
        addDictionary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                addDictionary_menu_clicked(actionEvent);
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                exit_menu_clicked(actionEvent);
            }
        });
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                help_menu_clicked(actionEvent);
            }
        });
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                about_menu_clicked(actionEvent);
            }
        });
        deleteRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                deleteRow_clicked(actionEvent);
            }
        });
        swapLetters.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                swapLetters_clicked(actionEvent);
            }
        });
        shuffleColumn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                shuffleColumn_clicked(actionEvent);
            }
        });
        shuffleRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                shuffleRow_clicked(actionEvent);
            }
        });
        shuffleTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                shuffleTable_clicked(actionEvent);
            }
        });
        checkWord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                checkWord_clicked(actionEvent);
            }
        });

        // κώδικας για τοποθέτηση του frame στο κέντρο της οθόνης
        // χρησιμοποιείτε πριν το pack() σύμφωνα με το javaDoc
        jframe.setLocationRelativeTo(null);

        // εμφάνιση του frame
        jframe.setVisible(true);
    }

    private void loadBoard() {
        boardButtons = game.getBoardLetters();

        // αφαίρεση προηγούμενων κουμπιών από το ταμπλό
        boardPanel.removeAll();

        // προσθήκη των γράμματων-κουμπιών στο ταμπλό
        for (Letter letter : boardButtons.values()) {
            boardPanel.add(letter);

            // προσθήκη listener των γράμματων-κουμπιών στο ταμπλό
            // εκτός και αν υπάρχει από πριν
            // αυτό συμβαίνει διότι όταν διαγράφουμε μια στήλη
            // τότε προσθέτουμε νέα γράμματα τα οποία δεν του έχει
            // ανατεθεί καποιος actionlistener
            if (letter.getActionListeners().length == 0) {
                // δημιουργία των listener για το κάθε ένα κουμπί
                letter.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        boardLetter_clicked(e);
                    }
                });

                // δημιουργία των listener για το κάθε ένα κουμπί
                letter.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (SwingUtilities.isRightMouseButton(e) || e.isControlDown()) {
                            // απεπιλλέγει όλα τα κουμπιά
                            unSelectAll();
                        }
                    }
                });
            }
        }

        // χρειάζεται να καλεσθεί μετά το removeAll()
        // αυτό συμβαίνει επείδη η περιοχή είναι "βρώμικη"
        jframe.revalidate();
    }

    private void unSelectAll() {
        // απεπιλλέγει όλα τα κουμπιά
        for (Letter letter : boardButtons.values()) {
            letter.diselect();
        }
        // αδειάζει τον πίνακα με τις επιλεγμένες λέξεις
        WordQueue.removeAll(WordQueue);
    }
}
