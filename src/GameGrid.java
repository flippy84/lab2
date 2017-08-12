import javafx.util.Pair;

public class GameGrid {
    private Marker[][] grid;

    public GameGrid() {
        grid = new Marker[8][8];
    }

    public void setCell(Marker m, int x, int y) {
        grid[x % 8][y % 8] = m;
    }

    public  Marker getCell(int x, int y) {
        return grid[x % 8][y % 8];
    }
}
