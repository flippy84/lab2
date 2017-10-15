import java.awt.*;

public class GameManager {
    private volatile boolean stop;
    private Player player1;
    private Player player2;
    private GameGrid gameGrid;
    private GameLoop gameLoop;
    private Thread gameLoopThread;

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
            }
        }
    }

    public void newGame() {
        stop();
        play();
    }

    GameManager(Player player1, Player player2, GameGrid gameGrid) {
        this.player1 = player1;
        this.player2 = player2;
        this.gameGrid = gameGrid;
        gameLoop = new GameLoop();
    }

    public void play() {
        gameGrid.reset();
        stop = false;
        gameLoopThread = new Thread(gameLoop);
        gameLoopThread.start();
    }

    private void stop() {
        stop = true;
        gameLoopThread.interrupt();
        try {
            gameLoopThread.join();
        } catch (InterruptedException ignored) {
        }
    }

    public void quit() {
        stop();
    }

    private void move(Player player) {
        Point point = player.getMove();
        if (stop)
            return;

        while (!isValidMove(point)) {
            System.out.println("Invalid move!");
            point = player.getMove();
        }

        Point landingPoint = getLandingPoint(point);
        gameGrid.setCell(player.markerID, landingPoint.x, landingPoint.y);
        if (hasWon(player, landingPoint)) {
            System.out.println(player.name + " wins!");
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

    // Count the number of markers in a row including the start point.
    // This method searches in the direction determined by xIncrement and yIncrement
    // and the opposite direction by negating the parameters.
    private boolean search(Player player, Point point, int xIncrement, int yIncrement) {
        int levels = search(player, point.x, point.y, xIncrement, yIncrement, 0);
        // Also search in the opposite direction
        levels += search(player, point.x, point.y, -xIncrement, -yIncrement, 0);
        // The start point is counted twice
        levels--;

        return levels >= 4;
    }

    // Recursive function that returns the number of consecutive markers in a specific color
    // including the marker in the starting point.
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
