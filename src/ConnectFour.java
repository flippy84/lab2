import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.util.Optional;

public class ConnectFour extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Optional<ButtonType> result;
        GameGrid gameGrid = new GameGrid();
        GameBoard gameBoard = new GameBoard(gameGrid);
        // GameBoard implements IHumanPlayerInput to support clicking on the GameBoard
        IHumanPlayerInput playerInput = gameBoard;
        // Show a set up dialog and pass in IHumanPlayerInput for the HumanPlayer class
        SetUpGameDialog setup = new SetUpGameDialog(playerInput, gameGrid);

        while (true) {
            result = setup.showAndWait();

            if (result.isPresent() && result.get().getButtonData() == ButtonData.OK_DONE) {
                if (!setup.getPlayer1().init() || !setup.getPlayer2().init()) {
                    continue;
                }
            }

            break;
        }

        // If the user clicked OK start the game
        if (result.isPresent() && result.get().getButtonData() == ButtonData.OK_DONE)
        {
            GameManager gameManager = new GameManager(setup.getPlayer1(), setup.getPlayer2(), gameGrid);
            GameFrame gameFrame = new GameFrame(gameManager, gameBoard);
            Scene scene = new Scene(gameFrame);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Connect Four");
            primaryStage.setOnCloseRequest(event -> gameManager.quit());

            primaryStage.show();
            gameManager.play();
        }
    }
}
