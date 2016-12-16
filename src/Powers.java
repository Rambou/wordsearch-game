import java.awt.*;

public interface Powers {
    void swapLetters(Point a, Point b);

    void deleteRow(Integer line) throws Exception;

    void shuffleLetters();

    void shuffleColumn(int column) throws Exception;

    void shuffleRow(int row) throws Exception;

    void shuffleTable();
}
