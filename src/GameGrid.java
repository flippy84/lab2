import java.util.Vector;

public class GameGrid  {
    private Marker[][] grid;
    private Vector<GridEvent> gameGridUpdateList;
    private int columns = 7;
    private int rows = 6;

    public GameGrid() {
        grid = new Marker[columns][rows];
        gameGridUpdateList = new Vector();

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                grid[x][y] = Marker.None;
            }
        }
    }

    public void setCell(Marker m, int x, int y) {
        grid[x % columns][y % rows] = m;
        fireOnUpdate();
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

    public void addOnUpdate(GridEvent e) {
        gameGridUpdateList.add(e);
    }

    private void fireOnUpdate() {
        for (GridEvent e : gameGridUpdateList) {
            e.fire(this);
        }
    }

    public void reset() {
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                grid[x][y] = Marker.None;
            }
        }
        fireOnUpdate();
    }
}

interface Event {
    void fire(Object sender);
}

interface GridEvent {
    void fire(GameGrid sender);
}