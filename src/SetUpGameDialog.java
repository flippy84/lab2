import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SetUpGameDialog extends DialogPane {
    public boolean startGame;
    public Player player1;
    public Player player2;

    public SetUpGameDialog() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SetUpGameDialog.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, root.prefWidth(-1), root.prefHeight(-1));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Setup players");
        stage.setScene(scene);
        stage.showAndWait();

        SetUpGameDialogController controller = loader.<SetUpGameDialogController>getController();
        startGame = controller.startGame;
        player1 = controller.getPlayer1();
        player2 = controller.getPlayer2();
    }
}
