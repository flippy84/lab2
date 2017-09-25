import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class GameFrame extends BorderPane {
    private Label status;
    private GameBoard gameBoard;

    public GameFrame(GameGrid grid) throws Exception {
        gameBoard = new GameBoard(this, grid);
        this.setCenter(gameBoard);

        Parent toolbar = FXMLLoader.load(getClass().getResource("Toolbar.fxml"));
        this.setTop(toolbar);

        status = new Label();
        this.setBottom(status);
    }

    public void setStatus(String text) {
        status.setText(text);
    }

    public GameBoard getGameBoard() {
        return this.gameBoard;
    }
}
