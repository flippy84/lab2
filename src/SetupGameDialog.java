import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SetupGameDialog extends DialogPane {
    public SetupGameDialog() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SetupGameDialog.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, root.prefWidth(-1), root.prefHeight(-1));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Setup players");
        stage.setScene(scene);
        stage.showAndWait();

        SetupGameDialogController controller = loader.<SetupGameDialogController>getController();
        Player player1 = controller.getPlayer1();
        Player player2 = controller.getPlayer2();
    }
}
