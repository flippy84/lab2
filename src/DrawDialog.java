import javafx.scene.control.Alert;

/**
 * DrawDialog displays a message that the current game ended in a draw.
 * @author Andreas Carlsson
 */
public class DrawDialog {
    private Alert alert;

    public DrawDialog() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Draw");
        alert.setHeaderText(null);
        alert.setContentText("The game is a draw!");
    }

    public void showAndWait() {
        alert.showAndWait();
    }
}
