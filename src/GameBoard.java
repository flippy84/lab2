import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GameBoard extends Pane {
    public GameBoard(GameFrame frame, GameGrid grid) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameBoard.fxml"));
        loader.setController(this);
        this.getChildren().add(loader.load());
    }

    @FXML
    private GridPane grid;

    @FXML
    public void initialize() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Circle circle = new Circle(20, Color.TRANSPARENT);
                circle.setOnMouseClicked(this::onMouseClicked);
                GridPane.setHalignment(circle, HPos.CENTER);
                GridPane.setValignment(circle, VPos.CENTER);

                if (x == 3 && y == 3)
                    circle.setFill(Color.WHITE);
                else if(x == 4 && y == 4)
                    circle.setFill(Color.WHITE);
                else if(x == 3 && y == 4)
                    circle.setFill(Color.BLACK);
                else if(x == 4 && y == 3)
                    circle.setFill(Color.BLACK);

                grid.add(circle, x, y);
            }
        }
    }

    private void onMouseClicked(MouseEvent event) {
        int y = GridPane.getRowIndex((Node) event.getSource());
        int x = GridPane.getColumnIndex((Node) event.getSource());
        Circle circle = (Circle) event.getSource();
        circle.setFill(Color.YELLOW);
    }
}
