import javax.xml.crypto.Data;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;

public class ConnectFourServer {
    public static void main(String[] args) {
        DatabaseManager databaseManager = new DatabaseManager();
        ServerDetails serverDetails = databaseManager.getServerDetails();

        Connection connection;
        String address;
        int port = 66;
        InetAddress inetAddress = null;

        try {
            inetAddress = Inet4Address.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Unknown host");
            return;
        }

        address = inetAddress.getHostAddress();

        try {
            connection = DriverManager.getConnection("jdbc:sqlserver://hitsql-db.hb.se:56077;databaseName=oomuht1608;user=oomuht1608;password=spad66;");
            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            ResultSet result = statement.executeQuery("SELECT [ipAddress], [port] FROM [dbo].[ConnectFourServer] WHERE [groupId] = 8");
            if (result.next()) {
                result.updateString("ipAddress", address);
                result.updateInt("port", port);
                result.updateRow();
            } else {
                statement.executeQuery(String.format("INSERT INTO [dbo].[ConnectFourServer] ([groupId], [ipAddres], [port]) VALUES (8, '%s', %d", address, port));
            }

        } catch (SQLException e) {
            System.out.println("Couldn't update database");
            return;
        }
    }
}
