import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectFourServer {
    public static void main(String[] args) {
        InetAddress localhost;
        DatabaseManager databaseManager;
        ServerSocket server;
        Socket client;
        int port = 5000;

        try {
            localhost = Inet4Address.getLocalHost();
            server = new ServerSocket(port);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        databaseManager = new DatabaseManager();
        if (!databaseManager.updateServerDetails(localhost, port)) {
            return;
        }

        try {
            while (true) {
                client = server.accept();
                RemoteComputerPlayer player = new RemoteComputerPlayer(client);
                Thread thread = new Thread(player);
                thread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
