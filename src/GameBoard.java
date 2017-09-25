import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GameBoard extends Pane {
    // Keep track of all markers for easy lookup
    private Circle[][] circles;
    @FXML
    // Our UI grid with markers
    private GridPane grid;

    // Locks and Condition's for our getInput support function for the HumanPlayer class
    private Condition newInput;
    private Lock lock;
    private Point input;

    private GameGrid gameGrid;

    public GameBoard(GameFrame frame, GameGrid gameGrid) throws Exception {
        this.gameGrid = gameGrid;
        // Add an event listener for game grid changes
        gameGrid.addOnUpdate(this::updateGrid);

        // Create a lock for input handling for the HumanPlayer class
        lock = new ReentrantLock();
        newInput = lock.newCondition();

        // Load the UI from the fxml template
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameBoard.fxml"));
        loader.setController(this);
        this.getChildren().add(loader.load());
    }

    @FXML
    /*
     * Add circles to every cell and save the reference in the circles matrix
     * for easy lookup in our update function.
     */
    public void initialize() {
        circles = new Circle[8][8];
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Circle circle = new Circle(20, Color.TRANSPARENT);
                circle.setOnMouseClicked(this::onMouseClicked);
                GridPane.setHalignment(circle, HPos.CENTER);
                GridPane.setValignment(circle, VPos.CENTER);

                circles[x][y] = circle;
                grid.add(circle, x, y);
            }
        }

        updateGrid(gameGrid);
    }

    /*
     * This is called from the HumanPlayer class from the GameManager thread.
     * This function blocks until we get an onMouseClicked event from the UI.
     * Return value is the coordinates of the clicked cell.
     */
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

    // Event handler for mouse clicks from the GridPane
    private void onMouseClicked(MouseEvent event) {
        int y = GridPane.getRowIndex((Node) event.getSource());
        int x = GridPane.getColumnIndex((Node) event.getSource());

        // We got user input, save it and send a signal so getInput returns
        lock.lock();
        try {
            input = new Point(x, y);
            newInput.signalAll();
        } finally {
            lock.unlock();
        }
    }

    // Iterate over all cells in the GameGrid and update the GridPane accordingly.
    private void updateGrid(GameGrid gameGrid) {
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
}
