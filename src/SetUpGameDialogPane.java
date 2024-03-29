import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import java.io.IOException;

/**
 * Internal class used by SetUpGameDialog that loads the UI from
 * a FXML file.
 * @author Andreas Carlsson
 * @see SetUpGameDialog
 */
public class SetUpGameDialogPane extends DialogPane {
    private IHumanPlayerInput playerInput;
    private GameGrid gameGrid;

    public SetUpGameDialogPane(IHumanPlayerInput playerInput, GameGrid gameGrid) throws IOException {
        //Save a reference to the board for human player input support
        this.playerInput = playerInput;
        this.gameGrid = gameGrid;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("SetUpGameDialogPane.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

        this.getButtonTypes().add(new ButtonType("OK", ButtonData.OK_DONE));
        this.getButtonTypes().add(new ButtonType("Cancel", ButtonData.CANCEL_CLOSE));
    }

    @FXML
    private ChoiceBox<Player> player1Type;

    @FXML
    private ChoiceBox<Player> player2Type;

    @FXML
    private TextField player1Name;

    @FXML
    private TextField player2Name;

    @FXML
    private void initialize() {
        player1Type.setItems(FXCollections.observableArrayList(
                new HumanPlayer(playerInput),
                new LocalComputerPlayer(gameGrid),
                new RemoteComputerPlayer(gameGrid)
        ));
        player1Type.getSelectionModel().selectFirst();

        player2Type.setItems(FXCollections.observableArrayList(
                new HumanPlayer(playerInput),
                new LocalComputerPlayer(gameGrid),
                new RemoteComputerPlayer(gameGrid)
        ));
        player2Type.getSelectionModel().selectFirst();
    }

    /**
     * Returns player one.
     * @return Returns a Player object for player one.
     */
    public Player getPlayer1() {
        Player player1 = player1Type.getSelectionModel().getSelectedItem();
        player1.name = player1Name.getText();
        player1.markerID = Marker.White;
        return player1;
    }

    /**
     * Returns player two.
     * @return Returns a Player object for player two.
     */
    public Player getPlayer2() {
        Player player2 = player2Type.getSelectionModel().getSelectedItem();
        player2.name = player2Name.getText();
        player2.markerID = Marker.Black;
        return player2;
    }
}
