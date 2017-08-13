import java.awt.*;

public class HumanPlayer extends Player {
    private GameBoard gameBoard;

    @Override
    public Point getMove() {
        Point t = gameBoard.getInput();
        System.out.println(t);
        return t;
    }

    @Override
    public String toString() {
        return "HumanPlayer";
    }

    public HumanPlayer(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }
}
