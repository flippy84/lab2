import javafx.scene.control.Alert;

/**
 * WinnerDialog displays a message when a player wins
 * the game.
 * @author Andreas Carlsson
 */
public class WinnerDialog {
    private Alert alert;

    public WinnerDialog() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Winner");
        alert.setHeaderText(null);
    }

    public void showAndWait(Player player) {
        alert.setContentText(player.name + " wins!");
        alert.showAndWait();
    }
}
