import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class GameFrame extends BorderPane {
    private Label statusLabel;

    public GameFrame(GameBoard gameBoard) throws Exception {
        this.setTop(createTop());
        this.setCenter(gameBoard);
        this.setBottom(createBottom());
    }

    private Node createTop() {
        Button newGameButton = new Button("New game");
        newGameButton.setOnMouseReleased((event -> {
            newGameEvent.fire(this);
        }));

        Button quitButton = new Button("Quit");
        quitButton.setOnMouseReleased((event -> {
            quitEvent.fire(this);
        }));

        HBox buttons = new HBox();
        HBox.setMargin(newGameButton, new Insets(5, 5, 5, 5));
        HBox.setMargin(quitButton, new Insets(5, 5, 5, 5));
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(newGameButton, quitButton);

        return buttons;
    }

    private Node createBottom() {
        Label statusLabel = new Label();
        statusLabel.setPadding(new Insets(5, 5, 5, 5));

        return statusLabel;
    }

    public Event quitEvent;
    public Event newGameEvent;

    public void setStatus(String text) {
        statusLabel.setText(text);
    }
}
