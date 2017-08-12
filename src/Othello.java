import javafx.stage.Stage;
import javafx.application.Application;

public class Othello extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        SetUpGameDialog setup = new SetUpGameDialog();
        if(setup.startGame) {
            GameManager gm = new GameManager(new HumanPlayer(), new HumanPlayer(), () -> {
                System.out.println("Hej");
            });
            primaryStage.show();
        }
    }
}
