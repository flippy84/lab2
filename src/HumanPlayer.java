import java.awt.*;

public class HumanPlayer extends Player {
    private GameBoard gameBoard;

    @Override
    public void getMove() {
        Point t = gameBoard.getInput();
        System.out.println(t);
    }

    @Override
    public String toString() {
        return "HumanPlayer";
    }

    public HumanPlayer(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }
}
