import javafx.scene.control.Alert;

public class DrawnDialog {
    private Alert alert;

    public DrawnDialog() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tie");
        alert.setHeaderText(null);
        alert.setContentText("The game is a tie!");
    }

    public void showAndWait() {
        alert.showAndWait();
    }
}
