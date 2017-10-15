import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemoteComputerPlayer extends Player implements Runnable {
    Socket socket;
    GameGrid gameGrid;
    @Override
    public Point getMove() {
        return null;
    }

    public RemoteComputerPlayer(Socket socket) {
        this.socket = socket;
    }

    public RemoteComputerPlayer(GameGrid gameGrid) {
        this.gameGrid = gameGrid;

        DatabaseManager databaseManager = new DatabaseManager();
        ServerDetails details = databaseManager.getServerDetails();
        try {
            socket = new Socket(details.inetAddress, details.port);
        } catch (Exception ignored) {

        }
    }

    @Override
    public String toString() {
        return "RemoteComputerPlayer";
    }

    @Override
    public void run() {
        BufferedReader in;
        PrintWriter out;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            while (true) {
                String line = in.readLine();
                if (line == null)
                    break;
                handleInput(line, out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleInput(String line, PrintWriter out) {
        String parts[] = line.split(" ");
        Vector<Point> points = new Vector<>();
        Pattern pattern = Pattern.compile("(\\d+),(\\d+)");

        if (parts[0].equals("GET_MOVE")) {
            for (int i = 1; i < parts.length; i++) {
                Matcher matcher = pattern.matcher(parts[i]);
                if (matcher.matches()) {
                    int x = Integer.parseInt(matcher.group(1));
                    int y = Integer.parseInt(matcher.group(2));
                    points.add(new Point(x, y));
                }
            }
        }

        if (points.size() > 0) {
            Random random = new Random();
            int i = random.nextInt(points.size());
            Point point = points.get(i);
            out.println(String.format("%d,%d", point.x, point.y));
            out.flush();
        }
    }
}
