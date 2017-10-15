import javafx.scene.control.Alert;

public class WinnerDialog {
    private Alert alert;

    public WinnerDialog(Player player) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Winner");
        alert.setHeaderText(null);
        alert.setContentText(player.name + " wins!");
    }

    public void showAndWait() {
        alert.showAndWait();
    }
}
