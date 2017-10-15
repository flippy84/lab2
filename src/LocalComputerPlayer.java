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
        Point[] moves = gameGrid.getValidMoves();
        System.out.println(moves.length);
        int i = random.nextInt(moves.length);
        return moves[i];
    }

    @Override
    public String toString() {
        return "LocalComputerPlayer";
    }
}
