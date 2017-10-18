import java.awt.*;

/**
 * A class representing a human player getting input from a
 * IHumanPlayerInput interface.
 * @author Andreas Carlsson
 * @see IHumanPlayerInput
 */
public class HumanPlayer extends Player {
    private IHumanPlayerInput playerInput;

    /**
     * Gets input from the player when clicking on a GameBoard,
     * this function blocks until the player clicks the board.
     * @return The point of the clicked cell.
     */
    @Override
    public Point getMove() {
        return playerInput.getInput();
    }

    @Override
    public String toString() {
        return "HumanPlayer";
    }

    /**
     * Constructs a new HumanPlayer and saves the IHumanPlayerInput
     * interface for getting input.
     * @param playerInput Interface for getting input from the player.
     */
    public HumanPlayer(IHumanPlayerInput playerInput) {
        this.playerInput = playerInput;
    }
}
