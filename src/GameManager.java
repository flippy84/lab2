import java.awt.*;

public class GameManager extends Thread {
    private boolean quit;
    private Player player1;
    private Player player2;
    private GameGrid gameGrid;

    GameManager(Player player1, Player player2, GameGrid gameGrid) {
        this.player1 = player1;
        this.player2 = player2;
        this.gameGrid = gameGrid;
    }

    @Override
    public void run() {
        while (true) {
            if (quit)
                break;
            Move(player1);

            if (quit)
                break;
            Move(player2);

            System.out.println("Running");
        }
        System.out.println("Stopped");
    }

    public void play() {
        start();
    }

    public void quit() {
        quit = true;
    }

    private void Move(Player player) {
        Point point = player.getMove();
        if (quit)
            return;

        while (!isValidMove(point)) {
            System.out.println("Invalid move!");
            point = player.getMove();
        }

        Point landingPoint = getLandingPoint(point);
        gameGrid.setCell(player.markerID, landingPoint.x, landingPoint.y);
        if (hasWon(player, landingPoint)) {
            System.out.println(player.name + " wins!");
            quit = true;
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

    enum Direction { Up, Down, Left, Right, UpLeft, UpRight, DownLeft, DownRight };

    // Search for four consecutive markers in all directions instead of up
    private boolean search(Player player, Point point, Direction direction) {
        switch (direction) {
            case Down:
                return search(player, point, 0, 1);
            case Left:
                return search(player, point, -1, 0);
            case Right:
                return search(player, point, 1, 0);
            case UpLeft:
                return search(player, point, -1, -1);
            case UpRight:
                return search(player, point, -1, 1);
            case DownLeft:
                return search(player, point, -1, 1);
            case DownRight:
                return search(player, point, 1, 1);
            default:
                return false;
        }
    }

    private boolean search(Player player, Point point, int xIncrement, int yIncrement) {
        return search(player, point.x, point.y, xIncrement, yIncrement, 0);
    }

    private boolean search(Player player, int x, int y, int xIncrement, int yIncrement, int level) {
        if (level == 4)
            return true;

        if (inGrid(x, y) && gameGrid.getCell(x, y) == player.markerID) {
            return search(player, x + xIncrement, y + yIncrement, xIncrement, yIncrement, level + 1);
        }

        return false;
    }

    private boolean inGrid(int x, int y) {
        return (x < gameGrid.getColumns() && y < gameGrid.getRows() && x >= 0 && y >= 0);
    }
}
