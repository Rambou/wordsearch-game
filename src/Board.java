public class Board implements Powers {

    private int size;
    private Letter table[][];

    public Board(int size) {
        this.size = size;
        this.table = new Letter[size][size];

        addLetters();
    }

    private void addLetters() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //table = new RandomLetter();
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
