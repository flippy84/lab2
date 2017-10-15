import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GameFrame extends BorderPane {
    private Label statusLabel;
    private GameManager gameManager;

    public GameFrame(GameManager gameManager, GameBoard gameBoard) {
        this.gameManager = gameManager;

        this.setTop(createTop());
        this.setCenter(gameBoard);
        this.setBottom(createBottom());
    }

    private Node createTop() {
        Button newGameButton = new Button("New game");
        newGameButton.setOnMouseReleased((event -> gameManager.newGame()));

        Button quitButton = new Button("Quit");
        quitButton.setOnMouseReleased((event -> {
            gameManager.quit();
            Stage stage = (Stage)quitButton.getScene().getWindow();
            stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        }));

        HBox buttons = new HBox();
        HBox.setMargin(newGameButton, new Insets(5, 5, 5, 5));
        HBox.setMargin(quitButton, new Insets(5, 5, 5, 5));
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(newGameButton, quitButton);

        return buttons;
    }

    private Node createBottom() {
        statusLabel = new Label();
        statusLabel.setPadding(new Insets(5, 5, 5, 5));

        return statusLabel;
    }

    public void setStatus(String text) {
        statusLabel.setText(text);
    }
}
