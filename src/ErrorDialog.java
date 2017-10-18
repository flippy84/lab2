import javafx.scene.control.Alert;

/**
 * ErrorDialog displays an error message to the user.
 * @author Andreas Carlsson
 */
public class ErrorDialog {
    private Alert alert;

    public ErrorDialog() {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
    }

    public void showAndWait(String text) {
        alert.setContentText(text);
        alert.showAndWait();
    }
}
