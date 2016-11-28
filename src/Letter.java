abstract class Letter {
    private static Character letter;
    private static Integer point;

    // Αρχικοποίηση κλάσης
    public Letter(Character letter, Integer point) {
        this.letter = letter;
        this.point = point;
    }

    // Επιστροφή γράμματος
    public char getLettter() {
        return letter;
    }

    // Επιστροφή πόντου
    public int getPoint() {
        return point;
    }

    // Υπολογισμός πόντου
    abstract int computePoint();
}
