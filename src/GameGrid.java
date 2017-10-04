import javafx.scene.paint.Color;

import java.util.Vector;

public class GameGrid  {
    private Marker[][] grid;
    private Vector<UpdateEvent> updateEventList;
    private int columns = 7;
    private int rows = 6;

    public GameGrid() {
        grid = new Marker[columns][rows];
        updateEventList = new Vector();

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

    public void addOnUpdate(UpdateEvent e) {
        updateEventList.add(e);
    }

    private void fireOnUpdate() {
        for (UpdateEvent e : updateEventList) {
            e.Update(this);
        }
    }
}

interface UpdateEvent {
    void Update(GameGrid sender);
}