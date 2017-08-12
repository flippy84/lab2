import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class SetupGameDialogController {
    @FXML
    private ChoiceBox<Player> player1;

    @FXML
    private ChoiceBox<Player> player2;

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
    }

    public SetupGameDialogController() {
    }

    public Player getPlayer1() {
        return new HumanPlayer();
    }

    public Player getPlayer2() {
        return new HumanPlayer();
    }
}
