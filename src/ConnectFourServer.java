import java.net.*;

public class ConnectFourServer {
    public static void main(String[] args) {
        InetAddress localhost;
        DatabaseManager databaseManager;

        try {
            localhost = Inet4Address.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Error getting local host address");
            return;
        }

        ServerSocket serverSocket;
        Socket clientSocket;
        try {
            serverSocket = new ServerSocket(5000);
        } catch (Exception e) {
            return;
        }

        databaseManager = new DatabaseManager();
        if (!databaseManager.updateServerDetails(localhost, 600)) {
            System.out.println("Error updating database");
            return;
        }

        try {
            while (true) {
                clientSocket = serverSocket.accept();
                ServerWorker worker = new ServerWorker(clientSocket);
                worker.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
