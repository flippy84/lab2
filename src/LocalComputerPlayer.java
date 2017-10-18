import java.awt.*;
import java.util.Random;

/**
 * A computer player that returns a random move.
 * @author Andreas Carlsson
 */
public class LocalComputerPlayer extends Player {
    private Random random;
    private GameGrid gameGrid;

    public LocalComputerPlayer(GameGrid gameGrid) {
        this.random = new Random();
        this.gameGrid = gameGrid;
    }

    /**
     * Gets all the valid moves from the current GameGrid
     * and returns a random move from the array returned.
     * @return The computers next move.
     */
    @Override
    public Point getMove() {
        Point[] moves = gameGrid.getValidMoves();
        int i = random.nextInt(moves.length);
        return moves[i];
    }

    @Override
    public String toString() {
        return "LocalComputerPlayer";
    }
}
