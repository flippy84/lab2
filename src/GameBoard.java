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

import java.awt.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GameBoard extends Pane {
    public GameBoard(GameFrame frame, GameGrid gameGrid) throws Exception {
        gameGrid.addOnUpdate(this::UpdateGrid);
        lock = new ReentrantLock();
        newInput = lock.newCondition();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameBoard.fxml"));
        loader.setController(this);
        this.getChildren().add(loader.load());
    }

    private Circle[][] circles;

    @FXML
    private GridPane grid;

    @FXML
    public void initialize() {
        circles = new Circle[8][8];
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

                circles[x][y] = circle;
                grid.add(circle, x, y);
            }
        }
    }

    private Condition newInput;
    private Lock lock;
    private Point input;

    public Point getInput() {
        lock.lock();
        while (true) {
            try {
                newInput.await();
                return input;
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    private void UpdateGrid(GameGrid gameGrid) {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Marker m = gameGrid.getCell(x, y);
                switch (m) {
                    case Black:
                        if (circles[x][y].getFill() != Color.BLACK)
                            circles[x][y].setFill(Color.BLACK);
                        break;
                    case White:
                        if (circles[x][y].getFill() != Color.WHITE)
                            circles[x][y].setFill(Color.WHITE);
                        break;
                }
            }
        }
    }

    private void onMouseClicked(MouseEvent event) {
        int y = GridPane.getRowIndex((Node) event.getSource());
        int x = GridPane.getColumnIndex((Node) event.getSource());

        lock.lock();
        try {
            input = new Point(x, y);
            newInput.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
