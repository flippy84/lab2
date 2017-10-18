import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This is the main class for the server and is a regular
 * console application.
 * @author Andreas Carlsson
 */
public class ConnectFourServer {
    /**
     * Entry point for the console application.
     * Starts a new game server and updates the SQL Server
     * with the address and port of the server.
     * This server doesn't work with Network Address Translation
     * so this only works with a public IP or if only played
     * on a Local Area Network.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        InetAddress localhost;
        DatabaseManager databaseManager;
        ServerSocket server;
        Socket client;
        int port = 5000;

        // Get the local IP and start the server
        try {
            localhost = Inet4Address.getLocalHost();
            server = new ServerSocket(port);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Update the SQL Server with the servers details.
        databaseManager = new DatabaseManager();
        if (!databaseManager.updateServerDetails(localhost, port)) {
            return;
        }

        /*
         * Enter a loop blocking on accept and waiting for
         * incoming connections from game clients
         */
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
