import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.*;
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
            Player player1, player2;
            Dialog dialog = new Dialog();
            ErrorDialog errorDialog = new ErrorDialog("Error connecting to server");
            dialog.setDialogPane(setup);
            dialog.setTitle("New game");
            result = dialog.showAndWait();

            if (result.isPresent() && result.get().getButtonData() == ButtonData.OK_DONE) {
                player1 = setup.getPlayer1();
                player2 = setup.getPlayer2();

                if (player1 instanceof RemoteComputerPlayer && !((RemoteComputerPlayer) player1).connect()) {
                    errorDialog.showAndWait();
                    continue;
                }

                if (player2 instanceof RemoteComputerPlayer && !((RemoteComputerPlayer) player2).connect()) {
                    errorDialog.showAndWait();
                    continue;
                }
            } else {
                break;
            }

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
