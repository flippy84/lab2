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
import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * GameBoard draws the current game board from information in GameGrid.
 * It also implements IHumanPlayerInput that HumanPlayer uses for getting input
 * from the player. Since the Player class and it's derived classes runs in a separate thread
 * from JavaFX and to not block the UI thread from running when human player input is needed
 * a lock and condition is used.
 * @author Andreas Carlsson
 */
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

    /**
     * The GameBoard constructor adds an Observer to the GameGrid for grid updates and creates a Lock and
     * Condition used for handling human player input.
     * @param gameGrid Reference to a GameGrid instance to receive events when the game updates.
     * @throws IOException Throws an exception when the UI can't be loaded.
     */
    public GameBoard(GameGrid gameGrid) throws IOException {
        this.gameGrid = gameGrid;
        columns = gameGrid.getColumns();
        rows = gameGrid.getRows();
        // Add an observer for game grid changes
        gameGrid.addObserver((object, arg) -> updateGrid((GameGrid) object));

        // Create a lock for input handling for the HumanPlayer class
        lock = new ReentrantLock();
        haveInput = lock.newCondition();

        // Load the UI from the fxml template
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameBoard.fxml"));
        loader.setController(this);
        this.getChildren().add(loader.load());
    }

    /*
     * Add circles to every cell and save the reference in the circles matrix
     * for easy lookup in our update function.
     */
    @FXML
    private void initialize() {
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

    /**
     * This is called from the HumanPlayer class from the GameManager thread.
     * This function blocks until we get an onMouseClicked event from the UI.
     * @return A Point with the coordinates of the cell the player clicked on.
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
