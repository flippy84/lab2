import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class SetUpGameDialogController {
    @FXML
    private ChoiceBox<Player> player1;

    @FXML
    private ChoiceBox<Player> player2;

    public boolean startGame;

    @FXML
    private void initialize() {
        player1.setItems(FXCollections.<Player>observableArrayList(
                new HumanPlayer(),
                new LocalComputerPlayer(),
                new RemoteComputerPlayer()
        ));
        player1.getSelectionModel().selectFirst();

        player2.setItems(FXCollections.<Player>observableArrayList(
                new HumanPlayer(),
                new LocalComputerPlayer(),
                new RemoteComputerPlayer()
        ));
        player2.getSelectionModel().selectFirst();
    }

    @FXML
    private void onClickedStart() {
        player1.getScene().getWindow().hide();
        startGame = true;
    }

    @FXML
    private  void onClickedQuit() {
        player2.getScene().getWindow().hide();
    }

    public SetUpGameDialogController() {
    }

    public Player getPlayer1() {
        return player1.getSelectionModel().getSelectedItem();
    }

    public Player getPlayer2() {
        return player2.getSelectionModel().getSelectedItem();
    }
}
