import java.util.Observable;

public class GameGrid extends Observable {
    private Marker[][] grid;
    private int columns = 7;
    private int rows = 6;

    public GameGrid() {
        grid = new Marker[columns][rows];

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                grid[x][y] = Marker.None;
            }
        }
    }

    public void setCell(Marker m, int x, int y) {
        grid[x % columns][y % rows] = m;
        setChanged();
        notifyObservers();
    }

    public  Marker getCell(int x, int y) {
        return grid[x % columns][y % rows];
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public void reset() {
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                grid[x][y] = Marker.None;
            }
        }
        setChanged();
        notifyObservers();
    }
}