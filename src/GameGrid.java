import javafx.scene.paint.Color;

import java.util.Vector;

public class GameGrid  {
    private Marker[][] grid;
    private Vector<UpdateEvent> updateEventList;

    public GameGrid() {
        grid = new Marker[8][8];
        updateEventList = new Vector();

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (x == 3 && y == 3)
                    grid[x][y] = Marker.White;
                else if(x == 4 && y == 4)
                    grid[x][y] = Marker.White;
                else if(x == 3 && y == 4)
                    grid[x][y] = Marker.Black;
                else if(x == 4 && y == 3)
                    grid[x][y] = Marker.Black;
                else
                    grid[x][y] = Marker.None;
            }
        }
    }

    public void setCell(Marker m, int x, int y) {
        grid[x % 8][y % 8] = m;
        fireOnUpdate();
    }

    public  Marker getCell(int x, int y) {
        return grid[x % 8][y % 8];
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