import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class GameBoard extends Pane {
    public GameBoard(GameGrid grid) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameBoard.fxml"));
        this.getChildren().add(loader.load());
    }
}
