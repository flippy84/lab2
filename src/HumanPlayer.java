import java.awt.*;

public class HumanPlayer extends Player {
    private IHumanPlayerInput playerInput;

    @Override
    public Point getMove() {
        return playerInput.getInput();
    }

    @Override
    public String toString() {
        return "HumanPlayer";
    }

    public HumanPlayer(IHumanPlayerInput playerInput) {
        this.playerInput = playerInput;
    }
}
