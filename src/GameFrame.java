import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * GameFrame is the main class for the UI, it hosts a GameBoard and contains two buttons
 * for new game and quit.
 * @author Andreas Carlsson
 */
public class GameFrame extends BorderPane {
    private GameManager gameManager;

    /**
     * GameFrame constructor creates and initializes the main UI.
     * @param gameManager GameManager reference used for starting a
     *                    new game when the current one has ended.
     * @param gameBoard GameBoard reference for displaying the game board.
     */
    public GameFrame(GameManager gameManager, GameBoard gameBoard) {
        this.gameManager = gameManager;

        this.setTop(createTop());
        this.setCenter(gameBoard);
    }

    private Node createTop() {
        Button newGameButton = new Button("New game");
        newGameButton.setOnMouseReleased((event -> gameManager.newGame()));

        Button quitButton = new Button("Quit");
        quitButton.setOnMouseReleased((event -> {
            //Get the parent main stage from a button to send a window close request to exit the game.
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
}
