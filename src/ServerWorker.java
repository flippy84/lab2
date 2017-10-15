import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerWorker extends Thread {
    private Socket clientSocket;

    public ServerWorker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());

            while (true) {
                String line = in.readLine();
                if (line == null)
                    break;
                processInput(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processInput(String line) {
        switch (line) {
            case "hej":
                System.out.println("cp");
                break;
        }
    }
}
