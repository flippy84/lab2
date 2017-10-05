import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SetUpGameDialog extends DialogPane {
    private IHumanPlayerInput playerInput;
    private GameGrid gameGrid;

    public SetUpGameDialog(IHumanPlayerInput playerInput, GameGrid gameGrid) throws Exception {
        //Save a reference to the board for human player input support
        this.playerInput = playerInput;
        this.gameGrid = gameGrid;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("SetUpGameDialog.fxml"));
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
        player1Type.setItems(FXCollections.<Player>observableArrayList(
                new HumanPlayer(playerInput),
                new LocalComputerPlayer(gameGrid),
                new RemoteComputerPlayer()
        ));
        player1Type.getSelectionModel().selectFirst();

        player2Type.setItems(FXCollections.<Player>observableArrayList(
                new HumanPlayer(playerInput),
                new LocalComputerPlayer(gameGrid),
                new RemoteComputerPlayer()
        ));
        player2Type.getSelectionModel().selectFirst();
    }

    public Player getPlayer1() {
        Player player1 = player1Type.getSelectionModel().getSelectedItem();
        player1.name = player1Name.getText();
        player1.markerID = Marker.White;
        return player1;
    }

    public Player getPlayer2() {
        Player player2 = player2Type.getSelectionModel().getSelectedItem();
        player2.name = player2Name.getText();
        player2.markerID = Marker.Black;
        return player2;
    }
}
