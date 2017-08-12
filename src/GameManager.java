interface Callback
{
    abstract void Call();
}

public class GameManager {
    GameManager(Player player1, Player player2, Callback callback) {
        callback.Call();
    }
}
