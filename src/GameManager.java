import javafx.application.Platform;
import java.awt.*;

/**
 * The GameManager runs the game logic asks both players for a move in turn until
 * the game either ends in a draw or one of the players gets four in a row.
 * @author Andreas Carlsson
 */
public class GameManager {
    private volatile boolean stop;
    private Player player1;
    private Player player2;
    private GameGrid gameGrid;
    private GameLoop gameLoop;
    private Thread gameLoopThread;

    GameManager(Player player1, Player player2, GameGrid gameGrid) {
        this.player1 = player1;
        this.player2 = player2;
        this.gameGrid = gameGrid;
        this.gameLoop = new GameLoop();
    }

    /*
     * Internal class that implements the Runnable interface that
     * runs a loop asking each player in turn for a move.
     */
    private class GameLoop implements Runnable {
        @Override
        public void run() {
            while (true) {
                move(player1);
                if (stop)
                    break;

                move(player2);
                if (stop)
                    break;

                if (gameGrid.isFull()) {
                    stop = true;
                    Platform.runLater(() -> {
                        DrawDialog drawDialog = new DrawDialog();
                        drawDialog.showAndWait();
                    });
                    break;
                }
            }
        }
    }

    public void newGame() {
        stop();
        play();
    }

    public void play() {
        gameGrid.reset();
        stop = false;
        gameLoopThread = new Thread(gameLoop);
        gameLoopThread.start();
    }

    public void quit() {
        stop();
        player1.close();
        player2.close();
    }

    /*
     * Stops the game by interrupting the GameLoop thread
     * and calling join on the same thread to wait for it exiting.
     * Since the HumanPlayer class blocks waiting for input we need
     * to interrupt it.
     */
    private void stop() {
        stop = true;
        gameLoopThread.interrupt();
        try {
            gameLoopThread.join();
        } catch (InterruptedException ignored) {
        }
    }

    /**
     * Asks for a move from the given player and checks if the move is valid.
     * If the move is from a human player who clicked in a empty marker in
     * a column we get the point were the marker lands and set the cell as marked
     * in the game grid. If the game is won after the player marks a cell this function
     * flags the game to stop and displays a dialog informing which player won.
     */
    private void move(Player player) {
        Point point = player.getMove();
        if (stop)
            return;

        while (!isValidMove(point)) {
            Platform.runLater(() -> {
                ErrorDialog errorDialog = new ErrorDialog();
                errorDialog.showAndWait("Invalid move!");
            });
            point = player.getMove();
        }

        Point landingPoint = getLandingPoint(point);
        gameGrid.setCell(player.markerID, landingPoint.x, landingPoint.y);
        if (hasWon(player, landingPoint)) {
            Platform.runLater(() -> {
                WinnerDialog winnerDialog = new WinnerDialog();
                winnerDialog.showAndWait(player);
            });
            stop = true;
        }
    }

    // Place the circle in the first free cell in the clicked column
    private Point getLandingPoint(Point point) {
        int y;
        for (y = point.y; y < gameGrid.getRows() - 1; y++) {
            if (gameGrid.getCell(point.x, y + 1) != Marker.None)
                break;
        }
        return new Point(point.x, y);
    }

    private boolean isValidMove(Point point) {
        if (point == null)
            return false;
        return gameGrid.getCell(point.x, point.y) == Marker.None;
    }

    /**
     * Checks to see if the player won after placing the last
     * marker by searching each direction from the last point
     * of the placed marker.
     * @param player The player who placed the last marker.
     * @param point The point which the player last placed on the grid.
     * @return Returns true if four markers in a row is found.
     */
    private boolean hasWon(Player player, Point point) {
        for (Direction direction : Direction.values()) {
            if (search(player, point, direction))
                return true;
        }
        return false;
    }

    enum Direction { Up, Down, Left, Right, UpLeft, UpRight, DownLeft, DownRight }

    // Search for four consecutive markers in a row
    private boolean search(Player player, Point point, Direction direction) {
        switch (direction) {
            case Down:
                return search(player, point, 0, 1);
            case Left:
                return search(player, point, -1, 0);
            case DownLeft:
                return search(player, point, -1, 1);
            case DownRight:
                return search(player, point, 1, 1);
            default:
                return false;
        }
    }

    /*
     * Count the number of markers in a row including the start point.
     * This method searches in the direction determined by xIncrement and yIncrement
     * and the opposite direction by negating the parameters.
     */
    private boolean search(Player player, Point point, int xIncrement, int yIncrement) {
        int levels = search(player, point.x, point.y, xIncrement, yIncrement, 0);
        // Also search in the opposite direction
        levels += search(player, point.x, point.y, -xIncrement, -yIncrement, 0);
        // The start point is counted twice
        levels--;

        return levels >= 4;
    }

    /**
     * Recursive function that returns the number of consecutive markers in a specific color
     * including the marker in the starting point.
     */
    private int search(Player player, int x, int y, int xIncrement, int yIncrement, int level) {
        if (inGrid(x, y) && gameGrid.getCell(x, y) == player.markerID) {
            return search(player, x + xIncrement, y + yIncrement, xIncrement, yIncrement, level + 1);
        }

        return level;
    }

    // Helper function to determine if a point is inside the GameGrid
    private boolean inGrid(int x, int y) {
        return (x < gameGrid.getColumns() && y < gameGrid.getRows() && x >= 0 && y >= 0);
    }
}
