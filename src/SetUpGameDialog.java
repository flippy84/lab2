import javafx.scene.control.Dialog;
import java.io.IOException;

/**
 * This class displays a dialog asking for player type and name
 * for both players.
 * @author Andreas Carlsson
 */
public class SetUpGameDialog extends Dialog {
    private SetUpGameDialogPane setup;

    /**
     * Constructs a new SetUpGameDialog.
     * @param playerInput Reference to an IHumanPlayerInput interface
     *                    used for the HumanPlayer class.
     * @param gameGrid Reference to a GameGrid used for both LocalComputerPlayer
     *                 and RemoteComputerPlayer for getting valid moves.
     * @throws IOException Throws an exception if the internal DialogPane can't
     *                     be loaded from the FXML file.
     */
    public SetUpGameDialog(IHumanPlayerInput playerInput, GameGrid gameGrid) throws IOException {
        setup = new SetUpGameDialogPane(playerInput, gameGrid);
        setDialogPane(setup);
        setTitle("New game");
    }

    /**
     * Returns player one.
     * @return Returns a Player object for player one.
     */
    public Player getPlayer1() {
        return setup.getPlayer1();
    }

    /**
     * Returns player two.
     * @return Returns a Player object for player two.
     */
    public Player getPlayer2() {
        return setup.getPlayer2();
    }
}
