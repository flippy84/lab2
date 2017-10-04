import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.stage.WindowEvent;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.*;

import java.util.Optional;

public class ConnectFour extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        GameGrid gameGrid = new GameGrid();
        GameBoard gameBoard = new GameBoard(gameGrid);
        GameFrame frame = new GameFrame(gameBoard);
        // GameBoard implements IHumanPlayerInput to support clicking on the GameBoard
        IHumanPlayerInput playerInput = gameBoard;
        // Show a set up dialog and pass in IHumanPlayerInput for the HumanPlayer class
        SetUpGameDialog setup = new SetUpGameDialog(playerInput);

        Dialog dialog = new Dialog();
        dialog.setDialogPane(setup);
        dialog.setTitle("New game");
        Optional<ButtonType> result = dialog.showAndWait();

        // If the user clicked OK start the game
        if (result.isPresent() && result.get().getButtonData() == ButtonData.OK_DONE)
        {
            Scene scene = new Scene(frame);
            GameManager gm = new GameManager(setup.getPlayer1(), setup.getPlayer2(), gameGrid);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Connect Four");
            primaryStage.setOnCloseRequest(event -> {
                gm.quit();
            });

            primaryStage.show();
            gm.play();
        }
    }
}
