import java.net.InetAddress;

public class ServerDetails {
    public InetAddress inetAddress;
    public int port;

    public ServerDetails(InetAddress inetAddress, int port) {
        this.inetAddress = inetAddress;
        this.port = port;
    }
}
