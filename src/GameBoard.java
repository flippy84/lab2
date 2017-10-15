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

public class GameBoard extends Pane implements IHumanPlayerInput {
    // Keep track of all markers for easy lookup
    private Circle[][] circles;
    @FXML
    // Our UI grid with markers
    private GridPane grid;

    // Locks and Condition's for our getInput support function for the HumanPlayer class
    private Condition haveInput;
    private Lock lock;
    private Point input;

    private GameGrid gameGrid;
    private int columns;
    private int rows;

    public GameBoard(GameGrid gameGrid) throws Exception {
        this.gameGrid = gameGrid;
        columns = gameGrid.getColumns();
        rows = gameGrid.getRows();
        // Add an observer for game grid changes
        gameGrid.addObserver((o, arg) -> updateGrid((GameGrid)o));

        // Create a lock for input handling for the HumanPlayer class
        lock = new ReentrantLock();
        haveInput = lock.newCondition();

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
        circles = new Circle[columns][rows];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
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
        try {
            haveInput.await();
            return input;
        } catch (InterruptedException exception) {
            return null;
        } finally {
            lock.unlock();
        }
    }

    // Event handler for mouse clicks from the GridPane
    private void onMouseClicked(MouseEvent event) {
        int y = GridPane.getRowIndex((Node) event.getSource());
        int x = GridPane.getColumnIndex((Node) event.getSource());

        // We got user input, save it and send a signal so haveInput returns
        lock.lock();
        try {
            input = new Point(x, y);
            haveInput.signalAll();
        } finally {
            lock.unlock();
        }
    }

    // Iterate over all cells in the GameGrid and update the GridPane accordingly.
    private void updateGrid(GameGrid gameGrid) {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                Marker m = gameGrid.getCell(x, y);
                switch (m) {
                    case Black:
                        circles[x][y].setFill(Color.BLACK);
                        break;
                    case White:
                            circles[x][y].setFill(Color.WHITE);
                        break;
                    case None:
                        circles[x][y].setFill(Color.TRANSPARENT);
                        break;
                }
            }
        }
    }
}
