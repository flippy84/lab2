import java.awt.*;
import java.util.Random;

public class LocalComputerPlayer extends Player {
    Random random;
    GameGrid gameGrid;

    public LocalComputerPlayer(GameGrid gameGrid) {
        random = new Random();
        this.gameGrid = gameGrid;
    }

    @Override
    public Point getMove() {
        int x = 0;
        int y = 0;
        while (true) {
            x = random.nextInt(gameGrid.getColumns());
            if (gameGrid.getCell(x, y) == Marker.None)
                return new Point(x, y);
        }
    }

    @Override
    public String toString() {
        return "LocalComputerPlayer";
    }
}
