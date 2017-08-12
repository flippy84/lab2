import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.stage.WindowEvent;

public class Othello extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        SetUpGameDialog setup = new SetUpGameDialog();
        if(setup.startGame) {
            GameGrid grid = new GameGrid();
            GameFrame frame = new GameFrame(grid);
            Scene scene = new Scene(frame);

            GameManager gm = new GameManager(setup.player1, setup.player2, () -> {
                System.out.println("Hej");
            }, grid);
            gm.play();

            primaryStage.setScene(scene);
            primaryStage.setTitle("Othello");
            primaryStage.setOnCloseRequest(event -> {
                gm.quit();
            });
            primaryStage.show();
        }
    }
}
