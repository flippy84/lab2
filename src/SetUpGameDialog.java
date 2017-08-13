import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SetUpGameDialog extends DialogPane {
    public boolean startGame;

    public SetUpGameDialog() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SetUpGameDialog.fxml"));
        loader.setController(this);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Setup players");
        stage.setScene(scene);
        stage.showAndWait();

        /*Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        lock.wait();

        condition.notify();*/
    }

    @FXML
    private ChoiceBox<Player> player1Type;

    @FXML
    private ChoiceBox<Player> player2Type;

    @FXML
    private TextField player1Name;

    @FXML
    private TextField player2Name;

    public GameFrame frame;

    @FXML
    private void initialize() {
        player1Type.setItems(FXCollections.<Player>observableArrayList(
                new HumanPlayer(frame),
                new LocalComputerPlayer(),
                new RemoteComputerPlayer()
        ));
        player1Type.getSelectionModel().selectFirst();

        player2Type.setItems(FXCollections.<Player>observableArrayList(
                new HumanPlayer(frame),
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
