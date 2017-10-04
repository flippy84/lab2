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
        Point point;

        point = player.getMove();
        if (quit)
            return;
        while (!isValidMove(point)) {
            System.out.println("Invalid move!");
            point = player.getMove();
        }

        Point landingPoint = getLandingPoint(point);
        gameGrid.setCell(player.markerID, landingPoint.x, landingPoint.y);
    }

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

    enum Direction { Up, Down, Left, Right, UpLeft, UpRight, DownLeft, DownRight };

    private boolean search(Player player, Point point, Direction direction) {
        switch (direction) {
            case Up:
                return search(player, point, 0, -1);
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
        }
        return false;
    }

    private boolean search(Player player, Point point, int xIncrement, int yIncrement) {
        Marker opponentMarker = player.markerID == Marker.Black ? Marker.White : Marker.Black;
        Marker playerMarker = player.markerID;
        Marker lastMarker = gameGrid.getCell(point.x, point.y);
        Marker secondLastMarker;
        int i = 0;

        for (int x = point.x, y = point.y; x >= 0 && x < 8 && y >= 0 && y < 8; x += xIncrement, y += yIncrement, i++) {
            secondLastMarker = lastMarker;
            if (i == 0 && gameGrid.getCell(x, y) != Marker.None)
                return false;
            else if (i == 1 && gameGrid.getCell(x, y) != opponentMarker)
                return false;
            else if (gameGrid.getCell(x, y) == Marker.None)
                break;

            lastMarker = gameGrid.getCell(x, y);
        }

        //if (i < )

        return false;
    }

    private boolean hasAdjacentMarker(Player player, Point point) {
        Marker opponentMarker = player.markerID == Marker.Black ? Marker.White : Marker.Black;
        Marker playerMarker = player.markerID;
        int x = point.x;
        int y = point.y;

        // Check for at least one adjacent opponent marker
        if (x > 0 && y > 0 && opponentMarker == gameGrid.getCell(x - 1, y - 1) ||
            x > 0 &&          opponentMarker == gameGrid.getCell(x - 1, y) ||
            x > 0 && y < 7 && opponentMarker == gameGrid.getCell(x - 1, y + 1) ||
            y > 0 &&          opponentMarker == gameGrid.getCell(x, y - 1) ||
            y < 7 &&          opponentMarker == gameGrid.getCell(x, y + 1) ||
            x < 7 && y > 0 && opponentMarker == gameGrid.getCell(x + 1, y - 1) ||
            x < 7 &&          opponentMarker == gameGrid.getCell(x + 1, y) ||
            x < 7 && y < 7 && opponentMarker == gameGrid.getCell(x + 1, y + 1))
            return true;
        else
            return false;
    }
}
