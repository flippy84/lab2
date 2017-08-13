import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.stage.WindowEvent;

public class Othello extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        GameGrid grid = new GameGrid();
        GameFrame frame = new GameFrame(grid);
        SetUpGameDialog setup = new SetUpGameDialog(frame.getGameBoard());
        if(setup.startGame) {
            Scene scene = new Scene(frame);
            GameManager gm = new GameManager(setup.getPlayer1(), setup.getPlayer2(), () -> {
                System.out.println("Hej");
            }, grid);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Othello");
            primaryStage.setOnCloseRequest(event -> {
                gm.quit();
            });
            primaryStage.show();
            gm.play();
        }
    }
}
