import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class GameFrame extends BorderPane {
    public GameFrame(GameGrid grid) throws Exception {
        GameBoard board = new GameBoard(grid);
        this.setCenter(board);

        Parent toolbar = FXMLLoader.load(getClass().getResource("Toolbar.fxml"));
        this.setTop(toolbar);
        this.setBottom(new Label("Hej"));
    }
}
