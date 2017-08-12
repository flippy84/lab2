import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class GameBoard extends AnchorPane {
    public GameBoard() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameBoard.fxml"));
        Parent root = loader.load();

        this.getChildren().add(root);

        /*Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Setup players");
        stage.setScene(scene);
        stage.showAndWait();*/

        GameBoardController controller = loader.getController();
    }
}
