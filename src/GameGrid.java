import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.*;
import javafx.scene.control.Button;
import javafx.util.Pair;

import java.util.List;
import java.util.Vector;

public class GameGrid  {
    private Marker[][] grid;
    Vector<UpdateEvent> updateEventList;

    public GameGrid() {
        grid = new Marker[8][8];
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                grid[x][y] = Marker.None;
            }
        }

        updateEventList = new Vector();
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
    public void Update(GameGrid sender);
}