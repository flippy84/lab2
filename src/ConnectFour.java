import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * ConnectFour is the main class for the game and extends the JavaFX class Application
 * and overrides the start method.
 * @author Andreas Carlsson
 */
public class ConnectFour extends Application {

    /**
     * This method is the entry point for the JavaFX application.
     * @param primaryStage Supplied stage from JavaFX where our game scene is added.
     * @throws Exception Throws an exception if the initialization of the game fails.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Optional result;
        GameGrid gameGrid = new GameGrid();
        GameBoard gameBoard = new GameBoard(gameGrid);
        // GameBoard implements IHumanPlayerInput to support clicking on the GameBoard
        IHumanPlayerInput playerInput = gameBoard;
        // Show a set up dialog and pass in IHumanPlayerInput for the HumanPlayer class
        SetUpGameDialog setup = new SetUpGameDialog(playerInput, gameGrid);

        while (true) {
            result = setup.showAndWait();

            if (result.isPresent() && result.get() instanceof ButtonType && ((ButtonType)result.get()).getButtonData() == ButtonData.OK_DONE) {
                if (!setup.getPlayer1().init() || !setup.getPlayer2().init()) {
                    continue;
                }
            }

            break;
        }

        // If the user clicked OK start the game
        if (result.isPresent() && result.get() instanceof ButtonType && ((ButtonType)result.get()).getButtonData() == ButtonData.OK_DONE)
        {
            GameManager gameManager = new GameManager(setup.getPlayer1(), setup.getPlayer2(), gameGrid);
            GameFrame gameFrame = new GameFrame(gameManager, gameBoard);
            Scene scene = new Scene(gameFrame);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Connect Four");
            primaryStage.setOnCloseRequest(event -> gameManager.close());

            primaryStage.show();
            gameManager.play();
        }
    }
}
