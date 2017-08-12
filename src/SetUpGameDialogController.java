import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class SetUpGameDialogController {
    @FXML
    private ChoiceBox<Player> player1Type;

    @FXML
    private ChoiceBox<Player> player2Type;

    @FXML
    private TextField player1Name;

    @FXML
    private TextField player2Name;

    public boolean startGame;

    @FXML
    private void initialize() {
        player1Type.setItems(FXCollections.<Player>observableArrayList(
                new HumanPlayer(),
                new LocalComputerPlayer(),
                new RemoteComputerPlayer()
        ));
        player1Type.getSelectionModel().selectFirst();

        player2Type.setItems(FXCollections.<Player>observableArrayList(
                new HumanPlayer(),
                new LocalComputerPlayer(),
                new RemoteComputerPlayer()
        ));
        player2Type.getSelectionModel().selectFirst();
    }

    @FXML
    private void onClickedStart() {
        player1Type.getScene().getWindow().hide();
        startGame = true;
    }

    @FXML
    private  void onClickedQuit() {
        player1Type.getScene().getWindow().hide();
    }

    public SetUpGameDialogController() {
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

