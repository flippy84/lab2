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

/**
 * Works like the LocalComputerPlayer except this class communicates
 * over a socket. Used by both the game server and game client.
 * @author Andreas Carlsson
 * @see LocalComputerPlayer
 */
public class RemoteComputerPlayer extends Player implements Runnable {
    private Socket socket;
    private GameGrid gameGrid;
    private BufferedReader in;
    private PrintWriter out;
    private Pattern pattern;

    /**
     * Construct the server part of the RemoteComputerPlayer that uses
     * the given socket for communicating with the game client.
     * @param socket Reference to an open socket for use with
     *               communicating with the game client.
     */
    public RemoteComputerPlayer(Socket socket) {
        this();
        this.socket = socket;
    }

    /**
     * The client part of the RemoteComputerPlayer.
     * @param gameGrid Reference to a GameGrid used for
     *                 sending valid moves in the current grid.
     */
    public RemoteComputerPlayer(GameGrid gameGrid) {
        this();
        this.gameGrid = gameGrid;
    }

    private RemoteComputerPlayer() {
        pattern = Pattern.compile("(\\d+),(\\d+)");
    }

    /**
     * Gets a move from the server given a set of valid moves
     * in the current GameGrid. The format of the request is:
     * "GET_MOVE x,y ..."
     * The response is a point with the format "x,y".
     * @return Returns the move sent from the server.
     */
    @Override
    public Point getMove() {
        String response;
        Point[] moves;
        Matcher matcher;
        int x, y;

        // Request a move from the server, format is GET_MOVE x,y ...
        out.print("GET_MOVE");
        moves = gameGrid.getValidMoves();
        for (Point move : moves) {
            out.print(String.format(" %d,%d", move.x, move.y));
        }
        out.println();
        out.flush();

        // Save the server response
        try {
            response = in.readLine();
            if (response == null)
                return null;
        } catch (Exception e) {
            return null;
        }

        // Parse response
        matcher = pattern.matcher(response);
        if (matcher.matches()) {
            x = Integer.parseInt(matcher.group(1));
            y = Integer.parseInt(matcher.group(2));
            return new Point(x, y);
        }

        return null;
    }

    /**
     * Initialize the client and connect to the server, displays
     * an error if the server doesn't respond.
     * @return
     */
    @Override
    public boolean init() {
        if (connect()) {
            return true;
        } else {
            ErrorDialog errorDialog = new ErrorDialog();
            errorDialog.showAndWait("Error connecting to server");
            return false;
        }
    }

    /**
     * Connect to the server by getting the last updated
     * address and port from the SQL Server.
     */
    private boolean connect() {
        if (socket != null && socket.isConnected())
            return true;

        // Get server details from SQL Server
        DatabaseManager databaseManager = new DatabaseManager();
        ServerDetails details = databaseManager.getServerDetails();

        // Set up input and output streams
        try {
            socket = new Socket(details.inetAddress, details.port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "RemoteComputerPlayer";
    }

    /**
     * This method is run in a new thread when the server gets a new
     * connection from a client, enters a loop waiting for lines from
     * the client and terminates when the client closes the connection
     * or a network error occurs.
     */
    @Override
    public void run() {
        String request;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            System.out.println("< " + socket.getInetAddress().getHostAddress() + " connected");

            while (true) {
                request = in.readLine();
                if (request == null) {
                    System.out.println("< " + socket.getInetAddress().getHostAddress() + " disconnected");
                    return;
                }
                handleRequest(request, out);
            }
        } catch (SocketException e) {
            System.out.println("< " + socket.getInetAddress().getHostAddress() + " disconnected unexpectedly");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the socket used by the client.
     */
    @Override
    public void close() {
        try {
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Handles a request from a client parsing the given points
     * in the request and returns a randomized point from the
     * given points.
     */
    private void handleRequest(String request, PrintWriter out) {
        Vector<Point> moves = new Vector<>();
        Point move;
        Matcher matcher;
        String requestSplits[] = request.split(" ");
        int x, y;
        Random random;

        // Parse request and save moves
        if ("GET_MOVE".equals(requestSplits[0])) {
            System.out.print("< " + socket.getInetAddress().getHostAddress() + " requested next move {");
            for (int i = 1; i < requestSplits.length; i++) {
                matcher = pattern.matcher(requestSplits[i]);
                if (matcher.matches()) {
                    x = Integer.parseInt(matcher.group(1));
                    y = Integer.parseInt(matcher.group(2));

                    moves.add(new Point(x, y));

                    System.out.print(String.format(" %d,%d", x, y));
                }
            }
            System.out.println(" }");
        }

        if (moves.size() > 0) {
            random = new Random();
            int i = random.nextInt(moves.size());
            move = moves.get(i);

            out.println(String.format("%d,%d", move.x, move.y));
            out.flush();

            System.out.println(String.format("> " + socket.getInetAddress().getHostAddress() + " received move %d,%d", move.x, move.y));
        }
    }
}
