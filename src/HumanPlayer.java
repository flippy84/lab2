import java.awt.*;

public class HumanPlayer extends Player {
    private IHumanPlayerInput playerInput;

    @Override
    public Point getMove() {
        Point point = playerInput.getInput();
        System.out.println(point);
        return point;
    }

    @Override
    public String toString() {
        return "HumanPlayer";
    }

    public HumanPlayer(IHumanPlayerInput playerInput) {
        this.playerInput = playerInput;
    }
}
