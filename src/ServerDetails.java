import java.net.InetAddress;

/**
 * The details of a game server that contains
 * the address and port of the server.
 * @author Andreas Carlsson
 */
public class ServerDetails {
    public InetAddress inetAddress;
    public int port;

    public ServerDetails(InetAddress inetAddress, int port) {
        this.inetAddress = inetAddress;
        this.port = port;
    }
}
