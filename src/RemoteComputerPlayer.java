import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemoteComputerPlayer extends Player implements Runnable {
    Socket socket;
    GameGrid gameGrid;
    BufferedReader in;
    PrintWriter out;

    @Override
    public Point getMove() {
        String line;

        if (socket == null) {
            connect();
        }

        out.print("GET_MOVE");
        Point[] moves = gameGrid.getValidMoves();
        for (Point move : moves) {
            out.print(String.format(" %d,%d", move.x, move.y));
        }
        out.println();
        out.flush();

        try {
            line = in.readLine();
        } catch (Exception e) {
            return null;
        }

        Pattern pattern = Pattern.compile("(\\d+),(\\d+)");
        Matcher matcher = pattern.matcher(line);
        System.out.println(line);
        System.out.println(matcher.groupCount());
        if (matcher.matches()) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            return new Point(x, y);
        }

        return null;
    }

    public RemoteComputerPlayer(Socket socket) {
        this.socket = socket;
    }

    public RemoteComputerPlayer(GameGrid gameGrid) {
        this.gameGrid = gameGrid;
    }

    private void connect() {
        DatabaseManager databaseManager = new DatabaseManager();
        ServerDetails details = databaseManager.getServerDetails();
        try {
            socket = new Socket(details.inetAddress, details.port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "RemoteComputerPlayer";
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            System.out.println("< " + socket.getInetAddress().getHostAddress() + " connected");

            while (true) {
                String line = in.readLine();
                if (line == null) {
                    System.out.println("< " + socket.getInetAddress().getHostAddress() + " disconnected");
                    return;
                }
                handleInput(line, out);
            }
        } catch (SocketException e) {
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (Exception ignored) {
        }
    }

    private void handleInput(String line, PrintWriter out) {
        String parts[] = line.split(" ");
        Vector<Point> points = new Vector<>();
        Pattern pattern = Pattern.compile("(\\d+),(\\d+)");

        if (parts[0].equals("GET_MOVE")) {
            System.out.print("< " + socket.getInetAddress().getHostAddress() + " requested next move {");
            for (int i = 1; i < parts.length; i++) {
                Matcher matcher = pattern.matcher(parts[i]);
                if (matcher.matches()) {
                    int x = Integer.parseInt(matcher.group(1));
                    int y = Integer.parseInt(matcher.group(2));
                    System.out.print(String.format(" %d,%d", x, y));
                    points.add(new Point(x, y));
                }
            }
            System.out.println(" }");
        }

        if (points.size() > 0) {
            Random random = new Random();
            int i = random.nextInt(points.size());
            Point point = points.get(i);
            System.out.println(String.format("> " + socket.getInetAddress().getHostAddress() + " received move %d,%d", point.x, point.y));
            out.println(String.format("%d,%d", point.x, point.y));
            out.flush();
        }
    }
}
