import java.awt.*;
import java.util.Random;

public class LocalComputerPlayer extends Player {
    private Random random;
    private GameGrid gameGrid;

    public LocalComputerPlayer(GameGrid gameGrid) {
        this.random = new Random();
        this.gameGrid = gameGrid;
    }

    @Override
    public Point getMove() {
        //TODO: Use gameGrid.validMoves
        while (true) {
            int x = random.nextInt(gameGrid.getColumns());
            if (gameGrid.getCell(x, 0) == Marker.None)
                return new Point(x, 0);
        }
    }

    @Override
    public String toString() {
        return "LocalComputerPlayer";
    }
}
