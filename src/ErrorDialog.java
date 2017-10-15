import javafx.scene.control.Alert;

public class ErrorDialog {
    private Alert alert;

    public ErrorDialog(String text) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(text);
    }

    public void showAndWait() {
        alert.showAndWait();
    }
}
