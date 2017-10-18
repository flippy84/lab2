import java.awt.*;
import java.util.Observable;
import java.util.Vector;

/**
 * This class keeps track of the current game and where all markers are placed.
 * It derives from Observable and notifies all observers that the grid has changed
 * when a player makes a move.
 * @author Andreas Carlsson
 */
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

    /**
     * Marks a specific cell with a marker.
     * @param marker What kind of marker we should set the cell to.
     * @param x The x coordinate of the cell to set.
     * @param y The y coordinate of the cell to set.
     */
    public void setCell(Marker marker, int x, int y) {
        grid[x % columns][y % rows] = marker;
        setChanged();
        notifyObservers();
    }

    /**
     *
     * @param x The x coordinate of the cell to get.
     * @param y The y coordinate of the cell to get.
     * @return Returns the marker in the cell with the given coordinates.
     */
    public  Marker getCell(int x, int y) {
        return grid[x % columns][y % rows];
    }

    /**
     * Gets the number of columns in the grid.
     * @return The number of columns.
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Gets the number of rows in the grid.
     * @return The number of rows.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Empties the grid setting all cells to Marker.None
     * and notifying all observers.
     */
    public void reset() {
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                grid[x][y] = Marker.None;
            }
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Gets all valid moves in the current grid
     * @return The current valid moves as an array.
     */
    public Point[] getValidMoves() {
        Vector<Point> moves = new Vector<>();

        for (int x = 0; x < columns; x++) {
            for (int y = rows - 1; y >= 0; y--) {
                if (grid[x][y] == Marker.None) {
                    moves.add(new Point(x, y));
                    break;
                }
            }
        }

        return moves.toArray(new Point[moves.size()]);
    }

    /**
     * Determines if the grid is full to detect if the game is a draw.
     * @return Returns true if the grid is full.
     */
    public boolean isFull() {
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                if (grid[x][y] == Marker.None) {
                    return false;
                }
            }
        }
        return true;
    }
}