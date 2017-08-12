import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ToolbarController {
    @FXML
    private Button quit;

    @FXML
    public void onClickedQuit() {
        Stage stage = (Stage)quit.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    @FXML
    public void onClickedNewGame() {
        
    }
}
