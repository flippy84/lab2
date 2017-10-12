import javax.xml.crypto.Data;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;

public class DatabaseManager {
    private String connectionString = "jdbc:sqlserver://hitsql-db.hb.se:56077;databaseName=oomuht1608;user=oomuht1608;password=spad66;";

    public void updateServerDetails(InetAddress inetAddress, int port) {
        Connection connection;

        try {
            inetAddress = Inet4Address.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Unknown host");
            return;
        }

        try {
            connection = DriverManager.getConnection(connectionString);
            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery("SELECT [ipAddress], [port] FROM [dbo].[ConnectFourServer] WHERE [groupId] = 8");
            if (result.next()) {
                statement.executeUpdate(String.format("UPDATE [dbo].[ConnectFourServer] SET [ipAddress] = %s, [port] = %d WHERE [groupId] = 8", inetAddress.getHostAddress(), port));
            } else {
                statement.executeUpdate(String.format("INSERT INTO [dbo].[ConnectFourServer] ([groupId], [ipAddres], [port]) VALUES (8, '%s', %d", inetAddress.getHostAddress(), port));
            }
        } catch (SQLException e) {
            System.out.println("Couldn't update database");
            return;
        }
    }

    public ServerDetails getServerDetails() {
        Connection connection;
        CallableStatement statement;
        String address;
        int port;
        InetAddress inetAddress;

        try {
            connection = DriverManager.getConnection(connectionString);
            statement = connection.prepareCall("{call [dbo].[GetConnectionDetails](?, ?, ?)}");
            statement.setInt(1, 8);
            statement.registerOutParameter(2, Types.VARCHAR);
            statement.registerOutParameter(3, Types.INTEGER);
            statement.execute();

            address = statement.getString(2);
            port = statement.getInt(3);
            inetAddress = Inet4Address.getByName(address);
        } catch (Exception e) {
            return null;
        }
        return new ServerDetails(inetAddress, port);
    }
}
