import java.net.*;

public class ConnectFourServer {
    public static void main(String[] args) {
        InetAddress localhost;
        DatabaseManager databaseManager;
        int port = 5000;

        try {
            localhost = Inet4Address.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Error getting local host address");
            return;
        }

        ServerSocket server;
        Socket client;
        try {
            server = new ServerSocket(port);
        } catch (Exception e) {
            return;
        }

        databaseManager = new DatabaseManager();
        if (!databaseManager.updateServerDetails(localhost, port)) {
            System.out.println("Error updating database");
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
