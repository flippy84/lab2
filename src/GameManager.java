import java.awt.*;

interface Callback
{
    abstract void Call();
}

public class GameManager extends Thread {
    private boolean stopped;
    private Player player1;
    private Player player2;
    private GameGrid grid;

    GameManager(Player player1, Player player2, Callback callback, GameGrid grid) {
        this.player1 = player1;
        this.player2 = player2;
        this.grid = grid;
    }

    @Override
    public void run() {
        while (!stopped) {
            Move(player1);
            Move(player2);
            System.out.println("Running");
        }
        System.out.println("Stopped");
    }

    public void play() {
        start();
    }

    public void quit() {
        stopped = true;
        this.interrupt();
    }

    private void Move(Player p) {
        Point t = p.getMove();
        grid.setCell(p.markerID, t.x, t.y);
    }
}
