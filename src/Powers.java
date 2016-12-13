import java.awt.*;

public interface Powers {
    void swapLetters(Point a, Point b);

    void deleteRow(Integer line);

    void shuffleLetters();

    void shuffleColumn(int column);

    void shuffleRow(int row);

    void shuffleTable();
}
