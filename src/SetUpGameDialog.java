import javafx.scene.control.*;

public class SetUpGameDialog extends Dialog {
    private SetUpGameDialogPane setup;

    public SetUpGameDialog(IHumanPlayerInput playerInput, GameGrid gameGrid) throws Exception {
        setup = new SetUpGameDialogPane(playerInput, gameGrid);
        setDialogPane(setup);
        setTitle("New game");
    }

    public Player getPlayer1() {
        return setup.getPlayer1();
    }

    public Player getPlayer2() {
        return setup.getPlayer2();
    }
}
