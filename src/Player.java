public class Player {

    // Ιδιοτητες κλάσης, Όνομα παίκτη, συνολικοί πόντοι, λέξεις
    private String name;
    private Integer points;
    private Integer words;

    // Αρχικοποίηση κλάσης
    public Player() {
        this.points = 0;
    }

    // προσθήκη πόντων
    public void setPoints(Integer Points) {
        this.points += Points;
    }

    // Επιστρέφει το όνομα του χρήστη
    public String getName() {
        return name;
    }

    // πετάει Exception σε περίπτωση λανθασμένης εισαγωγής ονόματος
    public void setName(String Name) throws Exception {
        // έλεγχος σωστής εισόδου ονόματος
        if (Name.isEmpty() || (Name.length() > 30 && Name.length() < 4)) {
            throw new Exception("Name must not be empty and have length from 4 to 30 characters.");
        }
        this.name = Name;
    }

    // επιστρέφει τους πόντους του παίκτη
    public int getPoint() {
        return this.points;
    }

}
