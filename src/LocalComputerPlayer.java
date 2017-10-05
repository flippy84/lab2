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
        int x = random.nextInt(gameGrid.getColumns());
        return new Point(x, 0);
    }

    @Override
    public String toString() {
        return "LocalComputerPlayer";
    }
}
